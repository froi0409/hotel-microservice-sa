package com.froi.hotel.booking.infrastructure.outputadapters;

import com.froi.hotel.booking.domain.BookingExtraCost;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "booking_extra_cost", schema = "public")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookingExtraCostDbEntity {
    @Id
    @Column
    private Integer id;

    @Column
    public Double price;

    @Column
    public String description;

    public BookingExtraCost toDomain() {
        return BookingExtraCost.builder()
                .id(id)
                .realPrice(price)
                .build();
    }

}
