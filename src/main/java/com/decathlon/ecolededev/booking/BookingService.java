package com.decathlon.ecolededev.booking;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.decathlon.ecolededev.client.ClientModel;
import com.decathlon.ecolededev.client.ClientRepository;
import com.decathlon.ecolededev.exceptions.NotAvailableSlotException;
import com.decathlon.ecolededev.exceptions.NotCorrectSlotException;
import com.decathlon.ecolededev.slot.Slot;
import com.decathlon.ecolededev.slot.SlotServiceImpl;
import com.decathlon.ecolededev.sportHall.SportHallModel;
import com.decathlon.ecolededev.sportHall.SportHallRepository;

import lombok.extern.log4j.Log4j;

@Service
public class BookingService {

	private BookingRepository bookingRepository;
	private ClientRepository clientRepository;
	private SportHallRepository sportHallRepository;

	public BookingService(BookingRepository bookingRepository, ClientRepository clientRepository,
			SportHallRepository sportHallRepository) {
		this.bookingRepository = bookingRepository;
		this.clientRepository = clientRepository;
		this.sportHallRepository = sportHallRepository;
	}

	/**
	 * Récupère une réservation en fonction de son ID
	 * 
	 * @param id
	 * @return bookingDTO
	 */
	public BookingDTO getOne(Long id) {
		BookingModel bookingModel = bookingRepository.getOne(id);
		BookingDTO bookingDTO = bookingConvertModelToDTO(bookingModel);
		return bookingDTO;
	}

	/**
	 * Récupère l'ensemble des réservations
	 * 
	 * @return listBookingDTO
	 */
	public List<BookingDTO> getBooking() {

		List<BookingModel> bookings = bookingRepository.findAll();
		List<BookingDTO> listBookingDTO = new ArrayList<>();

		for (BookingModel bookingModel : bookings) {
			BookingDTO bookingDTO = bookingConvertModelToDTO(bookingModel);
			listBookingDTO.add(bookingDTO);
		}
		return listBookingDTO;
	}

	public List<BookingDTO> getBookingByStatus(Status status) {
		List<BookingDTO> listBookingDTO = new ArrayList();

		// recuperer la liste des bookingModel qui ont le status passé en variable
		List<BookingModel> listBookingModel = bookingRepository.findByStatus(status);

		// transformer la liste de bookingModel en liste de bokkingDTO
		for (BookingModel bookingModel : listBookingModel) {
			listBookingDTO.add(bookingConvertModelToDTO(bookingModel));
		}

		return listBookingDTO;
	}

	/**
	 * Cree une réservation
	 * 
	 * @param bookingDTO sans id
	 * @return bookingDTO avec id
	 * @throws NotCorrectSlotException
	 * @throws NotAvailableSlotException
	 */
	public BookingDTO create(BookingDTO bookingDTO) throws NotCorrectSlotException, NotAvailableSlotException {

		// On convertit le DTO en model
		BookingModel bookingModelAInitier = bookingConvertDTOtoModel(bookingDTO);
		// Ramener les réservations futures de la salle de sport

		// Vérifier que le slot a initier est correct
		SlotServiceImpl slotImpl = new SlotServiceImpl();
		Slot slotAValider = new Slot(bookingModelAInitier.getStartDate(), bookingModelAInitier.getEndDate());

		if (!slotImpl.isCorrectSlot(slotAValider)) {
			bookingModelAInitier.setStatus(Status.CONFLIT);
			// message d'erreur
			throw new NotCorrectSlotException(slotAValider);
		}

		List<Slot> listeslotValides = new ArrayList();
		List<BookingModel> listeResaFuture = bookingRepository.findByStartingDate(bookingModelAInitier.getStartDate(),
				bookingModelAInitier.getSportHallModel().getId());

		// On remplit la liste de slot valide
		for (BookingModel resaFuture : listeResaFuture) {
			listeslotValides.add(new Slot(resaFuture.getStartDate(), resaFuture.getEndDate()));
		}
		// On vérifie la dispo
		if (!slotImpl.isAvailable(listeslotValides, slotAValider)) {
			bookingModelAInitier.setStatus(Status.CONFLIT);
			bookingRepository.save(bookingModelAInitier);
			throw new NotAvailableSlotException(slotAValider);
		}

		// recuperer le prix de la salle
		bookingModelAInitier.setPrice(getPrice(bookingModelAInitier.getSportHallModel().getId()));
		// on sauvegarde
		BookingModel save = bookingRepository.save(bookingModelAInitier);

		return bookingConvertModelToDTO(save);
	}

