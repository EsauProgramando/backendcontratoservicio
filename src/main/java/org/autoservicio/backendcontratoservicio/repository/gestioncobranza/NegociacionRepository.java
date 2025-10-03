package org.autoservicio.backendcontratoservicio.repository.gestioncobranza;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.autoservicio.backendcontratoservicio.config.IConfigGeneric;
import org.autoservicio.backendcontratoservicio.config.responseModel;
import org.autoservicio.backendcontratoservicio.excepciones.RepositorioException;
import org.autoservicio.backendcontratoservicio.interfaces.gestioncobranza.INegociacion;
import org.autoservicio.backendcontratoservicio.model.gestioncobranza.BuscarNegociacion;
import org.autoservicio.backendcontratoservicio.model.gestioncobranza.FacturasNociacionItemModel;
import org.autoservicio.backendcontratoservicio.model.gestioncobranza.NegociacionModel;
import org.autoservicio.backendcontratoservicio.response.ClienteContratoxServicio;
import org.autoservicio.backendcontratoservicio.response.CortesServicioRequest;
import org.autoservicio.backendcontratoservicio.response.ListadomorosidadRequest;
import org.autoservicio.backendcontratoservicio.response.Negociacion_clientesRequest;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.List;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Objects;
import java.util.Set;
@Repository
public class NegociacionRepository extends IConfigGeneric implements INegociacion {
    private static final Set<String> ACCIONES_VALIDAS =
            Set.of("FRACCIONAMIENTO", "MODIFICAR_PLAN_PAGOS", "PERIODO_DE_GRACIA");

