package com.app.userservice.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.userservice.dto.UsuarioDto;
import com.app.userservice.entity.Usuario;
import com.app.userservice.service.IUserService;
import com.app.userservice.tool.Constant;
import com.app.userservice.tool.MapperUtil;

@RequestMapping("/users")
@RestController
public class UserController {

	private Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private IUserService userService;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	private Map<String, Object> response;

	@GetMapping
	public ResponseEntity<?> findAll() {

		try {
			List<UsuarioDto> users = MapperUtil.mapAll(userService.findAll(), UsuarioDto.class);

			return new ResponseEntity<>(users, HttpStatus.OK);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			response = new HashMap<>();
			response.put(Constant.MENSAJE, e.getMessage());
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> findById(@PathVariable UUID id) {

		try {
			UsuarioDto user = MapperUtil.map(userService.findById(id), UsuarioDto.class);

			if (user != null) {
				return new ResponseEntity<>(user, HttpStatus.OK);
			} else {
				response = new HashMap<>();
				response.put(Constant.MENSAJE, "Usuario no encontrado");
				return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			response = new HashMap<>();
			response.put(Constant.MENSAJE, e.getMessage());
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping
	public ResponseEntity<?> create(@Valid @RequestBody UsuarioDto user, BindingResult result) {

		if (result.hasErrors()) {

			String errors = result.getFieldErrors().stream()
					.map(err -> "El " + err.getField() + " " + err.getDefaultMessage())
					.collect(Collectors.joining(", "));

			response = new HashMap<>();
			response.put(Constant.MENSAJE, errors);
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}

		try {

			UsuarioDto newUser = MapperUtil.map(userService.findByEmail(user.getEmail()), UsuarioDto.class);

			if (newUser != null) {
				response = new HashMap<>();
				response.put("mensage", "El usuario ya se encuentra registrado");
				return new ResponseEntity<>(response, HttpStatus.CONFLICT);
			} else {
				user.setPassword(passwordEncoder.encode(user.getPassword()));
				newUser = MapperUtil.map(userService.create(MapperUtil.map(user, Usuario.class)), UsuarioDto.class);
				return new ResponseEntity<>(newUser, HttpStatus.CREATED);
			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			response = new HashMap<>();
			response.put(Constant.MENSAJE, e.getMessage());
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> update(@Valid @RequestBody UsuarioDto user, @PathVariable UUID id, BindingResult result) {

		if (result.hasErrors()) {

			String errors = result.getFieldErrors().stream()
					.map(err -> "El " + err.getField() + " " + err.getDefaultMessage())
					.collect(Collectors.joining(", "));

			response = new HashMap<>();
			response.put(Constant.MENSAJE, errors);
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}

		try {

			UsuarioDto updateUser = MapperUtil.map(userService.findById(id), UsuarioDto.class);

			if (updateUser == null) {
				response = new HashMap<>();
				response.put(Constant.MENSAJE, "Usuario no encontrado");
				
				return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
			} else {
				user.setId(id);
				user.setActive(updateUser.getActive());
				user.setCreated(updateUser.getCreated());
				user.setLastLogin(updateUser.getLastLogin());
				if (user.getPassword() != null) {
					user.setPassword(passwordEncoder.encode(user.getPassword()));
				}
				updateUser = MapperUtil.map(user, UsuarioDto.class);
				updateUser = MapperUtil.map(userService.update(MapperUtil.map(updateUser, Usuario.class)),
						UsuarioDto.class);

				return new ResponseEntity<>(updateUser, HttpStatus.OK);
			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			response = new HashMap<>();
			response.put(Constant.MENSAJE, e.getMessage());
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteById(@PathVariable UUID id) {

		try {
			userService.delete(id);

			response = new HashMap<>();
			response.put(Constant.MENSAJE, "Usuario eliminado");
			return new ResponseEntity<>(response, HttpStatus.ACCEPTED);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			response = new HashMap<>();
			response.put(Constant.MENSAJE, e.getMessage());
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
