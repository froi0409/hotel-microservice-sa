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
import com.froi.hotel.room.domain.Room;
import com.froi.hotel.room.infrastructure.outputadapters.db.RoomDbOutputAdapter;
import com.froi.hotel.room.infrastructure.outputadapters.restapi.FindDiscountsRestAdapter;
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
    private FindDiscountsRestAdapter findDiscountsRestAdapter;
    private RoomDbOutputAdapter roomDbOutputAdapter;

    @Autowired
    public CheckinUseCase(BookingDbOutputAdapter bookingDbOutputAdapter, HotelDbOutputAdapter hotelDbOutputAdapter, PayBillOutputPort payBillOutputPort, FindDiscountsRestAdapter findDiscountsRestAdapter, RoomDbOutputAdapter roomDbOutputAdapter) {
        this.bookingDbOutputAdapter = bookingDbOutputAdapter;
        this.hotelDbOutputAdapter = hotelDbOutputAdapter;
        this.payBillOutputPort = payBillOutputPort;
        this.findDiscountsRestAdapter = findDiscountsRestAdapter;
        this.roomDbOutputAdapter = roomDbOutputAdapter;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public byte[] payCheckin(PayCheckinRequest payCheckinRequest) throws BookingException, NetworkMicroserviceException, LogicBookingException {
        Hotel hotel = hotelDbOutputAdapter.findHotelById(Integer.parseInt(payCheckinRequest.getHotelId()))
                .orElseThrow(() -> new EntityNotFoundException(String.format("Hotel with id %s not found", payCheckinRequest.getHotelId())));
        Booking booking = bookingDbOutputAdapter.findHotelBooking(payCheckinRequest.getBookingId(), payCheckinRequest.getHotelId());
        Room room = roomDbOutputAdapter.findRoomById(hotel.getId(), booking.getRoom().getCode())
                .orElseThrow(() -> new EntityNotFoundException(String.format("Room with code %s not found in hotel %s", booking.getRoom().getCode(), hotel.getId())));
        booking.setRoom(room);

        validateBooking(payCheckinRequest, booking);
        List<BillDetail> billDetails = addCostsList(booking);
        List<BillDiscount> billDiscounts = addDiscounts(payCheckinRequest, booking);

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
        }
        billDetails.add(new BillDetail("Mantenaince Cost", booking.getRoom().getMaintenanceCost()));
        return billDetails;
    }

    private List<BillDiscount> addDiscounts(PayCheckinRequest payCheckinRequest, Booking booking) {
        LocalDate date = LocalDate.now();
        List<BillDiscount> billDiscounts = new ArrayList<>();

        BillDiscount billDiscount = findDiscountsRestAdapter.findRoomDiscount(booking.getRoom().getCode(), payCheckinRequest.getHotelId(), date);
        if (billDiscount != null) {
            double percentage = (booking.getBookingPrice() / 100) * billDiscount.getDiscounted();
            BillDiscount percentageDiscount = new BillDiscount("Room Discount-" + billDiscount.getDescription(), percentage);
            billDiscounts.add(percentageDiscount);
        }

        if (payCheckinRequest.isHasDiscount()) {
            BillDiscount customerDiscount = findDiscountsRestAdapter.findCustomerDiscount(payCheckinRequest.getCustomerNit(), date);
            if (customerDiscount != null) {
                double percentage = (booking.getBookingPrice() / 100) * customerDiscount.getDiscounted();
                BillDiscount percentageDiscount = new BillDiscount("Customer Discount-" + customerDiscount.getDescription(), percentage);
                billDiscounts.add(percentageDiscount);
            }
        }

        return billDiscounts;
    }
}
