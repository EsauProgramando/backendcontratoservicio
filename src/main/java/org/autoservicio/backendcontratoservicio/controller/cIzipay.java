package org.autoservicio.backendcontratoservicio.controller;

import org.autoservicio.backendcontratoservicio.response.RequestCreatePaymentDTO;
import org.autoservicio.backendcontratoservicio.response.ResponseCreatePaymentDTO;
import org.autoservicio.backendcontratoservicio.service.SParamae;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.apache.commons.codec.binary.Hex;
import org.springframework.web.client.RestTemplate;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.*;
@RestController
@RequestMapping("/api/izipay")
public class cIzipay {

    @Autowired
    private SParamae service_paramae;

    @PostMapping("/createPayment")
    public ResponseCreatePaymentDTO obtenerUsuario(@RequestBody RequestCreatePaymentDTO request) {

        String username = this.service_paramae.buscar_x_ID("IZP", "USER")
                .map(result -> result.getValorstring())
                .block();

        String password = this.service_paramae.buscar_x_ID("IZP", "PASS")
                .map(result -> result.getValorstring())
                .block();

        String url = this.service_paramae.buscar_x_ID("IZP", "URL")
                .map(result -> result.getValorstring())
                .block();

        ResponseCreatePaymentDTO response = new ResponseCreatePaymentDTO();

        RestTemplate restTemplate = new RestTemplate();


        String auth = username + ":" + password;
        String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes(StandardCharsets.UTF_8));
        String authHeader = "Basic " + encodedAuth;

        // Crear el body como un Map anidado
        Map<String, Object> body = new HashMap<>();
        body.put("amount", request.getAmount());
        body.put("currency", request.getCurrency());
        body.put("orderId", request.getOrderId());

        Map<String, String> customer = new HashMap<>();
        customer.put("email", request.getCustomer().getEmail());
        body.put("customer", customer);
        List<String> lstPaymentMethods = new ArrayList<String>();
        lstPaymentMethods.add("VISA");
        body.put("paymentMethods", lstPaymentMethods);

        // Encabezados HTTP
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", authHeader); // si la API requiere autenticación

        HttpEntity<Map<String, Object>> requestIzipay = new HttpEntity<>(body, headers);

        ResponseEntity<ResponseCreatePaymentDTO> responseIzipay = restTemplate.postForEntity(url, requestIzipay, ResponseCreatePaymentDTO.class);



        return responseIzipay.getBody();
    }

    @PostMapping("/validate")
    public ResponseEntity<String> validatePayment(@RequestBody Map<String, Object> payload) {
        try {
            String rawClientAnswer = (String) payload.get("rawClientAnswer");
            String receivedHash = (String) payload.get("hash");

            String keyHash = this.service_paramae.buscar_x_ID("IZP", "HASH")
                    .map(result -> result.getValorstring())
                    .block();
            // Calcular HMAC SHA256
            String computedHash = calculateHmacSHA256(rawClientAnswer, keyHash);

            // Comparar los hashes
            if (computedHash.equalsIgnoreCase(receivedHash)) {
                return ResponseEntity.ok("Valid payment");
            } else {
                return ResponseEntity.status(500).body("Payment hash mismatch");
            }

        } catch (Exception e) {
            return ResponseEntity.status(500).body("Internal server error");
        }
    }

    private String calculateHmacSHA256(String data, String secret) throws Exception {
        Mac hmacSha256 = Mac.getInstance("HmacSHA256");
        SecretKeySpec secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
        hmacSha256.init(secretKey);
        byte[] hashBytes = hmacSha256.doFinal(data.getBytes(StandardCharsets.UTF_8));
        return Hex.encodeHexString(hashBytes);
    }

}
