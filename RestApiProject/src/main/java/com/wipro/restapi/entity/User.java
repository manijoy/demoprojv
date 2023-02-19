package com.wipro.restapi.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class User{
	
	@Id
	private String uUsername;
	@NotBlank
	private String uPassword;
	@NotBlank
	private String uEmail;
	
	@ManyToOne(cascade=CascadeType.ALL)
	private Role uRole;

	public String getuUsername() {
		return uUsername;
	}

	public void setuUsername(String uUsername) {
		this.uUsername = uUsername;
	}

	public String getuPassword() {
		return uPassword;
	}

	public void setuPassword(String uPassword) {
		this.uPassword = uPassword;
	}

	public String getuEmail() {
		return uEmail;
	}

	public void setuEmail(String uEmail) {
		this.uEmail = uEmail;
	}

	public Role getuRole() {
		return uRole;
	}

	public void setuRole(Role uRole) {
		this.uRole = uRole;
	}
	
}
