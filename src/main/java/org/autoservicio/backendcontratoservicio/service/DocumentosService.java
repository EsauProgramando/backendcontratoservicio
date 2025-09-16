package org.autoservicio.backendcontratoservicio.service;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.autoservicio.backendcontratoservicio.interfaces.IDocumentoRepo;
import org.autoservicio.backendcontratoservicio.interfaces.IDocumentoSercuencia;
import org.autoservicio.backendcontratoservicio.model.DocumentoSecuencia;
import org.autoservicio.backendcontratoservicio.model.documentoModel;
import org.autoservicio.backendcontratoservicio.repository.gestionclientes.ContratosRepository;
import org.autoservicio.backendcontratoservicio.request.DocumentoRequestDTO;
import org.autoservicio.backendcontratoservicio.response.ServicioContratadoRequest;
import org.autoservicio.backendcontratoservicio.util.NumeroALetrasUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.*;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;
import java.util.stream.Stream;
import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

@Service
public class DocumentosService {
//    @Autowired
//    private PedidoRepository pedidoRepository;
//    @Autowired private PedidoProductoRepository pedidoProductoRepository;
//    @Autowired private CuponUsoRepository cuponUsoRepository;
//    @Autowired private CuponProductoRepository cuponProductoRepository;
//    @Autowired private PagoPedidoRepository pagoPedidoRepository;
    @Autowired private ContratosRepository contratoRepository;
    @Autowired
    private GoogleDriveService googleDriveService;

    @Autowired
    private IDocumentoRepo documentoRepository;
    @Autowired
    private IDocumentoSercuencia secuenciaRepository;


    @Transactional
    public documentoModel crearDocumento(DocumentoRequestDTO dto) {
        Optional<documentoModel> existenteOpt = documentoRepository.findByIdContrato(dto.getIdContrato());

        if (existenteOpt.isPresent()) {
            documentoModel existente = existenteOpt.get();

            if ("PENDIENTE".equalsIgnoreCase(existente.getEstado())) {
                // Actualizar campos editables
                existente.setTipoComprobante(dto.getTipoComprobante());
                existente.setTipoDocumentoCliente(dto.getTipoDocumentoCliente());
                existente.setNumeroDocumentoCliente(dto.getNumeroDocumentoCliente());
                existente.setNombreCliente(dto.getNombreCliente());
                existente.setRazonSocialCliente(dto.getRazonSocialCliente());
                existente.setDireccionCliente(dto.getDireccionCliente());

                return documentoRepository.save(existente);
            }
            // Si ya existe y no está en estado PENDIENTE, no se modifica
            throw new IllegalStateException("El documento ya fue enviado o procesado y no puede modificarse.");
        }

        // Si no existe → generar serie y correlativo
        DocumentoSecuencia secuencia = secuenciaRepository
                .findTopByTipoComprobanteOrderBySerieActualDesc(dto.getTipoComprobante())
                .orElseThrow(() -> new IllegalStateException("No se encontró serie inicial para el tipo de comprobante " + dto.getTipoComprobante()));

        int nuevoCorrelativo = secuencia.getCorrelativoActual() + 1;

        if (nuevoCorrelativo > 9999) {
            String nuevaSerie = generarNuevaSerie(secuencia.getSerieActual());
            secuencia = DocumentoSecuencia.builder()
                    .tipoComprobante(dto.getTipoComprobante())
                    .serieActual(nuevaSerie)
                    .correlativoActual(1)
                    .build();
        } else {
            secuencia.setCorrelativoActual(nuevoCorrelativo);
        }

        secuenciaRepository.save(secuencia);
        String correlativoStr = String.format("%04d", secuencia.getCorrelativoActual());

        documentoModel nuevo = documentoModel.builder()
                .idContrato(dto.getIdContrato())
                .tipoComprobante(dto.getTipoComprobante())
                .tipoDocumentoCliente(dto.getTipoDocumentoCliente())
                .numeroDocumentoCliente(dto.getNumeroDocumentoCliente())
                .nombreCliente(dto.getNombreCliente())
                .razonSocialCliente(dto.getRazonSocialCliente())
                .direccionCliente(dto.getDireccionCliente())
                .serie(secuencia.getSerieActual())
                .correlativo(correlativoStr)
                .estado("PENDIENTE")
                .build();

        return documentoRepository.save(nuevo);
    }


