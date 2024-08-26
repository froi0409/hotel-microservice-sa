package com.froi.hotel.booking.application.makebookingusecase;

import com.froi.hotel.booking.infrastructure.inputports.MakeBookingInputPort;
import com.froi.hotel.booking.infrastructure.outputadapters.BookingDbOutputAdapter;
import com.froi.hotel.common.UseCase;
import org.springframework.beans.factory.annotation.Autowired;

@UseCase
public class MakeBookingUseCase implements MakeBookingInputPort {

    private BookingDbOutputAdapter bookingDbOutputAdapter;

    @Autowired
    public MakeBookingUseCase(BookingDbOutputAdapter bookingDbOutputAdapter) {
        this.bookingDbOutputAdapter = bookingDbOutputAdapter;
    }

    @Override
    public void makeBooking(MakeBookingRequest makeBookingRequest) {
        // verificar que el hotel y la habitación existen (o ambas al mismo tiempo, decide en cuanto lo implementes)
        // Si el bookingUser no es nulo, verifica que el usuario exista
        // verifica si la fecha de checkin y checkout coinciden con algunas otras reservas en estado booked
        // Si no coinciden con otra reserva, crea la reserva
        // Si coinciden con otra reserva, lanza una excepción
    }

}
