package com.decathlon.ecolededev;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.persistence.EntityNotFoundException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.decathlon.ecolededev.client.ClientController;
import com.decathlon.ecolededev.client.ClientModel;
import com.decathlon.ecolededev.client.ClientService;

@RunWith(SpringRunner.class)
@WebMvcTest(ClientController.class)
public class ClientControllerTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private ClientService clientService;

	@Before
	public void setup() throws Exception {
		// client trouvé
		when(clientService.getOneClient(1L, "titi")).thenReturn(new ClientModel());
		// client non trouvé
		when(clientService.getOneClient(2L, "toto")).thenThrow(new EntityNotFoundException());
	}

	@Test
	@WithMockUser(username = "test", password = "test", roles = "ADMIN")
	public void getClient_should_return_http_200_when_client_is_found() throws Exception {
		mvc.perform(get("/clients/1")).andExpect(status().isOk());
	}

	@Test
	@WithMockUser(username = "test", password = "test", roles = "ADMIN")
	public void getClient_should_return_http_400_when_client_is_not_found() throws Exception {
		mvc.perform(get("/clients/2")).andExpect(status().isNotFound());
	}
	

	@Test
	public void getClient_should_return_http_401_when_client_is_not_authentified() throws Exception {
		mvc.perform(get("/clients/3")).andExpect(status().isUnauthorized());
	}
	
	@Test
	@WithMockUser(username = "test", password = "test", roles = "USER")
	public void getClient_should_return_http_403_when_client_has_no_right() throws Exception {
		mvc.perform(get("/clients/4")).andExpect(status().isForbidden());
	}
	
}