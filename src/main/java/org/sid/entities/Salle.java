

package org.sid.entities;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.sun.istack.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class Salle {
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	@NotNull
	@Size(min = 3, max= 15)
	private String name;
	private int nombrePlaces;
	@ManyToOne
	@JsonProperty(access=Access.WRITE_ONLY)
	private Cinema cinema;
	@OneToMany(mappedBy="salle")
	@JsonProperty(access=Access.WRITE_ONLY)
	private Collection<Place> places;
	@OneToMany(mappedBy="salle")
	@JsonProperty(access=Access.WRITE_ONLY)
	private Collection<ProjectionFilm> projectionFilms;
}
