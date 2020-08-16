package com.app.userservice.dto;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UsuarioDto {

	private UUID id;

	@NotEmpty(message = "es obligatorio")
	private String name;

	@NotEmpty(message = "es obligatorio")
	@Pattern(regexp = "^[A-Z]{1}[a-z]+\\d{2}$", message = "no es un password valido")
	@Size(min = 6, max = 12, message = "tiene que tener entre 6 y 12 caracteres")
	private String password;

	@NotEmpty(message = "es obligatorio")
	@Pattern(regexp = "^[a-zA-Z0-9][a-zA-Z0-9._]*@[a-zA-Z0-9]+([.][a-zA-Z]+)+$", message = "no es una direccion valida")
	private String email;

	@NotEmpty(message = "es obligatorio")
	private List<PhoneDto> phones;
	
	@NotEmpty(message = "es obligatorio")
	private List<RoleDto> roles;

	private Boolean active;

	private Date created;
	
	private Date modified;

	private Date lastLogin;

}
