package com.decathlon.ecolededev.slot;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
public class Slot {


    /**
     * The beggining of the booking
     */
    private LocalDateTime start;
    /**
     * The end of the booking
     */
    private LocalDateTime end;

    public Slot(LocalDateTime start, LocalDateTime end) {
		this.start = start;
		this.end = end;
	}

}