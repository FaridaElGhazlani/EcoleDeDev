package com.decathlon.ecolededev.booking;

import java.time.LocalDateTime;

public class BookingDTO {

	Long id;
	Long idClient;
	Long idSportHall;
	LocalDateTime startDate;
	LocalDateTime endDate;
	private Status status;
	private int price;
	

	public BookingDTO(Long idClient, Long idSportHall, LocalDateTime startDate, LocalDateTime endDate,Status status, int price) {
		this.idClient = idClient;
		this.idSportHall = idSportHall;
		this.startDate = startDate;
		this.endDate = endDate;
		this.status = status;
		this.price = price;
	}
	
	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public BookingDTO() {
		
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getIdClient() {
		return idClient;
	}
	public void setIdClient(Long idClient) {
		this.idClient = idClient;
	}
	public Long getIdSportHall() {
		return idSportHall;
	}
	public void setIdSportHall(Long idSportHall) {
		this.idSportHall = idSportHall;
	}
	public LocalDateTime getStartDate() {
		return startDate;
	}
	public void setStartDate(LocalDateTime startDate) {
		this.startDate = startDate;
	}
	public LocalDateTime getEndDate() {
		return endDate;
	}
	public void setEndDate(LocalDateTime endDate) {
		this.endDate = endDate;
	}
	
	public Status getStatus() {
		return status;
	}
	
	public void setStatus(Status status) {
		this.status = status;
	}
	
}
