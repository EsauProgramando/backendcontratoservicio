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
import reactor.core.publisher.Mono;

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
    public Mono<ResponseCreatePaymentDTO> obtenerUsuario(@RequestBody RequestCreatePaymentDTO request) {
        Mono<String> usernameMono = this.service_paramae.buscar_x_ID("IZP", "USER")
                .map(result -> result.getValorstring());

        Mono<String> passwordMono = this.service_paramae.buscar_x_ID("IZP", "PASS")
                .map(result -> result.getValorstring());

        Mono<String> urlMono = this.service_paramae.buscar_x_ID("IZP", "URL")
                .map(result -> result.getValorstring());

        return Mono.zip(usernameMono, passwordMono, urlMono)
                .flatMap(tuple -> {
                    String username = tuple.getT1();
                    String password = tuple.getT2();
                    String url = tuple.getT3();

                    String auth = username + ":" + password;
                    String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes(StandardCharsets.UTF_8));
                    String authHeader = "Basic " + encodedAuth;

                    // Body
                    Map<String, Object> body = new HashMap<>();
                    body.put("amount", request.getAmount());
                    body.put("currency", request.getCurrency());
                    body.put("orderId", request.getOrderId());

                    Map<String, String> customer = new HashMap<>();
                    customer.put("email", request.getCustomer().getEmail());
                    body.put("customer", customer);
                    body.put("paymentMethods", List.of("VISA"));

                    // Headers
                    HttpHeaders headers = new HttpHeaders();
                    headers.setContentType(MediaType.APPLICATION_JSON);
                    headers.set("Authorization", authHeader);

                    HttpEntity<Map<String, Object>> requestIzipay = new HttpEntity<>(body, headers);

                    RestTemplate restTemplate = new RestTemplate();
                    ResponseEntity<ResponseCreatePaymentDTO> responseIzipay =
                            restTemplate.postForEntity(url, requestIzipay, ResponseCreatePaymentDTO.class);

                    return Mono.just(responseIzipay.getBody());
                });
    }

    @PostMapping("/validate")
    public Mono<ResponseEntity<String>> validatePayment(@RequestBody Map<String, Object> payload) {
        String rawClientAnswer = (String) payload.get("rawClientAnswer");
        String receivedHash = (String) payload.get("hash");

        return this.service_paramae.buscar_x_ID("IZP", "HASH")
                .map(result -> result.getValorstring())
                .flatMap(keyHash -> {
                    try {
                        String computedHash = calculateHmacSHA256(rawClientAnswer, keyHash);
                        if (computedHash.equalsIgnoreCase(receivedHash)) {
                            return Mono.just(ResponseEntity.ok("Valid payment"));
                        } else {
                            return Mono.just(ResponseEntity.status(500).body("Payment hash mismatch"));
                        }
                    } catch (Exception e) {
                        return Mono.just(ResponseEntity.status(500).body("Internal server error"));
                    }
                });
    }


    private String calculateHmacSHA256(String data, String secret) throws Exception {
        Mac hmacSha256 = Mac.getInstance("HmacSHA256");
        SecretKeySpec secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
        hmacSha256.init(secretKey);
        byte[] hashBytes = hmacSha256.doFinal(data.getBytes(StandardCharsets.UTF_8));
        return Hex.encodeHexString(hashBytes);
    }

}
