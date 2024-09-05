package com.froi.hotel.booking.infrastructure.outputadapters.restapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.froi.hotel.booking.application.checkinusecase.MakeBillRequest;
import com.froi.hotel.booking.infrastructure.outputports.PayBillOutputPort;
import com.froi.hotel.common.exceptions.NetworkMicroserviceException;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class BillControllerOutputAdapter implements PayBillOutputPort {
    @Override
    public byte[] payCheckin(MakeBillRequest makeBillRequest) throws NetworkMicroserviceException {
        String url = "http://localhost:8084/payments/v1/bills/hotel";

        RestTemplate restTemplate = new RestTemplate();
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            String jsonBody = objectMapper.writeValueAsString(makeBillRequest);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<String> entity = new HttpEntity<>(jsonBody, headers);

            ResponseEntity<byte[]> response =  restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    entity,
                    byte[].class
            );

            return response.getBody();
        } catch (Exception e) {
            throw new NetworkMicroserviceException(e.getMessage());
        }
    }
}
