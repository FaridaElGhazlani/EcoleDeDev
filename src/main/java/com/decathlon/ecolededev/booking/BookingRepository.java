package com.decathlon.ecolededev.booking;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
public interface BookingRepository extends JpaRepository<BookingModel, Long> {
// le Long en 2 eme parametre correspond au type de l'ID dans BookingModel
	
	public List<BookingModel> findByStatus(Status status);

	// ?1 et ?2 coorespondent à startingDate et id 
	 @Query("select b from BookingModel b where b.startDate > ?1 " +
			 " and b.sportHallModel.id = ?2 " +
			 " and b.status ='VALIDE'")
			    List<BookingModel> findByStartingDate(LocalDateTime startingDate,Long id);

	    @Transactional
	    @Modifying
		@Query("delete from BookingModel b where b.endDate < now() ")
	    void purgeBooking();
	
	 // Récupère les réservations d'une salle de sport entre 2 dates
//	 @Query("select b from BookingModel b where b.sportHallModel.id = ?1 " +
	//		 " and b.startdate = ?2 and b.enddate = ?3 ")
		//	    List<BookingModel> findBySportHallModelId(Long sportHallModelId, LocalDateTime startDate, LocalDateTime EndtDate);
	 
	
} 