    private String generarNuevaSerie(String serieActual) {
        String letra = serieActual.substring(0, 1);
        int numero = Integer.parseInt(serieActual.substring(1));
        return letra + String.format("%03d", numero + 1);
    }

//    public String generarNotaVenta(String idPedido) throws Exception {
//        Pedido pedido = pedidoRepository.findById(idPedido)
//                .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));
//
//        List<PedidoProducto> productos = pedidoProductoRepository.findByPedido(pedido);
//
//        Optional<CuponUso> cuponUsoOpt = cuponUsoRepository.findByPedidoAndAplicado(pedido, true);
//        Cupon cupon = cuponUsoOpt.map(CuponUso::getCupon).orElse(null);
//        List<CuponProducto> productosConDescuento = cupon != null ?
//                cuponProductoRepository.findAllByCupon(cupon) : new ArrayList<>();
//
//        Optional<PagoPedido> pago = pagoPedidoRepository.findFirstByPedido(pedido);
//
//        BigDecimal totalExonerada = BigDecimal.ZERO;
//
//        Rectangle smallPage = new Rectangle(300f, 600f); // tamaño más pequeño
//        Document document = new Document(smallPage, 20f, 20f, 20f, 20f); // márgenes más estrechos
//        String nombreArchivo = "NPV-" + idPedido + ".pdf";
//
//        java.io.File tempFile = new java.io.File(System.getProperty("java.io.tmpdir") + "/NPV-" + idPedido + ".pdf");
//        PdfWriter.getInstance(document, new FileOutputStream(tempFile));
//
//        document.open();
//
//        Font bold = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 9);
//        Font regular = FontFactory.getFont(FontFactory.HELVETICA, 9);
//
//        Paragraph empresa = new Paragraph("AGO TECNOVA E.I.R.L\nRUC: 20607546364\nPRO.LA MERCED NRO. 445 BAR. LA MERCED, MIRAFLORES, LIMA, PERÚ\nCel: +51923088747\nCorreo: administracion@bellacuret.com", bold);
//        empresa.setAlignment(Element.ALIGN_CENTER);
//        document.add(empresa);
//
//        Paragraph notaVenta = new Paragraph("\nNOTA DE VENTA\nN°: " + idPedido, bold);
//        notaVenta.setAlignment(Element.ALIGN_CENTER);
//        document.add(notaVenta);
//        document.add(new Paragraph("\n"));
//
//        LocalDateTime ahora = LocalDateTime.now();
//        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
//
//        document.add(new Paragraph("\nFECHA EMISIÓN: " + fmt.format(ahora), bold));
//        document.add(new Paragraph("MONEDA: SOLES", regular));
//
//        PdfPTable tabla = new PdfPTable(4);
//        tabla.setWidths(new int[]{1, 5, 2, 2});
//        tabla.setSpacingBefore(10f);
//        tabla.setWidthPercentage(100f);
//
//        // Encabezados
//        Stream.of("CANT", "DESCRIPCIÓN", "P/U", "TOTAL")
//                .forEach(header -> {
//                    PdfPCell cell = new PdfPCell(new Phrase(header, bold));
//                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//                    cell.setPadding(5f);
//                    tabla.addCell(cell);
//                });
//
//        for (PedidoProducto pp : productos) {
//            Producto prod = pp.getProducto();
//            String desc = prod.getProductoMaestro().getNombre() + " " + prod.getPresentacion() + " - " + prod.getTipoPresentacion().getDescripcion();
//            BigDecimal pu = prod.getPrecio();
//            if (Boolean.TRUE.equals(pp.isTieneDescuento()) && cupon != null) {
//                boolean aplicaDescuento = productosConDescuento.stream()
//                        .anyMatch(cp -> cp.getProducto().getIdProducto().equals(prod.getIdProducto()));
//                if (aplicaDescuento || productosConDescuento.isEmpty()) {
//                    BigDecimal porcentaje = cupon.getDescuento().divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
//                    pu = pu.subtract(pu.multiply(porcentaje));
//                }
//            }
//            BigDecimal total = pu.multiply(BigDecimal.valueOf(pp.getCantidad()));
//
//            tabla.addCell(createCell(pp.getCantidad().toString(), regular));
//            tabla.addCell(createCell(desc, regular));
//            tabla.addCell(createCell(pu.setScale(2, RoundingMode.HALF_UP).toString(), regular));
//            tabla.addCell(createCell(total.setScale(2, RoundingMode.HALF_UP).toString(), regular));
//
//            totalExonerada = totalExonerada.add(total);
//        }
//
//        document.add(tabla);
//
//        // Secciones en bold solo el label
//        BigDecimal exonerada = BigDecimal.ZERO;
//        BigDecimal gravada = totalExonerada.divide(BigDecimal.valueOf(1.18), 2, RoundingMode.HALF_UP);
//        BigDecimal igv = totalExonerada.subtract(gravada);
//
//        document.add(negritaConValor("EXONERADA S/: ", exonerada));
//        document.add(negritaConValor("GRAVADA S/: ", gravada));
//        document.add(negritaConValor("IGV(18%) S/: ", igv));
//        document.add(negritaConValor("TOTAL S/: ", totalExonerada));
//
//
//        document.add(new Paragraph("\n" + "IMPORTE EN LETRAS: ", bold));
//        document.add(new Paragraph(convertirNumeroALetras(totalExonerada), regular));
//
//        document.add(new Paragraph("\nCONDICIÓN DE PAGO: ", bold));
//        document.add(new Paragraph("CONTADO", regular));
//
//        if (pago.isPresent()) {
//            document.add(new Paragraph("MEDIOS DE PAGO:", bold));
//            document.add(new Paragraph(pago.get().getTipoPago().getDescripcion() + " - S/ " + totalExonerada, regular));
//        }
//
//        document.close();
//
//        // ID de la carpeta "Tickets de Venta" en Google Drive
//        String folderId = "1BYPTbtpYChUOuBr21UEy3RCnlhPvAU8b";
//
//        String driveLink = googleDriveService.uploadFile(tempFile, folderId);
//
//        tempFile.delete();
//
//        return driveLink;
//    }

