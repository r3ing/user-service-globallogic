package com.app.userservice;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.app.userservice.entity.Phone;
import com.app.userservice.entity.Role;
import com.app.userservice.entity.Usuario;
import com.app.userservice.service.IUserService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)

@SpringBootTest
@WithMockUser
@AutoConfigureMockMvc
public class UserControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Mock
	private IUserService userService;

	@Test
	public void findAll() throws Exception {

		List<Usuario> users = createUsersData();

		Mockito.when(userService.findAll()).thenReturn(users);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/users").accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		String expected = "[{'name':'Administrador','email':'admin@system.com','phones':[{'id':1,'number':'1234567','citycode':'1','contrycode':'57'}],'roles':[{'id':1,'name':'ROLE_ADMIN'}],'active':true,'created':'2020-08-16','lastLogin':'2020-08-16'},{'name':'Juan Rodriguez','email':'juan@rodriguez.org','phones':[{'id':2,'number':'1234567','citycode':'1','contrycode':'57'}],'roles':[{'id':1,'name':'ROLE_ADMIN'}],'active':true,'created':'2020-08-16','lastLogin':'2020-08-16'}]";
		
		assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
		
		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
	}

	@Test
	public void findById() throws Exception {
		
		Usuario user = createUserData();

		Mockito.when(userService.findById(user.getId())).thenReturn(user);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/users/ff8c620e-1147-42dc-8a8f-976129bfc867")
				.accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		String expected = "{'id':'ff8c620e-1147-42dc-8a8f-976129bfc867','name':'Administrador','password':'$2a$10$BbsMolrXnH71CSie01nM.eHNBOLdE7jqGr3/QVXJbg5byYgGG2gaK','email':'admin@system.com','phones':[{'id':1,'number':'1234567','citycode':'1','contrycode':'57'}],'roles':[{'id':1,'name':'ROLE_ADMIN'}],'active':true,'created':'2020-08-16','lastLogin':'2020-08-16'}";

		assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
		
		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
	}

	@Test
	public void create() throws Exception {
		
		ObjectMapper objectMapper = new ObjectMapper();
		
		Usuario user = createUserData();
		
		Mockito.when(userService.create(user)).thenReturn(user);
		
		String body =  objectMapper.writeValueAsString(user); 
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/users")
				.accept(MediaType.APPLICATION_JSON).content(body).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.CREATED.value(), response.getStatus());
	}

	public List<Usuario> createUsersData() {

		List<Usuario> users = new ArrayList<>();

		Usuario user1 = new Usuario();
		user1.setName("Administrador");
		user1.setPassword("Administrador20");
		user1.setEmail("admin@system.com");
		user1.setActive(true);
		user1.setCreated(new Date());
		user1.setLastLogin(new Date());

		Usuario user2 = new Usuario();
		user2.setName("Juan Rodriguez");
		user2.setPassword("Hunter22");
		user2.setEmail("juan@rodriguez.org");
		user2.setActive(true);
		user2.setCreated(new Date());
		user2.setLastLogin(new Date());

		Phone phone = new Phone();
		phone.setId(1);
		phone.setNumber("1234567");
		phone.setCitycode("1");
		phone.setContrycode("57");

		List<Phone> phones = new ArrayList<>();
		phones.add(phone);

		user1.setPhones(phones);

		Role role1 = new Role();
		role1.setId(1);
		role1.setName("ROLE_ADMIN");

		Role role2 = new Role();
		role2.setId(2);
		role2.setName("ROLE_USER");

		List<Role> roles1 = new ArrayList<>();
		roles1.add(role1);
		user1.setRoles(roles1);

		List<Role> roles2 = new ArrayList<>();
		roles2.add(role2);

		users.add(user1);
		users.add(user2);

		return users;
	}

	public Usuario createUserData() {

		Usuario user = new Usuario();
		user.setName("Juan Rodriguez");
		user.setPassword("Hunter22");
		user.setEmail("juan@rodriguez.org");
		user.setActive(true);
		user.setCreated(new Date());
		user.setLastLogin(new Date());

		Phone phone = new Phone();
		phone.setNumber("1234567");
		phone.setCitycode("1");
		phone.setContrycode("57");

		List<Phone> phones = new ArrayList<>();
		phones.add(phone);

		user.setPhones(phones);

		Role role = new Role();
		role.setId(1);
		role.setName("ROLE_USER");

		List<Role> roles = new ArrayList<>();
		roles.add(role);
		user.setRoles(roles);

		return user;
	}

}
