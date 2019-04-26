package com.decathlon.ecolededev.booking;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.lang.Nullable;

import com.decathlon.ecolededev.client.ClientModel;
import com.decathlon.ecolededev.sportHall.SportHallModel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data  // fait getter et setters
@Entity
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "booking")
public class BookingModel {

	@Id
	@GeneratedValue
	private Long id;
	
	@OneToOne
	private SportHallModel sportHallModel;
	
	@OneToOne
	private ClientModel clientModel;
	
	private LocalDateTime startDate;
	
	private LocalDateTime endDate;
	
	@Enumerated(EnumType.STRING)
	private Status status;
	
	@Nullable
	private Integer price;
	
}
