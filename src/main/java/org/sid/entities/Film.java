package org.sid.entities;


import java.util.Collection;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Data @AllArgsConstructor    @NoArgsConstructor

public class Film {
	@Id  @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String titre;
  
	private double duree;
	
	private String realisateur;
	
	private String description;
	
	private String photo;
	 
	@Temporal(TemporalType.DATE) 
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date dateSortie;
	@OneToMany(mappedBy="film")
	@JsonProperty(access=Access.WRITE_ONLY)
	private Collection<ProjectionFilm> projections;
	@ManyToOne 
	private Categorie categorie;
	

}
