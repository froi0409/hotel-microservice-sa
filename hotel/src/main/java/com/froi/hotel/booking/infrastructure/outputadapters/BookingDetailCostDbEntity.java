package com.froi.hotel.booking.infrastructure.outputadapters;

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

}
