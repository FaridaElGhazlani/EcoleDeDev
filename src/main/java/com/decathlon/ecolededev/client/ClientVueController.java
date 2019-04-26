package com.decathlon.ecolededev.client;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("html/client/")
public class ClientVueController {
	
	private ClientService clientService;
	
	public ClientVueController(ClientService clientService) {
		this.clientService = clientService;
	}
	
	@GetMapping("delete/{id}")
	public String deletetClient(@PathVariable Long id) {
		clientService.deleteOneClient(id);
		return "redirect:../list";
	}
	
	@GetMapping("list")
	public String listClient(Model model, @RequestParam(value ="errorMessage",required = false) String message) {
	    model.addAttribute("clients", clientService.getClients());
	    model.addAttribute("newClient",new ClientModel());
	    if(!StringUtils.isEmpty(message)){
	        model.addAttribute("errorMessage",message);
	    }
	    return "client";
	}
	
	@PostMapping(value = {"/ajouterClient"})
	public String savePerson(RedirectAttributes model,@ModelAttribute("client") ClientModel client) {
	    if(!StringUtils.isEmpty(client.getName())) {
	        clientService.createClient(client);
	    } else
	    {
	        model.addAttribute("errorMessage","Le nom ne peutpas être vide");
	    }
	    return "redirect:list";
	}
}




