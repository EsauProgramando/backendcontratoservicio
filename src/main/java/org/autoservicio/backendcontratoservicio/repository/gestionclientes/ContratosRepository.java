package org.autoservicio.backendcontratoservicio.repository.gestionclientes;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.autoservicio.backendcontratoservicio.config.IConfigGeneric;
import org.autoservicio.backendcontratoservicio.config.responseModel;
import org.autoservicio.backendcontratoservicio.excepciones.RepositorioException;
import org.autoservicio.backendcontratoservicio.interfaces.gestionclientes.IContratosRepo;
import org.autoservicio.backendcontratoservicio.model.gestionclientes.ContratosModel;
import org.autoservicio.backendcontratoservicio.response.ContratoResponse;
import org.autoservicio.backendcontratoservicio.response.ServicioContratadoRequest;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;
import java.util.List;
@Repository
public class ContratosRepository extends IConfigGeneric implements IContratosRepo {

    @Override
    public List<ServicioContratadoRequest> listadosContratos() {
        try {
            String query = "CALL sp_listar_contratos_servicios()";
            return this.jTemplate().query(query,
                    new BeanPropertyRowMapper<ServicioContratadoRequest>(ServicioContratadoRequest.class));
        } catch (Exception ex) {

            throw new RepositorioException("error en listado: "+ex.getMessage());
        }
    }


    @Override
    public ContratoResponse buscarpor_contratos(Integer id_cliente, Integer id_contrato) {
        String query = "CALL sp_buscarpor_contratos(?, ?)";
        try {
            return this.jTemplate().queryForObject(
                    query,
                    new BeanPropertyRowMapper<>(ContratoResponse.class),
                    id_cliente, id_contrato
            );
        } catch (Exception ex) {
            throw new RepositorioException("Error al ejecutar sp_buscarpor_contratos: " + ex.getMessage());
        }
    }

    @Override
    public List<ServicioContratadoRequest> buscar_servicio_pornrocontrato(Integer id_contrato) {
        String query = "CALL buscar_servicio_pornrocontrato(?)";
        try {
            return this.jTemplate().query(
                    query,
                    new BeanPropertyRowMapper<>(ServicioContratadoRequest.class),
                    id_contrato
            );
        } catch (Exception ex) {
            throw new RepositorioException("Error al ejecutar buscar_servicio_pornrocontrato: " + ex.getMessage());
        }
    }

    @Override
    public responseModel registrarcontrato(Integer op, ContratosModel obj) {
        try {
            // Convertir a JSON plano
            ObjectMapper mapper = new ObjectMapper();
            String contrato = mapper.writeValueAsString(obj);

            String sql = "CALL usp_registrar_contrato(?, ?)";

            String mensaje = this.jTemplate().queryForObject(sql, String.class, op, contrato);

            return responseModel.builder()
                    .response(mensaje)
                    .build();
        } catch (Exception ex) {
            throw new RepositorioException("Error al registrar kardex: " + ex.getMessage());
        }
    }

    @Override
    public responseModel generar_facturas_contrato(Integer id_contrato, Integer id_cliente) {
        try {
            // Llamada al procedimiento almacenado
            String sql = "CALL sp_generar_facturas_contrato(?, ?)";

            // Ejecutar el procedimiento y obtener el mensaje (si devuelve un String)
            String mensaje = this.jTemplate().queryForObject(sql, String.class, id_contrato, id_cliente);

            // Devolver responseModel
            return responseModel.builder()
                    .response(mensaje)
                    .build();
        } catch (Exception ex) {
            throw new RepositorioException("Error al generar facturas del contrato: " + ex.getMessage());
        }
    }
    @Override
    public List<ServicioContratadoRequest> buscar_servicio_x_codigo_factura(String codigo_factura) {
        String query = "CALL buscar_servicio_x_codigo_factura(?)";
        try {
            return this.jTemplate().query(
                    query,
                    new BeanPropertyRowMapper<>(ServicioContratadoRequest.class),
                    codigo_factura
            );
        } catch (Exception ex) {
            throw new RepositorioException("Error al ejecutar buscar_servicio_pornrocontrato: " + ex.getMessage());
        }
    }


}
