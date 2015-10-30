package com.ils.data.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Cette table est utilisee par spring security dans le processus de gestion de
 * l'option d'authentification "se souvenir de moi"
 * 
 * @author: lonceny.mara
 * 
 */
@Entity
public class Persistent_logins {

	@Id
	@Column(length = 64, nullable = false)
	private String series;

	@Column(length = 64, nullable = false)
	private String username;

	@Column(length = 64, nullable = false)
	private String token;

	@Column(nullable = false)
	private Timestamp last_used;

	public String getSeries() {
		return series;
	}

	public void setSeries(String series) {
		this.series = series;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Timestamp getLast_used() {
		return last_used;
	}

	public void setLast_used(Timestamp last_used) {
		this.last_used = last_used;
	}

}
