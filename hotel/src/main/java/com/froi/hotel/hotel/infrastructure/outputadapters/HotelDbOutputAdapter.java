package com.froi.hotel.hotel.infrastructure.outputadapters;

import com.froi.hotel.common.PersistenceAdapter;
import com.froi.hotel.hotel.domain.Hotel;
import com.froi.hotel.hotel.infrastructure.outputports.db.CreateHotelOutputPort;
import com.froi.hotel.hotel.infrastructure.outputports.db.FindHotelByIdOutputPort;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

@PersistenceAdapter
public class HotelDbOutputAdapter implements FindHotelByIdOutputPort, CreateHotelOutputPort {
    private HotelDbEntityRepository hotelDbEntityRepository;

    @Autowired
    public HotelDbOutputAdapter(HotelDbEntityRepository hotelDbEntityRepository) {
        this.hotelDbEntityRepository = hotelDbEntityRepository;
    }

    @Override
    public Optional<Hotel> findHotelById(int id) {
        return hotelDbEntityRepository.findById(id)
                .map(HotelDbEntity::toDomain);
    }

    @Override
    public void createHotel(Hotel hotel) {
        hotelDbEntityRepository.save(HotelDbEntity.fromDomain(hotel));
    }
}
