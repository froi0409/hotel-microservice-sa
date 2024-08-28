package com.froi.hotel.booking.infrastructure.outputadapters;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "booking", schema = "public")
@Getter
@Setter
@NoArgsConstructor
public class BookingDbEntity {

    @Id
    @Column
    private String id;

    @Column(name = "room_code")
    private String roomCode;

    @Column
    private Integer hotel;

    @Column(name = "checkin_expected_date")
    private LocalDate checkinExpectedDate;

    @Column(name = "checkout_expected_date")
    private LocalDate checkoutExpectedDate;

    @Column(name = "checkin_date")
    private LocalDate checkinDate;

    @Column(name = "checkout_date")
    private LocalDate checkoutDate;

    @Column(name = "booking_price")
    private Double bookingPrice;

    @Column
    private String note;

    @Column(name = "booking_name")
    private String bookingName;

    @Column(name = "booking_user")
    private String bookingUser;

    @Column(name = "booking_date")
    private LocalDate bookingDate;
}
