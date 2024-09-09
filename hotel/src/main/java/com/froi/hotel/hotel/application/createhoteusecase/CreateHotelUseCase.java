package com.froi.hotel.hotel.application.createhoteusecase;

import com.froi.hotel.common.UseCase;
import com.froi.hotel.hotel.domain.Hotel;
import com.froi.hotel.hotel.domain.exceptions.InvalidHotelFormatException;
import com.froi.hotel.hotel.infrastructure.outputadapters.HotelDbOutputAdapter;
import com.froi.hotel.hotel.infrastructure.inputports.db.CreateHotelInputPort;
import org.springframework.beans.factory.annotation.Autowired;

@UseCase
public class CreateHotelUseCase implements CreateHotelInputPort {

    private HotelDbOutputAdapter hotelDbOutputAdapter;

    @Autowired
    public CreateHotelUseCase(HotelDbOutputAdapter hotelDbOutputAdapter) {
        this.hotelDbOutputAdapter = hotelDbOutputAdapter;
    }

    @Override
    public void createHotel(CreateHotelRequest createHotelRequest) throws InvalidHotelFormatException {
        Hotel hotel = createHotelRequest.toDomain();
        hotel.validate();
        hotelDbOutputAdapter.createHotel(hotel);
    }
}
