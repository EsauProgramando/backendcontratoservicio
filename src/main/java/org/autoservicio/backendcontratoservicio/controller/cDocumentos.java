package org.autoservicio.backendcontratoservicio.controller;

import org.autoservicio.backendcontratoservicio.model.documentoModel;
import org.autoservicio.backendcontratoservicio.request.DocumentoRequestDTO;
import org.autoservicio.backendcontratoservicio.response.ResponseValueDTO;
import org.autoservicio.backendcontratoservicio.service.DocumentosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/documentos")
@CrossOrigin
public class cDocumentos {

    @Autowired
    private DocumentosService documentoService;

    @PostMapping("/crear")
    public ResponseEntity<ResponseValueDTO<documentoModel>> crearDocumento(@RequestBody DocumentoRequestDTO dto) {
        try {
            documentoModel documento = documentoService.crearDocumento(dto);

            return ResponseEntity.ok(
                    ResponseValueDTO.<documentoModel>builder()
                            .idResultado(1)
                            .resultado("Documento registrado correctamente.")
                            .value(documento)
                            .values(null)
                            .build()
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    ResponseValueDTO.<documentoModel>builder()
                            .idResultado(-1)
                            .resultado(e.getMessage())
                            .value(null)
                            .values(null)
                            .build()
            );
        }
    }

//    @GetMapping("/nota-venta/{idPedido}")
//    public ResponseEntity<String> generarNotaVenta(@PathVariable String idPedido) {
//        try {
//            String url = documentoService.generarNotaVenta(idPedido);
//            return ResponseEntity.ok(url);
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
//        }
//    }

    @GetMapping("/factura/{idContrato}")
    public ResponseEntity<ResponseValueDTO<String>> generarFactura(@PathVariable Integer idContrato) {
        try {
            // Llamar al service para generar el PDF (ya guarda en disco)
            String url = documentoService.generarFactura(idContrato);

            ResponseValueDTO<String> response = new ResponseValueDTO<>(
                    1,
                    "Factura generada correctamente",
                    url,
                    null
            );

            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            ResponseValueDTO<String> error = new ResponseValueDTO<>(
                    -1,
                    "Error al generar la factura: " + ex.getMessage(),
                    null,
                    null
            );

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @GetMapping("/factura-manual")
    public ResponseEntity<ResponseValueDTO<String>> generarFacturaManual(@RequestParam Integer idContrato, @RequestParam String ruc, @RequestParam String razonSocial) {
        try {

            razonSocial = razonSocial.toUpperCase();

            if(!(ruc != null && ruc.matches("\\d{11}"))) {
                throw new RuntimeException("El RUC no tiene un formato válido.");
            }

            String url = documentoService.generarFacturaManual(idContrato, ruc, razonSocial);

            ResponseValueDTO<String> response = new ResponseValueDTO<>(
                    1,
                    "Factura generada correctamente",
                    url,
                    null
            );

            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            ResponseValueDTO<String> error = new ResponseValueDTO<>(
                    -1,
                    "Error al generar la factura: " + ex.getMessage(),
                    null,
                    null
            );

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @GetMapping("/boleta/{idContrato}")
    public ResponseEntity<ResponseValueDTO<String>> generarBoleta(@PathVariable Integer idContrato) {
        try {
            String url = documentoService.generarBoleta(idContrato);

            ResponseValueDTO<String> response = new ResponseValueDTO<>(
                    1,
                    "Boleta generada correctamente",
                    url,
                    null
            );

            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            ResponseValueDTO<String> error = new ResponseValueDTO<>(
                    -1,
                    "Error al generar la boleta: " + ex.getMessage(),
                    null,
                    null
            );

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @GetMapping("/boleta-manual")
    public ResponseEntity<ResponseValueDTO<String>> generarBoletaManual(@RequestParam Integer idContrato, @RequestParam String dni, @RequestParam String nombre) {
        try {

            nombre = nombre.toUpperCase();

            if(!(dni != null && dni.matches("\\d{8}"))) {
                throw new RuntimeException("El DNI no tiene un formato válido.");
            }

            String url = documentoService.generarBoletaManual(idContrato, dni, nombre);

            ResponseValueDTO<String> response = new ResponseValueDTO<>(
                    1,
                    "Boleta generada correctamente",
                    url,
                    null
            );

            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            ResponseValueDTO<String> error = new ResponseValueDTO<>(
                    -1,
                    "Error al generar la boleta: " + ex.getMessage(),
                    null,
                    null
            );

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
}
