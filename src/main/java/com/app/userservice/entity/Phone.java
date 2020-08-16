package com.app.userservice.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "phones")
@Setter
@Getter
public class Phone implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column
	private String number;
	
	@Column
	private String citycode;
	
	@Column
	private String contrycode;

	@ManyToOne
    @JoinColumn(name="user_id")
	private Usuario user;

	@Column
	@Temporal(TemporalType.DATE)
	private Date created;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
}