    @Override
    @Transactional(rollbackFor = Exception.class)
    public responseModel generarnegociacion(NegociacionModel obj) {

        // --- Saneos rápidos tipo "defensa en profundidad"
        if (obj.getFecha_vencimiento_nuevo() == null || obj.getFecha_vencimiento_nuevo().isBlank()) {
            obj.setFecha_vencimiento_nuevo(null);
        }
        final String accion = safeUpper(obj.getAcciones_negociacion());
        final String canalPreferido = safeUpperOrDefault(obj.getCanal_preferido(), "WHATSAPP");

        // --- Validaciones mínimas (mimics SP)
        String validMsg = validarEntrada(obj, accion);
        if (validMsg != null) {
            return responseModel.builder().response(validMsg).build();
        }

        // Fecha nueva (si aplica)
        LocalDate fechaNueva = null;
        try {
            fechaNueva = parseOrNull(obj.getFecha_vencimiento_nuevo());
        } catch (DateTimeParseException dtpe) {
            return responseModel.builder()
                    .response("BAD_REQUEST: fecha_vencimiento_nuevo debe tener formato yyyy-MM-dd")
                    .build();
        }

        // Defaults como en el SP
        Integer autoRecordatorio = obj.getAuto_recordatorio() != null ? obj.getAuto_recordatorio() : 1;
        Integer frecuenciaDias    = obj.getFrecuencia_dias()   != null ? obj.getFrecuencia_dias()   : 3;
        Double montoInicial       = obj.getMontopagar_inicial() != null ? obj.getMontopagar_inicial() : 0.0;
        Integer periodoGracia     = obj.getPeriodo_gracia() != null ? obj.getPeriodo_gracia() : 0;

        try {
            // -------------------------
            // 1) Insert negociaciones
            // -------------------------
            final String sqlInsertNeg = """
                INSERT INTO negociaciones(
                  id_cliente, id_contrato, id_tipo,
                  canal_preferido, acciones_negociacion,
                  auto_recordatorio, frecuencia_dias,
                  fecha_inicio, estado,
                  montopagar_inicial, fecha_vencimiento_nuevo, periodo_gracia,
                  observaciones, usuario_crea, fechareg, monto_total
                ) VALUES (
                  ?, ?, ?,
                  ?, ?,
                  ?, ?,
                  NOW(), 'VIGENTE',
                  ?, ?, ?,
                  ?, ?, NOW(), ?
                )
                """;

            this.jTemplate().update(sqlInsertNeg,
                    obj.getId_cliente(),
                    obj.getId_contrato(),
                    obj.getId_tipo(),
                    canalPreferido,
                    accion,
                    autoRecordatorio,
                    frecuenciaDias,
                    // null si 0, como en el SP: NULLIF(v_monto_inicial, 0)
                    (montoInicial != null && montoInicial > 0) ? montoInicial : null,
                    (fechaNueva != null ? Date.valueOf(fechaNueva) : null),
                    (periodoGracia != null && periodoGracia > 0) ? periodoGracia : null,
                    obj.getObservaciones(),
                    obj.getUsuario_crea(),
                    obj.getMonto_total()
            );

            // -------------------------
            // 2) Acción por factura
            // -------------------------
            final List<FacturasNociacionItemModel> facturas = obj.getFacturas();
            for (int idx = 0; idx < facturas.size(); idx++) {
                FacturasNociacionItemModel it = facturas.get(idx);
                Long idFactura = it.getId_factura();
               String  fecha_vencimiento_nuevo= it.getFecha_vencimiento_nuevo();
                // Pertenencia: factura del cliente/contrato
                final String sqlCheck = "SELECT COUNT(1) FROM facturas WHERE id_factura=? AND id_cliente=? AND id_contrato=?";
                Integer count = this.jTemplate().queryForObject(sqlCheck, Integer.class,
                        idFactura, obj.getId_cliente(), obj.getId_contrato());

                if (count == null || count == 0) {
                    throw negocioError("ERROR_LOGICO: Factura " + idFactura + " no pertenece al cliente/contrato");
                }

                switch (accion) {
                    case "FRACCIONAMIENTO" -> {
                        Double montoFracc = it.getMonto_fraccionado();
                        if (montoFracc == null) {
                            throw negocioError("ERROR_LOGICO: Debe enviar monto_fraccionado para factura " + idFactura);
                        }

                        // Suma fraccionado + marca flag
                        final String sqlFracc1 = """
                            UPDATE facturas
                            SET flagfracionado = 1,
                                monto_fracionado = COALESCE(monto_fracionado, 0) + ?,
                                saldo_fracionado = COALESCE(saldo_fracionado, 0) + ?
                            WHERE id_factura = ?
                            """;
                        this.jTemplate().update(sqlFracc1, montoFracc, montoFracc, idFactura);

                        // Descontar inicial en la PRIMERA factura si aplica
                        if (idx == 0 && montoInicial != null && montoInicial > 0) {
                            final String sqlFracc2 = """
                                UPDATE facturas
                                SET saldo_fracionado = GREATEST(COALESCE(saldo_fracionado, 0) - ?, 0)
                                WHERE id_factura = ?
                                """;
                            this.jTemplate().update(sqlFracc2, montoInicial, idFactura);
                        }

                    }

                    case "MODIFICAR_PLAN_PAGOS" -> {
                        if (fecha_vencimiento_nuevo == null) {
                            throw negocioError("ERROR_LOGICO: fecha_vencimiento_nuevo es obligatorio");
                        }
                        final String sqlUpd = """
                            UPDATE facturas
                            SET flagfechavencimiento = 1,
                                fecha_vencimiento_nuevo = ?
                                 WHERE id_factura = ? AND id_cliente = ?
                            """;
                        this.jTemplate().update(sqlUpd, Date.valueOf(fecha_vencimiento_nuevo), idFactura,obj.getId_cliente());
                    }

                    case "PERIODO_DE_GRACIA" -> {
                        if (periodoGracia == null || periodoGracia <= 0) {
                            throw negocioError("ERROR_LOGICO: periodo_gracia (días) es obligatorio");
                        }

                        // Base: COALESCE(fecha_vencimiento_nuevo, fecha_vencimiento) FOR UPDATE
                        final String sqlBaseVenc = """
                            SELECT COALESCE(fecha_vencimiento_nuevo, fecha_vencimiento)
                            FROM facturas
                            WHERE id_factura=? AND id_cliente=?
                            FOR UPDATE
                            """;
                        LocalDate base = this.jTemplate().queryForObject(sqlBaseVenc, (rs, n) -> {
                            Date d = rs.getDate(1);
                            return (d != null) ? d.toLocalDate() : null;
                        }, idFactura, obj.getId_cliente());

                        if (base == null) {
                            throw negocioError("ERROR_LOGICO: La factura " + idFactura + " no tiene fecha de vencimiento base");
                        }

                        LocalDate nuevoVenc = base.plusDays(periodoGracia);

                        final String sqlUpd = """
                            UPDATE facturas
                            SET flagfechavencimiento = 1,
                                fecha_vencimiento_nuevo = ?
                            WHERE id_factura = ? AND id_cliente = ?
                            """;
                        this.jTemplate().update(sqlUpd, Date.valueOf(nuevoVenc), idFactura, obj.getId_cliente());
                    }

                    default -> throw negocioError("BAD_REQUEST: acciones_negociacion inválida");
                }
            }

            // Si llegamos aquí, todo ok
            return responseModel.builder()
                    .response("OK: Negociación creada y aplicada a facturas")
                    .build();

        } catch (NegocioException be) {
            // Queremos devolver mensaje *y* hacer rollback.
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return responseModel.builder().response(be.getMessage()).build();

        } catch (Exception ex) {
            // Fallo técnico: rollback por @Transactional y propagamos
            throw new RepositorioException("Error al generar negociación: " + ex.getMessage());
        }
    }

