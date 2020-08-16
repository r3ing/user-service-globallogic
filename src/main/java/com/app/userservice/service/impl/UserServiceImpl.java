package com.app.userservice.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.userservice.entity.Usuario;
import com.app.userservice.exception.DataException;
import com.app.userservice.repository.IPhoneRepository;
import com.app.userservice.repository.IUserRepository;
import com.app.userservice.service.IUserService;

@Service
public class UserServiceImpl implements IUserService, UserDetailsService {

	private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	private IUserRepository userRepository;

	@Autowired
	private IPhoneRepository phoneRepository;

	@Override
	public Usuario findByEmail(String email) {
		logger.info("Buscando usuario por email");
		try {
			return userRepository.findByEmail(email);
		} catch (Exception ex) {
			throw new DataException("Error al buscar usuario por email", ex);
		}
	}

	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String email) {

		Usuario user = findByEmail(email);

		if (user == null) {
			throw new UsernameNotFoundException("Login error, no es un usuario del sistema!");
		}

		user.setLastLogin(new Date());

		userRepository.save(user);

		List<GrantedAuthority> authorities = user.getRoles().stream()
				.map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());

		return new User(user.getEmail(), user.getPassword(), user.getActive(), true, true, true, authorities);
	}

	@Override
	public List<Usuario> findAll() {
		logger.info("Listando todos los usuarios");
		try {
			return (List<Usuario>) userRepository.findAll();
		} catch (Exception ex) {
			throw new DataException("Error al listar todos los usuarios", ex);
		}
	}

	@Override
	public Usuario findById(UUID id) {
		logger.info("Buscando usuario por id");
		try {
			Optional<Usuario> user = userRepository.findById(id);

			if (user.isPresent()) {
				return user.get();
			} else {
				return null;
			}

		} catch (Exception ex) {
			throw new DataException("Error al buscar usuario por id", ex);
		}
	}

	@Override
	@Transactional
	public Usuario create(Usuario user) {
		logger.info("Creando nuevo usuario");

		try {

			user.setActive(true);
			user.setCreated(new Date());
			user.setLastLogin(new Date());

			Usuario newUser = userRepository.save(user);

			user.getPhones().forEach(phone -> phone.setUser(newUser));
			phoneRepository.saveAll(user.getPhones());

			return newUser;

		} catch (Exception ex) {
			throw new DataException("Error al crear nuevo usuario", ex);
		}
	}

	@Override
	@Transactional
	public Usuario update(Usuario user) {

		logger.info("Actualizando un usuario");

		try {

			user.setModified(new Date());

			Usuario newUser = userRepository.save(user);

			user.getPhones().forEach(phone -> phone.setUser(newUser));
			phoneRepository.saveAll(user.getPhones());

			return newUser;

		} catch (Exception ex) {
			throw new DataException("Error al actualizar un usuario", ex);
		}
	}

	@Override
	public void delete(UUID id) {
		logger.info("Eliminado un usuario por id");
		try {
			userRepository.deleteById(id);
		} catch (Exception ex) {
			throw new DataException("Error al eliminado un usuario por id", ex);
		}

	}

}
