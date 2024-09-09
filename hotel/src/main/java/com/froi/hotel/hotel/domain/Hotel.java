package com.froi.hotel.hotel.domain;

import com.froi.hotel.common.DomainEntity;
import com.froi.hotel.hotel.domain.exceptions.InvalidHotelFormatException;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

@Getter
@Setter
@Builder
@DomainEntity
public class Hotel {
    private Integer id;
    private String name;
    private String phone1;
    private String phone2;
    private Integer city;

    public void validate() throws InvalidHotelFormatException {
        if (!StringUtils.isNumeric(phone1) || phone1.contains("-")) {
            throw new InvalidHotelFormatException(String.format("Phone 1 should be a valid phone number. Founded value: %s", phone1));
        }
        if (phone2 != null && (!StringUtils.isNumeric(phone2) || phone1.contains("-"))) {
            throw new InvalidHotelFormatException(String.format("Phone 2 should be a valid phone number. Founded value: %s", phone2));
        }
        if (city == null) {
            throw  new InvalidHotelFormatException("City should be a valid city id");
        }
    }

}
