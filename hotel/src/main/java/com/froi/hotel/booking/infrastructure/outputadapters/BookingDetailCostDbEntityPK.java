package com.froi.hotel.booking.infrastructure.outputadapters;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookingDetailCostDbEntityPK implements Serializable {
    @Column(name = "booking_extra_cost")
    private Integer bookingExtraCost;

    @Column(name = "booking")
    private Integer booking;
}
