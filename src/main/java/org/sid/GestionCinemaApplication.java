package org.sid;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.sid.dao.CategorieRepository;
import org.sid.dao.FilmRepository;
import org.sid.dao.ProjectionFilmRepository;
import org.sid.entities.Categorie;
import org.sid.entities.Film;
import org.sid.entities.ProjectionFilm;
import org.sid.entities.Salle;
import org.sid.entities.Ticket;
import org.sid.service.ICinemaInitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;

@SpringBootApplication
public class GestionCinemaApplication implements CommandLineRunner{
	
		@Autowired
		private ICinemaInitService cinemaInitService;
		@Autowired
		private RepositoryRestConfiguration restConfiguration;

		
	
		public static void main(String[] args) {
			SpringApplication.run(GestionCinemaApplication.class, args);
		}
		
		@Override
		public void run(String... args) throws Exception {
		
			restConfiguration.exposeIdsFor(Film.class,Salle.class,Ticket.class);
			cinemaInitService.initVilles();
			cinemaInitService.initCinemas();
			cinemaInitService.initSalles();
			cinemaInitService.initPlaces();
			cinemaInitService.initSeances();
			cinemaInitService.initCategories();
			cinemaInitService.initFilms();
			cinemaInitService.initProjections();
			cinemaInitService.initTickets();
			
			
			
			
	
			
		}
		
}
