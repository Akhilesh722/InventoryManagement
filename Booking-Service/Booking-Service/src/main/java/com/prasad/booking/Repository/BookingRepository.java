package com.prasad.booking.Repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.prasad.booking.Entity.Booking;

@Repository
public interface BookingRepository extends CrudRepository<Booking, Integer> {

	List<Booking> findAllByCustomerId(int customerId);


}
