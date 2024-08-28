package com.froi.hotel.hotel.infrastructure.outputadapters;

import com.froi.hotel.hotel.domain.Hotel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "hotel", schema = "public")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HotelDbEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "phone_1", nullable = false, length = 15)
    private String phone1;

    @Column(name = "phone_2", length = 15)
    private String phone2;

    @Column(name = "city", nullable = false)
    private Integer city;

    public Hotel toDomain() {
        return Hotel.builder()
                .id(id)
                .name(name)
                .phone1(phone1)
                .phone2(phone2)
                .city(city)
                .build();
    }
}
