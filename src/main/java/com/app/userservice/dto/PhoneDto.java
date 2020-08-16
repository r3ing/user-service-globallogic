package com.app.userservice.dto;

import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PhoneDto {
	
	private Integer id;

	@NotEmpty(message = "es obligatorio")
	private String number;
	
	@NotEmpty(message = "es obligatorio")
	private String citycode;
	
	@NotEmpty(message = "es obligatorio")
	private String contrycode;

}
