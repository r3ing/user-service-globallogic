package com.app.userservice.dto;

import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RoleDto {

	private Integer id;
	
	@NotEmpty(message = "es obligatorio")
	private String name;

}

