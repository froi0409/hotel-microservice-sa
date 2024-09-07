package com.froi.hotel.room.infrastructure.outputadapters.restapi;

import com.froi.hotel.booking.application.checkinusecase.BillDiscount;
import com.froi.hotel.room.infrastructure.outputports.restapi.FindDiscountsOutputAdapter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;

@Controller
public class FindDiscountsRestAdapter implements FindDiscountsOutputAdapter {

    @Value("${discounts.url}")
    String discountsUrl;

    @Override
    public BillDiscount findRoomDiscount(String roomCode, String hotel, LocalDate date) {
        String url = discountsUrl + "/v1/discounts/findRoomDiscount/" + roomCode + "/" + hotel + "/" + date;
        return getDiscount(url);
    }

    @Override
    public BillDiscount findCustomerDiscount(String customerNit, LocalDate date) {
        String url = discountsUrl + "/v1/discounts/findCustomerDiscount/" + customerNit + "/" + date;
        return getDiscount(url);
    }

    private BillDiscount getDiscount(String url) {
        RestTemplate restTemplate = new RestTemplate();
        try {
            HttpHeaders headers = new HttpHeaders();

            HttpEntity<?> entity = new HttpEntity<>(headers);

            ResponseEntity<FindDiscountResponse> response = restTemplate.exchange(
                    url,
                    org.springframework.http.HttpMethod.GET,
                    entity,
                    FindDiscountResponse.class
            );
            System.out.println("Discount: " + response.getBody());
            return response.getBody().toBillDiscount();
        } catch(HttpClientErrorException e) {
            if (e.getStatusCode() != HttpStatus.NOT_FOUND) {
                System.err.println("Find Discount Error: " + e.getMessage());
                e.printStackTrace();
            }
        } catch (Exception e) {
            System.err.println("Find Discount Error: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

}
