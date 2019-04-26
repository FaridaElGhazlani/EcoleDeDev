package com.decathlon.ecolededev.client;


import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/clients/")
public class ClientController {

//	@Autowired ou constructeur (Si test pas de @Autowired)
	private ClientService clientService;
	
	public ClientController(ClientService clientService) {
		this.clientService = clientService;
	}
	
	@PostMapping
	public ClientModel createClient(@RequestBody ClientModel newClient) {  
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
			String username = ((UserDetails)principal).getUsername();
			newClient.setUsername(username);
			} 
		return clientService.createClient(newClient); 
	}

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping
	public List<ClientModel> getClients(){
		return clientService.getClients();
	}
	
	@GetMapping("{id}")
	public ClientModel getOneClient(@PathVariable Long id) throws Exception {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
			String username = ((UserDetails)principal).getUsername();
			return clientService.getOneClient(id, username);
			} 
		return null;
	}
	
	@DeleteMapping("{id}")
	public void deleteOneClient(@PathVariable Long id) {
		clientService.deleteOneClient(id);
	}
}
