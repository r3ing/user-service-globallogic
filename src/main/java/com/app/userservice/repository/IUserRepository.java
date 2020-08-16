package com.app.userservice.repository;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import com.app.userservice.entity.Usuario;

public interface IUserRepository extends CrudRepository<Usuario, UUID> {

	public Usuario findByEmail(String email);

}