    // ------------------ helpers ------------------

    private static String validarEntrada(NegociacionModel obj, String accion) {
        if (obj.getId_cliente() == null || obj.getId_contrato() == null || obj.getId_tipo() == null) {
            return "BAD_REQUEST: id_cliente, id_contrato e id_tipo son obligatorios";
        }
        if (!ACCIONES_VALIDAS.contains(accion)) {
            return "BAD_REQUEST: acciones_negociacion inválida";
        }
        List<FacturasNociacionItemModel> facturas = obj.getFacturas();
        if (facturas == null || facturas.isEmpty()) {
            return "BAD_REQUEST: Debe enviar al menos una factura";
        }
        return null;
    }

    private static LocalDate parseOrNull(String yyyyMMdd) throws DateTimeParseException {
        return (yyyyMMdd == null || yyyyMMdd.isBlank()) ? null : LocalDate.parse(yyyyMMdd);
    }

    private static String safeUpper(String s) {
        return s == null ? null : s.trim().toUpperCase();
    }

    private static String safeUpperOrDefault(String s, String def) {
        String t = (s == null || s.isBlank()) ? def : s;
        return t.trim().toUpperCase();
    }

    private static String format2(Double d) {
        if (d == null) return "0.00";
        return String.format(java.util.Locale.US, "%.2f", d);
    }

    private static NegocioException negocioError(String msg) {
        return new NegocioException(msg);
    }

    private static class NegocioException extends RuntimeException {
        public NegocioException(String message) { super(Objects.requireNonNullElse(message, "ERROR_LOGICO")); }
    }

    @Override
    public List<Negociacion_clientesRequest> busqueda_negociacion(BuscarNegociacion enviodatos) {
        //validar si viene vacio ponerle null
        if( enviodatos.getNombre_completo().isEmpty() ) {
            enviodatos.setNombre_completo(null);
        }

        if( enviodatos.getEstado().isEmpty() ) {
            enviodatos.setEstado(null);
        }
        try {
            String query = "CALL buscar_negociacion_clientes(?,?)";
            return this.jTemplate().query(query,
                    new BeanPropertyRowMapper<Negociacion_clientesRequest>(Negociacion_clientesRequest.class),
                    enviodatos.getNombre_completo(),enviodatos.getEstado()
            );
        } catch (Exception ex) {
          //imprimir en consola

            throw new RepositorioException("error en listado: "+ex.getMessage());
        }
    }

    @Override
    public List<ClienteContratoxServicio> clientesContratoxServicioContratados(Integer id_cliente) {
        try {
            String query = "SELECT contratos.id_contrato, tipo_servicio.descripcion, tipo_servicio.id_tipo " +
                    "FROM contratos " +
                    "INNER JOIN tipo_servicio ON contratos.id_tipo = tipo_servicio.id_tipo " +
                    "WHERE id_cliente = ?";

            return this.jTemplate().query(
                    query,
                    new BeanPropertyRowMapper<>(ClienteContratoxServicio.class),
                    id_cliente
            );
        } catch (Exception ex) {
            throw new RepositorioException("error en listado: " + ex.getMessage());
        }
    }

    @Override
    public responseModel modificarEstadoNegociacion(NegociacionModel obj) {
        try {
            // Update en baja_morosidad
            String sql1 = "UPDATE negociaciones SET estado = ? WHERE id_cliente  = ? AND id_contrato  = ? AND id_tipo =? AND id_negociacion = ?";
            int rows1 = this.jTemplate().update(sql1, obj.getEstado(), obj.getId_cliente(), obj.getId_contrato(), obj.getId_tipo(), obj.getId_negociacion());
            if (rows1 == 0) {
                return responseModel.builder()
                        .response("ERROR_LOGICO: No se encontró modificarEstadoNegociacion con id=" + obj.getId_negociacion() +
                                " para el cliente " + obj.getId_cliente())
                        .build();
            }

             String mensaje = "OK: Se modificó el estado de la negociación con id=" + obj.getId_negociacion() +
                    " para el cliente " + obj.getId_cliente();
            return responseModel.builder()
                    .response(mensaje)
                    .build();

        } catch (Exception ex) {
            throw new RepositorioException("Error al actualizar: " + ex.getMessage());
        }
    }

}
