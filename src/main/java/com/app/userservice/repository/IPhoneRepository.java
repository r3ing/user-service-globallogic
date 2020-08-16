package com.app.userservice.repository;

import org.springframework.data.repository.CrudRepository;

import com.app.userservice.entity.Phone;

public interface IPhoneRepository extends CrudRepository<Phone, Integer> {

}
