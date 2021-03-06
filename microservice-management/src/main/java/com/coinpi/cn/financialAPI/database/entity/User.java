package com.coinpi.cn.financialAPI.database.entity;


import java.util.LinkedHashSet;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.coinpi.cn.financialAPI.model.RegistModel;
import com.coinpi.cn.financialAPI.security.SecurityConfig;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class User implements UserDetails{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;

	@Email
	@NotNull(message = "This field can't be empty")
	@Column(nullable = false, unique = true)
	private String email;

	@NotNull(message = "This field can't be empty")
	@Column(nullable = false)
	private String password;

	@NotNull(message = "This field can't be empty")
	@Column(nullable = false)
	private String firstName;

	@NotNull(message = "This field can't be empty")
	@Column(nullable = false)
	private String lastName;

	@ElementCollection(targetClass=LocalDateTime.class)
	@LazyCollection(LazyCollectionOption.TRUE)
	private Set<LocalDateTime> logs = new LinkedHashSet<>();

	@ElementCollection(targetClass=AcessRole.class)
	@LazyCollection(LazyCollectionOption.FALSE)
	@Enumerated(EnumType.STRING)
	private Set<AcessRole> roles = new LinkedHashSet<>();

	
	//Constructor
	public User( String email,String password,String firstName,String lastName, Set<AcessRole> roles) {
		this.email = email;
		this.password = SecurityConfig.getEncoder().encode(password);
		this.firstName = firstName;
		this.lastName = lastName;
		this.roles = roles;
		logs.add(LocalDateTime.now());
	}
	


	public User(RegistModel registData) {
		this.email = registData.getUsername();
		this.password = SecurityConfig.getEncoder().encode(registData.getPassword());
		this.firstName = registData.getFirstName();
		this.lastName = registData.getLastName();
		this.roles = Set.of(AcessRole.ROLE_CLIENT);
		logs.add(LocalDateTime.now());
	}
	


	/**
	 * Add an entry into user logs set
	 */
	public void addLog(LocalDateTime date) {
		logs.add(date);
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return  roles.stream().map(AcessRole::toGrantedAuthority).collect(Collectors.toList());
	}
	
	@Override
	public String getUsername() {
		return email;
	}
	
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
	
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}
	
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
	
	@Override
	public boolean isEnabled() {
		return true;
	}
	
	
	/**
	 * 
	 * Receives rawPassword and encodes it
	 * 
	 * @param password
	 */
	public void setPassword(String password) {
		this.password = SecurityConfig.getEncoder().encode(password);
	}
}
