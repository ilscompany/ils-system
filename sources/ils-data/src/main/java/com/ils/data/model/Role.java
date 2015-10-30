package com.ils.data.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

/**
 * 
 * @author mara
 *
 */
@Entity
public class Role extends AbstractEntity{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(length=40, nullable=false, unique=true)
	private Name name;
	
	@Column(length=250, nullable=true)
	private String description;
	
	@ManyToMany(mappedBy="authorities")
	private List<IlsUser> users = new ArrayList<IlsUser>();
	
	public enum Name {
		ROLE_ADMIN,
		ROLE_AGENT,
		ROLE_USER,
		ROLE_ANONYMOUS
	}

	public Role() {
	}
	
	public Role(Name name) {
		this.name = name;
		this.description = name.name();
	}
	
	public Role(Name name, String description) {
		this.name = name;
		this.description = description;
	}
	
	public Role(String name) {
		try {
			setName(Name.valueOf(Name.class, name));
			description = name;
		} catch (Exception e) {
			throw new IllegalArgumentException("Cannot create role with unrecognized role name : " + name, e);
		}
	}
	
	public static Role getAdminRole(){
		return new Role(Name.ROLE_ADMIN);
	}

	public static Role getAgentRole(){
		return new Role(Name.ROLE_AGENT);
	}
	
	public static Role getUserRole(){
		return new Role(Name.ROLE_USER);
	}
	
	public static Role getAnonymousRole(){
		return new Role(Name.ROLE_ANONYMOUS);
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Name getName() {
		return name;
	}

	public void setName(Name name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<IlsUser> getUsers() {
		return users;
	}

	public void setUsers(List<IlsUser> users) {
		this.users = users;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Role)) return false;
		Role role = (Role) o;
		return Objects.equals(getName(), role.getName());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getName());
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this)
				.append("name", name)
				.append("description", description)
				.toString();
	}
}
