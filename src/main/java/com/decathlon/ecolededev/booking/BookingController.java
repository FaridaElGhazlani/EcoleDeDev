package com.decathlon.ecolededev.booking;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.decathlon.ecolededev.exceptions.NotAvailableSlotException;
import com.decathlon.ecolededev.exceptions.NotCorrectSlotException;

@RestController
@RequestMapping("/booking/")
public class BookingController {

		private BookingService bookingService;

		public BookingController(BookingService bookingService) {
			this.bookingService = bookingService;
		}
		
		@PostMapping 
		@ResponseStatus(HttpStatus.CREATED) //201
		public BookingDTO create(@RequestBody BookingDTO bookingDTO) throws NotCorrectSlotException, NotAvailableSlotException {  
				return bookingService.create(bookingDTO); 
		}

		
		@PreAuthorize("hasRole('ADMIN')")
		@DeleteMapping("purge")
		public void deleteBooking() {
			bookingService.purgeBooking();
		}
		
		
		@PreAuthorize("hasRole('ADMIN')")
		@GetMapping
		public List<BookingDTO> getBooking(){
			return bookingService.getBooking();
		}
		
		@GetMapping("{id}")
		public BookingDTO getOne(@PathVariable Long id, HttpServletResponse response) {
			
			BookingDTO bookingdto = null;
				bookingdto = bookingService.getOne(id);
			return bookingdto;
		}
		
		
		@GetMapping("status/{status}")
		public List<BookingDTO> getBookingByStatus(@PathVariable Status status) {
			List<BookingDTO> listBookingDTO = new ArrayList();
			listBookingDTO = bookingService.getBookingByStatus(status);
			return listBookingDTO;
		}
		
		@PreAuthorize("hasRole('ADMIN')")
		@PatchMapping(path="{id}/{status}")
		public BookingDTO patchOne(@PathVariable Long id,@PathVariable String status) {
			return bookingService.patchOne(id, status);
		}
		
//		@PreAuthorize("hasRole('ADMIN')")
//		@PatchMapping(path="maintenance/{id}/{startDate}/{enddate}")
//		public BookingDTO patchOneSportHall(@PathVariable Long id,@PathVariable LocalDateTime startDate, @PathVariable LocalDateTime endDate ) {
//			return bookingService.patchOneSportHall(id, startDate, endDate );
//		}		

		
}
