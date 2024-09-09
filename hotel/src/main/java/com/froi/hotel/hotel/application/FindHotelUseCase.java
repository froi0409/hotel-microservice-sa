package com.froi.hotel.hotel.application;

import com.froi.hotel.common.UseCase;
import com.froi.hotel.hotel.domain.exceptions.InvalidHotelFormatException;
import com.froi.hotel.hotel.infrastructure.inputports.db.FindHotelInputPort;
import com.froi.hotel.hotel.infrastructure.outputadapters.HotelDbOutputAdapter;
import jakarta.persistence.EntityNotFoundException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

@UseCase
public class FindHotelUseCase implements FindHotelInputPort {

    private HotelDbOutputAdapter hotelDbOutputAdapter;

    @Autowired
    public FindHotelUseCase(HotelDbOutputAdapter hotelDbOutputAdapter) {
        this.hotelDbOutputAdapter = hotelDbOutputAdapter;
    }

    @Override
    public void existsHotel(String existsHotel) throws InvalidHotelFormatException {
        if (!StringUtils.isNumeric(existsHotel)) {
            throw new InvalidHotelFormatException("Hotel id must be a number");
        }

        hotelDbOutputAdapter.findHotelById(Integer.parseInt(existsHotel))
                .orElseThrow(() -> new EntityNotFoundException(String.format("Hotel with id %s not found", existsHotel)));
    }
}