	/**
	 * Convertit un bookingModelDTO en bookingModel
	 * 
	 * @param bookingDTO
	 * @return bookingModel
	 */
	private BookingModel bookingConvertDTOtoModel(BookingDTO bookingDTO) {
		BookingModel bookingModel = new BookingModel();
		ClientModel clientModel = clientRepository.getOne(bookingDTO.getIdClient());
		bookingModel.setClientModel(clientModel);
		SportHallModel sportHallModel = sportHallRepository.getOne(bookingDTO.getIdSportHall());
		bookingModel.setSportHallModel(sportHallModel);
		bookingModel.setStartDate(bookingDTO.getStartDate());
		bookingModel.setEndDate(bookingDTO.getEndDate());
		bookingModel.setStatus(Status.EN_ATTENTE_DE_VALIDATION);
		bookingModel.setPrice(bookingDTO.getPrice());
		return bookingModel;
	}

	/**
	 * Convertit un BookingModel en BookingDTO
	 * 
	 * @param bookingModel
	 * @return bookingDTO
	 */
	private BookingDTO bookingConvertModelToDTO(BookingModel bookingModel) {

		BookingDTO bookingDTO = new BookingDTO();
		bookingDTO.setId(bookingModel.getId());
		ClientModel clientModel = bookingModel.getClientModel();
		bookingDTO.setIdClient(clientModel.getId());
		SportHallModel sportHallModel = bookingModel.getSportHallModel();
		bookingDTO.setIdSportHall(sportHallModel.getId());

		bookingDTO.setStartDate(bookingModel.getStartDate());
		bookingDTO.setEndDate(bookingModel.getEndDate());

		bookingDTO.setStatus(bookingModel.getStatus());
		if(bookingModel.getPrice()!=null) {
		bookingDTO.setPrice(bookingModel.getPrice());
		}

		return bookingDTO;
	}

	public BookingDTO patchOne(Long id, String status) {
		BookingModel bookingModel = bookingRepository.getOne(id);
		if (status.equalsIgnoreCase("VALIDE")) {
			bookingModel.setStatus(Status.VALIDE);
		} else {
			if (status.equalsIgnoreCase("CANCEL")) {
				bookingModel.setStatus(Status.CANCEL);
			} 
			
		}
		bookingRepository.save(bookingModel);
		return bookingConvertModelToDTO(bookingModel);
	}
	

//	public BookingDTO patchOneSportHall(Long sportHallId, LocalDateTime startDate,LocalDateTime endDate) {
//		// Passer toutes les réservations de la salle à CANCEL
//		List<BookingModel> listeResa = bookingRepository.findBySportHallModelId (sportHallId, startDate, endDate );
//		for (BookingModel bookingModel : listeResa) {
//			bookingModel.setStatus(Status.CANCEL);
//			bookingRepository.save(bookingModel);
//		}
//
//		// Créer une réservation de type maintenance
//		
//		
//		
//		return null;
//	}

	@Value("${api.price}")
	String urlString;
	public int getPrice(Long id) {
		   RestTemplate restTemplate = new RestTemplate();
		   URI uri = null;
		   try {
			   uri = new URI(urlString + "/price/" + id);
		   } catch (URISyntaxException e) {
		 //      log.error("erreur lors de la construction de l'uri");
		       return 50;
		   }
		   ResponseEntity<Integer> response = restTemplate.getForEntity(uri, Integer.class);
		   return response.getBody();
		}

	public void purgeBooking() {
		bookingRepository.purgeBooking();
		
	}
	
	
}
