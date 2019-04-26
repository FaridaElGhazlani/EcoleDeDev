package com.decathlon.ecolededev.sportHall;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sporthall/")
public class SportHallController {
	
	private SportHallService sportHallService;

	public SportHallController(SportHallService sportHallService) {
		this.sportHallService = sportHallService;
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping 
	public SportHallModel createSportHall(@RequestBody SportHallModel newSportHall) {  
			return sportHallService.createSportHall(newSportHall); 
	}

	@GetMapping
	public List<SportHallModel> getSportHalls(){
		return sportHallService.getSportHall();
	}

	@GetMapping("{id}")
	public SportHallModel getOneSportHall(@PathVariable Long id) {
		return sportHallService.getOneSportHall(id);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("{id}")
	public void deleteOneSportHall(@PathVariable Long id) {
		sportHallService.deleteOneSportHall(id);
	}

}
