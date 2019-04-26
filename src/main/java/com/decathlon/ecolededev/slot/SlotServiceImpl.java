package com.decathlon.ecolededev.slot;

import java.util.List;

public class SlotServiceImpl implements SlotService {

	@Override
	public boolean isAvailable(List<Slot> slotList, Slot slotAValider) {

		for (Slot slotValide : slotList) {
			
			// Si date de Début a Valider est < ou = à la dateDébut du slot valide
			// et la date de fin à valider est après la date de debut du slot valide
			
			if (slotAValider.getStart().isBefore(slotValide.getStart()) &&
				slotAValider.getEnd().isAfter(slotValide.getStart())	) {
				return false;
			}
				
			// Si date début à tester est >= Date début du slot valide
			// et la date de début à tester <= date de fin du slot valide
			if( slotAValider.getStart().isAfter(slotValide.getStart()) &&
				slotAValider.getStart().isBefore(slotValide.getEnd())) {
				return false;
			}
			
			if( slotAValider.getStart().isEqual(slotValide.getStart()) &&
					slotAValider.getEnd().isEqual(slotValide.getEnd())) {
					return false;
				}
		}
		
		return true;
	}

	@Override
	public boolean isCorrectSlot(Slot slot) {

		return slot.getStart().isBefore(slot.getEnd());
	}
}
