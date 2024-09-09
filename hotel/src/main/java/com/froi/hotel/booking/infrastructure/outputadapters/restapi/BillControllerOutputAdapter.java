package com.froi.hotel.booking.infrastructure.outputadapters.restapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.froi.hotel.booking.application.checkinusecase.MakeBillRequest;
import com.froi.hotel.booking.infrastructure.outputports.PayBillOutputPort;
import com.froi.hotel.common.exceptions.NetworkMicroserviceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class BillControllerOutputAdapter implements PayBillOutputPort {

    @Value("${payments.url}")
    String paymentsUrl;

    private RestTemplate restTemplate;

    @Autowired
    public BillControllerOutputAdapter(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public byte[] payCheckin(MakeBillRequest makeBillRequest) throws NetworkMicroserviceException {
        String url = paymentsUrl + "/v1/bills/hotel";
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
            e.printStackTrace();
            System.out.println(e.getMessage());
            throw new NetworkMicroserviceException(e.getMessage());
        }
    }
}
