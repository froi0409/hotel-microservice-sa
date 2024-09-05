package com.froi.hotel.booking.application.checkinusecase;

import com.froi.hotel.booking.application.exceptions.BookingException;
import com.froi.hotel.booking.domain.Booking;
import com.froi.hotel.booking.domain.BookingExtraCost;
import com.froi.hotel.booking.domain.exceptions.LogicBookingException;
import com.froi.hotel.booking.infrastructure.inputports.restapi.PayCheckinInputPort;
import com.froi.hotel.booking.infrastructure.outputadapters.db.BookingDbOutputAdapter;
import com.froi.hotel.booking.infrastructure.outputports.PayBillOutputPort;
import com.froi.hotel.booking.infrastructure.outputports.PayCheckinOutputPort;
import com.froi.hotel.common.UseCase;
import com.froi.hotel.common.exceptions.NetworkMicroserviceException;
import com.froi.hotel.hotel.domain.Hotel;
import com.froi.hotel.hotel.infrastructure.outputadapters.HotelDbOutputAdapter;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@UseCase
@Transactional(rollbackFor = Throwable.class)
public class CheckinUseCase implements PayCheckinInputPort {

    private PayBillOutputPort payBillOutputPort;
    private BookingDbOutputAdapter bookingDbOutputAdapter;
    private HotelDbOutputAdapter hotelDbOutputAdapter;

    @Autowired
    public CheckinUseCase(BookingDbOutputAdapter bookingDbOutputAdapter, HotelDbOutputAdapter hotelDbOutputAdapter, PayBillOutputPort payBillOutputPort) {
        this.bookingDbOutputAdapter = bookingDbOutputAdapter;
        this.hotelDbOutputAdapter = hotelDbOutputAdapter;
        this.payBillOutputPort = payBillOutputPort;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public byte[] payCheckin(PayCheckinRequest payCheckinRequest) throws BookingException, NetworkMicroserviceException, LogicBookingException {
        Hotel hotel = hotelDbOutputAdapter.findHotelById(Integer.parseInt(payCheckinRequest.getHotelId()))
                .orElseThrow(() -> new EntityNotFoundException(String.format("Hotel with id %s not found", payCheckinRequest.getHotelId())));
        Booking booking = bookingDbOutputAdapter.findHotelBooking(payCheckinRequest.getBookingId(), payCheckinRequest.getHotelId());
        validateBooking(payCheckinRequest, booking);
        List<BillDetail> billDetails = addCostsList(booking);
        List<BillDiscount> billDiscounts = addDiscounts();

        bookingDbOutputAdapter.updateCheckinDate(payCheckinRequest.getBookingId());

        MakeBillRequest makeBillRequest = new MakeBillRequest(
                payCheckinRequest.getCustomerNit(),
                payCheckinRequest.getOptionalCustomerDpi(),
                payCheckinRequest.getOptionalCustomerFirstName(),
                payCheckinRequest.getOptionalCustomerLastName(),
                payCheckinRequest.getOptionalCustomerBirthDate(),
                hotel.getId().toString(),
                booking.getId().toString(),
                billDetails,
                billDiscounts,
                payCheckinRequest.isHasDiscount()
        );


        return payBillOutputPort.payCheckin(makeBillRequest);
    }

    private void validateBooking(PayCheckinRequest payCheckinRequest, Booking booking) throws BookingException, LogicBookingException {
        booking.validateIsNotCheckin();
        booking.setCheckinDate(LocalDate.now());
        booking.validateDates();
        booking.setCostsList(bookingDbOutputAdapter.findBookingExtraCosts(payCheckinRequest.getBookingId()));
    }

    private List<BillDetail> addCostsList(Booking booking) {
        List<BillDetail> billDetails = new ArrayList<>();
        for (BookingExtraCost cost : booking.getCostsList()) {
            billDetails.add(new BillDetail(cost.getDescription(), cost.getRealPrice()));
            System.out.println(cost.getDescription());
        }

        return billDetails;
    }

    private List<BillDiscount> addDiscounts() {
        List<BillDiscount> billDiscounts = new ArrayList<>();
        return billDiscounts;
    }
}
