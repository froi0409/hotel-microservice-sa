package com.froi.hotel.booking.infrastructure.outputadapters;

import com.froi.hotel.booking.domain.Booking;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "booking", schema = "public")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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

    public Booking toDomain() {
        return Booking.builder()
                .id(UUID.fromString(id))
                .checkinExpectedDate(checkinExpectedDate)
                .checkoutExpectedDate(checkoutExpectedDate)
                .checkinDate(checkinDate)
                .checkoutDate(checkoutDate)
                .bookingPrice(bookingPrice)
                .note(note)
                .bookingName(bookingName)
                .bookingUser(bookingUser)
                .bookingDate(bookingDate)
                .build();
    }

    public static BookingDbEntity fromDomain(Booking booking) {
        return new BookingDbEntity(
                UUID.randomUUID().toString(),
                booking.getRoom().getCode(),
                booking.getHotel().getId(),
                booking.getCheckinExpectedDate(),
                booking.getCheckoutExpectedDate(),
                booking.getCheckinDate(),
                booking.getCheckoutDate(),
                booking.getBookingPrice(),
                booking.getNote(),
                booking.getBookingName(),
                booking.getBookingUser(),
                booking.getBookingDate());
    }

}
