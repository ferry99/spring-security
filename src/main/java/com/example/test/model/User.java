package com.example.test.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(	name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
	public User(String username, String password, String roles) {
		this.username = username;
		this.password = password;
		this.roles = roles;
	}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="username" , unique = true) 
	private String username;
	
    @Column(name="password")
	private String password;

    @Column(name="roles")
	private String roles;	
	
	@CreatedDate
	@Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_date")
	protected Date createdDate;

	@LastModifiedDate
	@Temporal(TemporalType.TIMESTAMP)
    @Column(name = "last_modified_date")
	protected Date lastModifiedDate;
}