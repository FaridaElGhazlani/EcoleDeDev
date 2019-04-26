package com.decathlon.ecolededev.exceptions;

import com.decathlon.ecolededev.slot.Slot;

public class NotCorrectSlotException extends Exception  {

	public NotCorrectSlotException(Slot slot) {
		super("Slot for "+slot.getStart()+" to "+slot.getEnd()+" is not correct ");
	}
}
