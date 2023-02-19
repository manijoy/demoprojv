package com.wipro.restapi.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Role{

	@Id
	private int roleId;
	@NotBlank
	private String rolename;
	public int getRoleId() {
		return roleId;
	}
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	public String getRolename() {
		return rolename;
	}
	public void setRolename(String rolename) {
		this.rolename = rolename;
	}
	public Role(int roleId, @NotBlank String rolename) {
		super();
		this.roleId = roleId;
		this.rolename = rolename;
	}
	
	public Role() {
		super();
	}
	
	
}
