package com.clearbnb.services;

import com.clearbnb.entities.Booking;
import com.clearbnb.repositories.BookingRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingService {

    @Autowired
    BookingRepo bookingRepo;

    public List<Booking> findAllBookings() {
        return (List<Booking>) bookingRepo.findAll();
    }

    public Booking getOneBooking(int id) {
        return bookingRepo.findById(id);
    }

    public List<Booking> getAllBookingsByResidenceId(int residence_id) {
        return bookingRepo.findByResidenceId(residence_id);
    }

}