    // Método para generar el PDF
    public String generarFacturaPdf(Map<String, Object> parametros, List<Map<String, Object>> detalles, String nombreArchivo) throws Exception {
        // 1. Cargar el JRXML desde resources
        InputStream reportStream = getClass().getResourceAsStream("/jasper/rpteFacturaElectronicaA4FormatoCompleto.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(reportStream);

        // 2. Fuente de datos para los detalles (tablas)
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(detalles);

        // 3. Llenar el reporte
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, dataSource);

        // 4. Ruta destino (ajusta si deseas otra ruta)
        String outputPath = "C:/pdf-facturacion/" + nombreArchivo;

        // 5. Exportar a PDF
        JasperExportManager.exportReportToPdfFile(jasperPrint, outputPath);

        return outputPath;
    }

    public String generarFactura(Integer idContrato) throws Exception {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        List<ServicioContratadoRequest> pedido = contratoRepository.buscar_servicio_pornrocontrato(idContrato);

        if(pedido.isEmpty()) {
            throw new RuntimeException("No se encontró el detalle del contrato con ID: "+idContrato );
        }

        List<Map<String, Object>> dataList = new ArrayList<>();




        Double suma = 0.0;
        for (int i = 0; i < pedido.size(); i++) {
            System.out.println("SUBTOTAL: " + pedido.get(i).getPrecio_mensual());
            suma = suma + pedido.get(i).getPrecio_mensual(); // usando +
            System.out.println("SUMA: " + suma);
        }
        Double igv = suma * 0.18;
        documentoModel documento = documentoRepository.findByIdContrato(idContrato)
                .orElseThrow(() -> new RuntimeException("Documento no encontrado"));
        // Validar que sea una factura (debe tener RUC = "6")
        if(documento.getTipoDocumentoCliente() == null || !documento.getTipoDocumentoCliente().equals("6")) {
            throw new RuntimeException("El documento no tiene un RUC válido para generar factura, por favor verifique los datos del documento.");
        }

        Map<String, Object> row = new HashMap<>();
        row.put("empr_ruc", "20607546364");
        row.put("empr_nombre_comercial", "AGO TECNOVA E.I.R.L");
        row.put("empr_razon_social", "AGO TECNOVA E.I.R.L");
        row.put("empr_direccion_fiscal", "PRO.LA MERCED NRO. 445 BAR. LA MERCED");
        row.put("empr_direccion_sucursal", "PRO.LA MERCED NRO. 445 BAR. LA MERCED");
        row.put("empr_telefono", "+51956324064");
        row.put("empr_pagina_web", "https://www.agotecnovaperu.com/");
        row.put("empr_numero_autorizacion", "AUT123456");
        row.put("empr_imagen", "");
        row.put("empr_pdf_marca_agua", "");
        row.put("empr_pdf_texto_inferior", "Gracias por su preferencia");
        row.put("empr_pdf_eslogan", "Planes flexibles, Cobertura local");
        row.put("tico_descripcion", "Factura Electrónica");
        row.put("comp_numero_comprobante", documento.getSerie()  + "-" + documento.getCorrelativo());
        row.put("comp_descripcion_cliente", documento.getNombreCliente());
        row.put("comp_direccion_cliente", documento.getDireccionCliente() != null ? documento.getDireccionCliente() : "-");
        row.put("clie_numero_documento", documento.getNumeroDocumentoCliente());
        row.put("comp_descuento_global", 0);
        row.put("comp_fecha_emicion", Timestamp.valueOf(LocalDateTime.now()));
        row.put("comp_descripcion_moneda", "SOLES");
        row.put("comp_simbolo_moneda", "S/");
        row.put("comp_codigo_hash", "ABC123HASH456");
        String qr = "20607546364|"+documento.getTipoComprobante()+"|"+documento.getSerie()+"|"+idContrato+"|"+igv+"|"+suma+"|"+LocalDateTime.now().format(formatter)+"|6|"+documento.getNumeroDocumentoCliente();
        row.put("comp_cadena_qr", qr);
        System.out.println("Cadena QR: [" + qr + "]");
        row.put("comp_condicion_pago", "Contado");
        row.put("comp_estado", "Aceptado");
        row.put("otros_tributos", 0.00);
        row.put("comp_id", 1L);
        row.put("itco_descuento", new BigDecimal("0.0"));
        row.put("itco_tipo_igv", 1);
        //row.put("itco_igv", new BigDecimal("9.0"));
        row.put("icbper", 0.00);
        row.put("exonerado_igv", "NO");
        row.put("priorizar_despacho", false);
        row.put("observaciones", "");

        //DATOS PRODUCTO 1
        for (int i = 0; i < pedido.size(); i++) {
            ServicioContratadoRequest pp = pedido.get(i);

            if (i > 0) row = new HashMap<>(row); // Clonar para múltiples filas

            row.put("itco_codigo_interno", pp.getId_contrato());
            row.put("itco_cantidad", 1);
            row.put("itco_unidad_medida", "UND");
            row.put("itco_descripcion_completa",
                    pp.getId_contrato() + " " + pp.getId_cliente());
            row.put("itco_precio_unitario", pp.getPrecio_mensual());

            dataList.add(row);
        }

        // ✅ Parámetros tipo <parameter>
        Map<String, Object> parametros = new HashMap<>();
        // Leer recurso desde classpath
        String pathRelativo = "/static/images/logo-bc.png";
        URL urlImagen = getClass().getResource(pathRelativo);
        if (urlImagen == null) {
            throw new FileNotFoundException("No se encontró la imagen en " + pathRelativo);
        }
        parametros.put("urlImagen", urlImagen.toString()); // <-- IMPORTANTE: lo pasas como String

        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(dataList);

        InputStream inputStream = getClass().getResourceAsStream("/jasper/rpteFacturaElectronicaA4FormatoCompleto.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, dataSource);

        // Nombre temporal del archivo PDF
        String nombreArchivo = documento.getSerie()  + "-" + documento.getCorrelativo() + ".pdf";
        java.io.File tempFile = new java.io.File(System.getProperty("java.io.tmpdir") + "/" + nombreArchivo);
        JasperExportManager.exportReportToPdfFile(jasperPrint, tempFile.getAbsolutePath());

        // Subir a Google Drive
        String folderId = "1B65s8ET20cKwIQB3OYGg7TT4cUi_OgQB";
        String driveLink = googleDriveService.uploadFile(tempFile, folderId);

        // Actualizar el campo urlPdf en el documento
        documento.setUrlPdf(driveLink);
        documentoRepository.save(documento);

        tempFile.delete();

        return driveLink;
    }


    public String generarFacturaManual(Integer idContrato, String ruc, String razonSocial) throws Exception {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        List<ServicioContratadoRequest> pedido = contratoRepository.buscar_servicio_pornrocontrato(idContrato);

        if(pedido.isEmpty()) {
            throw new RuntimeException("No se encontró el detalle del contrato con ID: "+idContrato );
        }

        List<Map<String, Object>> dataList = new ArrayList<>();

        Double suma = 0.0;
        for (int i = 0; i < pedido.size(); i++) {
            System.out.println("SUBTOTAL: " + pedido.get(i).getPrecio_mensual());
            suma = suma + pedido.get(i).getPrecio_mensual(); // usando +
            System.out.println("SUMA: " + suma);
        }
        Double igv = suma * 0.18;
        documentoModel documento = documentoRepository.findByIdContrato(idContrato)
                .orElseThrow(() -> new RuntimeException("Documento no encontrado"));

        Map<String, Object> row = new HashMap<>();
        row.put("empr_ruc", "20607546364");
        row.put("empr_nombre_comercial", "AGO TECNOVA E.I.R.L");
        row.put("empr_razon_social", "AGO TECNOVA E.I.R.L");
        row.put("empr_direccion_fiscal", "PRO.LA MERCED NRO. 445 BAR. LA MERCED");
        row.put("empr_direccion_sucursal", "PRO.LA MERCED NRO. 445 BAR. LA MERCED");
        row.put("empr_telefono", "+51956324064");
        row.put("empr_pagina_web", "https://www.agotecnovaperu.com/");
        row.put("empr_imagen", "");
        row.put("empr_pdf_marca_agua", "");
        row.put("empr_pdf_texto_inferior", "Gracias por su preferencia");
        row.put("empr_pdf_eslogan", "Planes flexibles, Cobertura local");
        row.put("tico_descripcion", "Factura Electrónica");
        row.put("comp_numero_comprobante", documento.getSerie()  + "-" + documento.getCorrelativo());
        row.put("comp_descripcion_cliente", razonSocial);
        row.put("comp_direccion_cliente", "-");
        row.put("clie_numero_documento", ruc);
        row.put("comp_descuento_global", 0);
        row.put("comp_fecha_emicion", Timestamp.valueOf(LocalDateTime.now()));
        row.put("comp_descripcion_moneda", "SOLES");
        row.put("comp_simbolo_moneda", "S/");
        row.put("comp_codigo_hash", "ABC123HASH456");
        String qr = "20607546364|"+documento.getTipoComprobante()+"|"+documento.getSerie()+"|"+idContrato+"|"+ String.format("%.2f", igv)+"|"+suma+"|"+LocalDateTime.now().format(formatter)+"|6|"+ruc;
        row.put("comp_cadena_qr", qr);
        System.out.println("Cadena QR: [" + qr + "]");
        row.put("comp_condicion_pago", "Contado");
        row.put("comp_estado", "Aceptado");
        row.put("otros_tributos", 0.00);
        row.put("comp_id", 1L);
        row.put("itco_descuento", new BigDecimal("0.0"));
        row.put("itco_tipo_igv", 1);
        //row.put("itco_igv", new BigDecimal("9.0"));
        row.put("icbper", 0.00);
        row.put("exonerado_igv", "NO");
        row.put("priorizar_despacho", false);
        row.put("observaciones", "");

        //DATOS PRODUCTO 1
        for (int i = 0; i < pedido.size(); i++) {
            ServicioContratadoRequest pp = pedido.get(i);

            if (i > 0) row = new HashMap<>(row); // Clonar para múltiples filas

            row.put("itco_codigo_interno", pp.getId_contrato());
            row.put("itco_cantidad", 1);
            row.put("itco_unidad_medida", "UND");
            row.put("itco_descripcion_completa",
                    pp.getId_contrato() + " " + pp.getId_cliente());
            row.put("itco_precio_unitario", pp.getPrecio_mensual());

            dataList.add(row);
        }

        // ✅ Parámetros tipo <parameter>
        Map<String, Object> parametros = new HashMap<>();
        // Leer recurso desde classpath
        String pathRelativo = "/static/images/logo-bc.png";
        URL urlImagen = getClass().getResource(pathRelativo);
        if (urlImagen == null) {
            throw new FileNotFoundException("No se encontró la imagen en " + pathRelativo);
        }
        parametros.put("urlImagen", urlImagen.toString()); // <-- IMPORTANTE: lo pasas como String

        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(dataList);

        InputStream inputStream = getClass().getResourceAsStream("/jasper/rpteFacturaElectronicaA4FormatoCompleto.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, dataSource);

        // Nombre temporal del archivo PDF
        String nombreArchivo = documento.getSerie()  + "-" + documento.getCorrelativo() + ".pdf";
        java.io.File tempFile = new java.io.File(System.getProperty("java.io.tmpdir") + "/" + nombreArchivo);
        JasperExportManager.exportReportToPdfFile(jasperPrint, tempFile.getAbsolutePath());

        // Subir a Google Drive
        String folderId = "1B65s8ET20cKwIQB3OYGg7TT4cUi_OgQB";
        String driveLink = googleDriveService.uploadFile(tempFile, folderId);

        // Actualizar el campo urlPdf en el documento
        documento.setUrlPdf(driveLink);
        documentoRepository.save(documento);

        tempFile.delete();

        return driveLink;
    }


    public String generarBoleta(Integer idContrato) throws Exception {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        List<ServicioContratadoRequest> pedido = contratoRepository.buscar_servicio_pornrocontrato(idContrato);

        if(pedido.isEmpty()) {
            throw new RuntimeException("No se encontró el detalle del contrato con ID: "+idContrato );
        }

        List<Map<String, Object>> dataList = new ArrayList<>();


        // Validar que sea una boleta (debe tener DNI = "1")

        Double suma = 0.0;
        for (int i = 0; i < pedido.size(); i++) {
            System.out.println("SUBTOTAL: " + pedido.get(i).getPrecio_mensual());
            suma = suma + pedido.get(i).getPrecio_mensual(); // usando +
            System.out.println("SUMA: " + suma);
        }
        Double igv = suma * 0.18;
        documentoModel documento = documentoRepository.findByIdContrato(idContrato)
                .orElseThrow(() -> new RuntimeException("Documento no encontrado"));

        if(documento.getTipoDocumentoCliente() == null || !documento.getTipoDocumentoCliente().equals("1")) {
            throw new RuntimeException("El documento no tiene un DNI válido para generar boleta, por favor verifique los datos del documento.");
        }
        Map<String, Object> row = new HashMap<>();
        row.put("empr_ruc", "20607546364");
        row.put("empr_nombre_comercial", "AGO TECNOVA E.I.R.L");
        row.put("empr_razon_social", "AGO TECNOVA E.I.R.L");
        row.put("empr_direccion_fiscal", "PRO.LA MERCED NRO. 445 BAR. LA MERCED");
        row.put("empr_direccion_sucursal", "PRO.LA MERCED NRO. 445 BAR. LA MERCED");
        row.put("empr_telefono", "+51956324064");
        row.put("empr_pagina_web", "https://www.agotecnovaperu.com/");
        row.put("empr_imagen", "");
        row.put("empr_pdf_marca_agua", "");
        row.put("empr_pdf_texto_inferior", "Gracias por su preferencia");
        row.put("empr_pdf_eslogan", "Planes flexibles, Cobertura local");
        row.put("tico_descripcion", "Boleta Electrónica");
        row.put("comp_numero_comprobante", documento.getSerie()  + "-" + documento.getCorrelativo());
        row.put("comp_descripcion_cliente", documento.getNombreCliente() != null ? documento.getNombreCliente() : "CLIENTES VARIOS");
        row.put("comp_direccion_cliente", documento.getDireccionCliente() != null ? documento.getDireccionCliente() : "-");
        row.put("clie_numero_documento", documento.getNumeroDocumentoCliente() != null ? documento.getNumeroDocumentoCliente() : "99999999");
        row.put("comp_descuento_global", 0);
        row.put("comp_fecha_emicion", Timestamp.valueOf(LocalDateTime.now()));
        row.put("comp_descripcion_moneda", "SOLES");
        row.put("comp_simbolo_moneda", "S/");
        String qr = "20607546364|"+documento.getTipoComprobante()+"|"+documento.getSerie()+"|"+idContrato+"|"+igv+"|"+suma+"|"+LocalDateTime.now().format(formatter)+"|6|"+documento.getNumeroDocumentoCliente();
        row.put("comp_cadena_qr", qr);
        System.out.println("Cadena QR: [" + qr + "]");
        row.put("comp_condicion_pago", "Contado");
        row.put("comp_estado", "Aceptado");
        row.put("otros_tributos", 0.00);
        row.put("comp_id", 1L);
        row.put("itco_descuento", new BigDecimal("0.0"));
        row.put("itco_tipo_igv", 1);
        //row.put("itco_igv", new BigDecimal("9.0"));
        row.put("icbper", 0.00);
        row.put("exonerado_igv", "NO");
        row.put("priorizar_despacho", false);
        row.put("observaciones", "");

        //DATOS PRODUCTO 1
        for (int i = 0; i < pedido.size(); i++) {
            ServicioContratadoRequest pp = pedido.get(i);

            if (i > 0) row = new HashMap<>(row); // Clonar para múltiples filas

            row.put("itco_codigo_interno", pp.getId_contrato());
            row.put("itco_cantidad", 1);
            row.put("itco_unidad_medida", "UND");
            row.put("itco_descripcion_completa",
                    pp.getId_contrato() + " " + pp.getId_cliente());
            row.put("itco_precio_unitario", pp.getPrecio_mensual());

            dataList.add(row);
        }

        // ✅ Parámetros tipo <parameter>
        Map<String, Object> parametros = new HashMap<>();
        // Leer recurso desde classpath
        String pathRelativo = "/static/images/logo-bc.png";
        URL urlImagen = getClass().getResource(pathRelativo);
        if (urlImagen == null) {
            throw new FileNotFoundException("No se encontró la imagen en " + pathRelativo);
        }
        parametros.put("urlImagen", urlImagen.toString()); // <-- IMPORTANTE: lo pasas como String

        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(dataList);

        InputStream inputStream = getClass().getResourceAsStream("/jasper/rpteFacturaElectronicaA4FormatoCompleto.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, dataSource);

        // Nombre temporal del archivo PDF
        String nombreArchivo = documento.getSerie()  + "-" + documento.getCorrelativo() + ".pdf";
        java.io.File tempFile = new java.io.File(System.getProperty("java.io.tmpdir") + "/" + nombreArchivo);
        JasperExportManager.exportReportToPdfFile(jasperPrint, tempFile.getAbsolutePath());

        // Subir a Google Drive
        String folderId = "1B65s8ET20cKwIQB3OYGg7TT4cUi_OgQB";
        String driveLink = googleDriveService.uploadFile(tempFile, folderId);

        // Actualizar el campo urlPdf en el documento
        documento.setUrlPdf(driveLink);
        documentoRepository.save(documento);

        tempFile.delete();

        return driveLink;
    }

    public String generarBoletaManual(Integer idContrato, String dni, String nombre) throws Exception {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        List<ServicioContratadoRequest> pedido = contratoRepository.buscar_servicio_pornrocontrato(idContrato);

        if(pedido.isEmpty()) {
            throw new RuntimeException("No se encontró el detalle del contrato con ID: "+idContrato );
        }

        List<Map<String, Object>> dataList = new ArrayList<>();

        Double suma = 0.0;
        for (int i = 0; i < pedido.size(); i++) {
            System.out.println("SUBTOTAL: " + pedido.get(i).getPrecio_mensual());
            suma = suma + pedido.get(i).getPrecio_mensual(); // usando +
            System.out.println("SUMA: " + suma);
        }
        Double igv = suma * 0.18;

        documentoModel documento = documentoRepository.findByIdContrato(idContrato)
                .orElseThrow(() -> new RuntimeException("Documento no encontrado"));

        if(documento.getTipoDocumentoCliente() == null || !documento.getTipoDocumentoCliente().equals("1")) {
            throw new RuntimeException("El documento no tiene un DNI válido para generar boleta, por favor verifique los datos del documento.");
        }
        Map<String, Object> row = new HashMap<>();
        row.put("empr_ruc", "20607546364");
        row.put("empr_nombre_comercial", "AGO TECNOVA E.I.R.L");
        row.put("empr_razon_social", "AGO TECNOVA E.I.R.L");
        row.put("empr_direccion_fiscal", "PRO.LA MERCED NRO. 445 BAR. LA MERCED");
        row.put("empr_direccion_sucursal", "PRO.LA MERCED NRO. 445 BAR. LA MERCED");
        row.put("empr_telefono", "+51956324064");
        row.put("empr_pagina_web", "https://www.agotecnovaperu.com/");
        row.put("empr_imagen", "");
        row.put("empr_pdf_marca_agua", "");
        row.put("empr_pdf_texto_inferior", "Gracias por su preferencia");
        row.put("empr_pdf_eslogan", "Planes flexibles, Cobertura local");
        row.put("tico_descripcion", "Boleta Electrónica");
        row.put("comp_numero_comprobante", documento.getSerie()  + "-" + documento.getCorrelativo());
        row.put("comp_descripcion_cliente", nombre);
        row.put("comp_direccion_cliente", "-");
        row.put("clie_numero_documento", dni);
        row.put("comp_descuento_global", 0);
        row.put("comp_fecha_emicion", Timestamp.valueOf(LocalDateTime.now()));
        row.put("comp_descripcion_moneda", "SOLES");
        row.put("comp_simbolo_moneda", "S/");
        String qr = "20607546364|"+documento.getTipoComprobante()+"|"+documento.getSerie()+"|"+idContrato+"|"+igv+"|"+suma+"|"+LocalDateTime.now().format(formatter)+"|1|"+dni;
        row.put("comp_cadena_qr", qr);
        System.out.println("Cadena QR: [" + qr + "]");
        row.put("comp_condicion_pago", "Contado");
        row.put("comp_estado", "Aceptado");
        row.put("otros_tributos", 0.00);
        row.put("comp_id", 1L);
        row.put("itco_descuento", new BigDecimal("0.0"));
        row.put("itco_tipo_igv", 1);
        //row.put("itco_igv", new BigDecimal("9.0"));
        row.put("icbper", 0.00);
        row.put("exonerado_igv", "NO");
        row.put("priorizar_despacho", false);
        row.put("observaciones", "");

        //DATOS PRODUCTO 1
        for (int i = 0; i < pedido.size(); i++) {
            ServicioContratadoRequest pp = pedido.get(i);

            if (i > 0) row = new HashMap<>(row); // Clonar para múltiples filas

            row.put("itco_codigo_interno", pp.getId_contrato());
            row.put("itco_cantidad", 1);
            row.put("itco_unidad_medida", "UND");
            row.put("itco_descripcion_completa",
                    pp.getId_contrato() + " " + pp.getId_cliente());
            row.put("itco_precio_unitario", pp.getPrecio_mensual());

            dataList.add(row);
        }

        // ✅ Parámetros tipo <parameter>
        Map<String, Object> parametros = new HashMap<>();
        // Leer recurso desde classpath
        String pathRelativo = "/static/images/logo-bc.png";
        URL urlImagen = getClass().getResource(pathRelativo);
        if (urlImagen == null) {
            throw new FileNotFoundException("No se encontró la imagen en " + pathRelativo);
        }
        parametros.put("urlImagen", urlImagen.toString()); // <-- IMPORTANTE: lo pasas como String

        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(dataList);

        InputStream inputStream = getClass().getResourceAsStream("/jasper/rpteFacturaElectronicaA4FormatoCompleto.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, dataSource);

        // Nombre temporal del archivo PDF
        String nombreArchivo = documento.getSerie()  + "-" + documento.getCorrelativo() + ".pdf";
        java.io.File tempFile = new java.io.File(System.getProperty("java.io.tmpdir") + "/" + nombreArchivo);
        JasperExportManager.exportReportToPdfFile(jasperPrint, tempFile.getAbsolutePath());

        // Subir a Google Drive
        String folderId = "1B65s8ET20cKwIQB3OYGg7TT4cUi_OgQB";
        String driveLink = googleDriveService.uploadFile(tempFile, folderId);

        // Actualizar el campo urlPdf en el documento
        documento.setUrlPdf(driveLink);
        documentoRepository.save(documento);

        tempFile.delete();

        return driveLink;
    }


    private Paragraph negritaConValor(String label, BigDecimal valor) {
        Phrase frase = new Phrase();
        frase.add(new Chunk(label, FontFactory.getFont(FontFactory.HELVETICA_BOLD, 9)));
        frase.add(new Chunk(valor.setScale(2, RoundingMode.HALF_UP).toString(), FontFactory.getFont(FontFactory.HELVETICA, 9)));
        return new Paragraph(frase);
    }

    private PdfPCell createCell(String texto, Font fuente) {
        PdfPCell cell = new PdfPCell(new Phrase(texto, fuente));
        cell.setPadding(5f);
        return cell;
    }

    private String convertirNumeroALetras(BigDecimal monto) {
        return NumeroALetrasUtil.convertir(monto); // debe estar ya creada
    }
}
