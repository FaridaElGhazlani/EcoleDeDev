package com.decathlon.ecolededev.exceptions;

import com.decathlon.ecolededev.slot.Slot;

public class NotAvailableSlotException extends Exception  {

	public NotAvailableSlotException(Slot slot) {
		super("Slot for "+slot.getStart()+" to "+slot.getEnd()+" is not free ");
	}
}
