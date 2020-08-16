package com.app.userservice.service;

import java.util.List;
import java.util.UUID;

import com.app.userservice.entity.Usuario;

public interface IUserService {
	
	public Usuario findByEmail(String email);

	public List<Usuario> findAll();
		
	public Usuario findById(UUID id);
	
	public Usuario create(Usuario user);
	
	public Usuario update(Usuario user);
	
	public void delete(UUID id);
}
