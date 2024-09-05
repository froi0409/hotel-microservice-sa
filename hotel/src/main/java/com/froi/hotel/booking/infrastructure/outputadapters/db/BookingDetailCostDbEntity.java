package com.froi.hotel.booking.infrastructure.outputadapters.db;

import com.froi.hotel.booking.domain.BookingExtraCost;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "booking_detail_cost", schema = "public")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookingDetailCostDbEntity {
    @EmbeddedId
    private BookingDetailCostDbEntityPK id;

    @Column(name = "real_price")
    private Double realPrice;

    public BookingExtraCost toDomain() {
        return BookingExtraCost.builder()
                .id(id.getBookingExtraCost())
                .realPrice(realPrice)
                .build();
    }

}
