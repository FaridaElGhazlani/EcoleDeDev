package com.decathlon.ecolededev;


import org.junit.Test;

import com.decathlon.ecolededev.slot.Slot;
import com.decathlon.ecolededev.slot.SlotService;
import com.decathlon.ecolededev.slot.SlotServiceImpl;

import org.junit.Test;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class SlotServiceImplTest {

   private SlotService slotService = new SlotServiceImpl();
   private LocalDateTime lundi6h = LocalDateTime.of(2019, Month.APRIL, 22, 7, 0, 0);
   private LocalDateTime lundi7h = LocalDateTime.of(2019, Month.APRIL, 22, 7, 0, 0);
   private LocalDateTime lundi8h = LocalDateTime.of(2019, Month.APRIL, 22, 8, 0, 0);
   private LocalDateTime lundi9h = LocalDateTime.of(2019, Month.APRIL, 22, 9, 0, 0);
   private LocalDateTime lundi10h = LocalDateTime.of(2019, Month.APRIL, 22, 10, 0, 0);
   private LocalDateTime lundi11h = LocalDateTime.of(2019, Month.APRIL, 22, 11, 0, 0);
   private LocalDateTime lundi12h = LocalDateTime.of(2019, Month.APRIL, 22, 12, 0, 0);


   @Test
   public void correct_slot_start_before_end() {
       assertTrue(slotService.isCorrectSlot(new Slot(lundi8h, lundi9h)));
   }

   @Test
   public void incorrect_slot_start_before_end() {
       assertFalse(slotService.isCorrectSlot(new Slot(lundi10h, lundi9h)));
   }

   @Test
   public void slot_isNotAvailable_when_inside_a_slot() {
       assertFalse(slotService.isAvailable(Collections.singletonList(new Slot(lundi8h, lundi12h)), new Slot(lundi10h, lundi11h)));
   }

   @Test
   public void slot_isNotAvailable_when_start_inside_a_slot() {
       assertFalse(slotService.isAvailable(Collections.singletonList(new Slot(lundi8h, lundi10h)), new Slot(lundi9h, lundi11h)));
   }

   @Test
   public void slot_isNotAvailable_when_end_inside_a_slot() {
       assertFalse(slotService.isAvailable(Collections.singletonList(new Slot(lundi8h, lundi10h)), new Slot(lundi7h, lundi9h)));
   }

   @Test
   public void slot_isNotAvailable_when_slot_start_before_and_end_after() {
       assertFalse(slotService.isAvailable(Collections.singletonList(new Slot(lundi8h, lundi10h)), new Slot(lundi7h, lundi11h)));
   }

   @Test
   public void slot_isNotAvailable_when_already_exist() {
       assertFalse(slotService.isAvailable(Collections.singletonList(new Slot(lundi8h, lundi10h)), new Slot(lundi8h, lundi10h)));
   }

   @Test
   public void slot_isAvailable_when_before_a_slot() {
       assertTrue(slotService.isAvailable(Collections.singletonList(new Slot(lundi8h, lundi10h)), new Slot(lundi6h, lundi7h)));
   }

   @Test
   public void slot_isAvailable_when_after_a_slot() {
       assertTrue(slotService.isAvailable(Collections.singletonList(new Slot(lundi8h, lundi10h)), new Slot(lundi11h, lundi12h)));
   }

   @Test
   public void slot_isAvailable_when_start_when_a_slot_end() {
       assertTrue(slotService.isAvailable(Collections.singletonList(new Slot(lundi8h, lundi10h)), new Slot(lundi10h, lundi11h)));
   }

   @Test
   public void slot_isAvailable_when_end_when_a_slot_start() {
       assertTrue(slotService.isAvailable(Collections.singletonList(new Slot(lundi8h, lundi10h)), new Slot(lundi7h, lundi8h)));
   }

   @Test
   public void slot_isAvailable_with_slot_just_before_and_just_after() {
       assertTrue(slotService.isAvailable(Arrays.asList(new Slot(lundi8h, lundi10h), new Slot(lundi11h, lundi12h)), new Slot(lundi10h, lundi11h)));
   }

   @Test
   public void slot_isNotAvailable_with_slot_is_conflict_and_slot_without_conflict() {
       assertFalse(slotService.isAvailable(Arrays.asList(new Slot(lundi8h, lundi10h), new Slot(lundi11h, lundi12h)), new Slot(lundi10h, lundi12h)));
   }

}