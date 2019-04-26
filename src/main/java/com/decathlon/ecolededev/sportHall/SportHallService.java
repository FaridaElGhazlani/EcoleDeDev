package com.decathlon.ecolededev.sportHall;

import java.util.List;

import org.springframework.stereotype.Service;

import com.decathlon.ecolededev.client.ClientModel;

@Service
public class SportHallService {
	
	SportHallRepository sportHallRepository;

	public SportHallService(SportHallRepository sportHallRepository) {
		this.sportHallRepository = sportHallRepository;
	}

	public SportHallModel createSportHall(SportHallModel sportHall) {
		SportHallModel SportHall = sportHallRepository.saveAndFlush(sportHall);
	
		return SportHall;
	}
	
	public List<SportHallModel> getSportHall() {
		List<SportHallModel> clients = sportHallRepository.findAll();
		return clients;
	}
	
	public SportHallModel getOneSportHall(Long  id) {
		
		return sportHallRepository.getOne(id);
	}
	
	public void deleteOneSportHall(Long  id) {
		sportHallRepository.deleteById(id);
	}
}
