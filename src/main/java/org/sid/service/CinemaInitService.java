package org.sid.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

import org.sid.dao.CategorieRepository;
import org.sid.dao.CinemaRepository;
import org.sid.dao.FilmRepository;
import org.sid.dao.PlaceRepository;
import org.sid.dao.ProjectionFilmRepository;
import org.sid.dao.SalleRepository;
import org.sid.dao.SeanceRepository;
import org.sid.dao.TicketRepository;
import org.sid.dao.VilleRepository;
import org.sid.entities.Categorie;
import org.sid.entities.Cinema;
import org.sid.entities.Film;
import org.sid.entities.Place;
import org.sid.entities.ProjectionFilm;
import org.sid.entities.Salle;
import org.sid.entities.Seance;
import org.sid.entities.Ticket;
import org.sid.entities.Ville;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CinemaInitService implements ICinemaInitService {
	@Autowired
private VilleRepository villeRepository;
	@Autowired
	private CinemaRepository cinemaRepository;
	@Autowired
	private SalleRepository salleRepository;
	@Autowired
	private PlaceRepository placeRepository;
	@Autowired
	private SeanceRepository seanceRepository;
	@Autowired
	private FilmRepository filmRepository;
	@Autowired
	private ProjectionFilmRepository projectionRepository;
	@Autowired
	private CategorieRepository categorieRepository;
	@Autowired
	private TicketRepository ticketRepository;
	@Override
	public void initVilles() {
		// TODO Auto-generated method stub
		Stream.of("casa","rabat","kech").forEach(nameVille->{
			Ville ville=new Ville();
			ville.setName(nameVille);
			villeRepository.save(ville);
		});
		
	}

	@Override
	public void initCinemas() {
		// TODO Auto-generated method stub
		villeRepository.findAll().forEach(v->{
			Stream.of("megaRama","founoun","chahrazad").forEach(nameCinema->{
				Cinema cinema=new Cinema();
				cinema.setName(nameCinema);
				cinema.setNombreSalles(3);
				cinema.setVille(v);
				cinemaRepository.save(cinema);
			});
		});
	}

	@Override
	public void initSalles() {
		// TODO Auto-generated method stub
		cinemaRepository.findAll().forEach(cinema->{
			for(int i=0;i<cinema.getNombreSalles();i++) {
				Salle salle=new Salle();
				salle.setName("Salle"+(i+1));
				salle.setCinema(cinema);
				salle.setNombrePlaces(15+(int)(Math.random()*20));
				salleRepository.save(salle);
				
			}
		});
		
	}

	@Override
	public void initPlaces() {
		// TODO Auto-generated method stub
		salleRepository.findAll().forEach(salle->{
			for(int i=0;i<salle.getNombrePlaces();i++) {
				Place place=new Place();
				place.setNumero(i+1);
				place.setSalle(salle);
				placeRepository.save(place);
			}
			});
		
	}

	@Override
	public void initSeances() {
		// TODO Auto-generated method stub
		DateFormat dateFormat=new SimpleDateFormat("HH:MM");
		Stream.of("12:00","13:00").forEach(s->{
			Seance seance=new Seance();
			try {
				seance.setHeureDebut(dateFormat.parse(s));
				seanceRepository.save(seance);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		});
	}

	@Override
	public void initCategories() {
		// TODO Auto-generated method stub
		Stream.of("histoire","fiction").forEach(cat->{
			Categorie categorie=new Categorie();
			categorie.setName(cat);
			categorieRepository.save(categorie);
		});
		
	}

	@Override
	public void initFilms() {
		// TODO Auto-generated method stub
		double[] durees= new double[] {1,1.5,2};
		List<Categorie> categories=categorieRepository.findAll();
		Stream.of("Crazy Wife","Iteone Class").forEach(titreFilm->{
			Film film=new Film();
			film.setTitre(titreFilm);
			film.setDuree(durees [new Random().nextInt(durees.length)]);
			film.setPhoto(titreFilm.replaceAll(" ", "")+".jpg");
			film.setCategorie(categories.get(new Random().nextInt(categories.size())));
			filmRepository.save(film);
		});
		
		
	}

	@Override
	public void initProjections() {
		// TODO Auto-generated method stub
		double [] prices=new double[] {30,60,80,90};
		List<Film> films=filmRepository.findAll();
		villeRepository.findAll().forEach(ville->{
			ville.getCinemas().forEach(cinema->{
				cinema.getSalles().forEach(salle->{
					int index=new Random().nextInt(films.size());
				//	filmRepository.findAll().forEach(film->{
					Film film=films.get(index);
						seanceRepository.findAll().forEach(seance->{
							ProjectionFilm projectionFilm=new ProjectionFilm();
							projectionFilm.setDateProjection(new Date());
							projectionFilm.setFilm(film);
							projectionFilm.setPrix(prices [new Random().nextInt(prices.length)]);
							projectionFilm.setSalle(salle);
							projectionFilm.setSeance(seance);
							projectionRepository.save(projectionFilm);
							
						});
					//});
				});
			});
		});
		
	}

	@Override
	public void initTickets() {
		// TODO Auto-generated method stub
		projectionRepository.findAll().forEach(p->{
			p.getSalle().getPlaces().forEach(place->{
				Ticket ticket=new Ticket();
				ticket.setPlace(place);
				ticket.setPrice(p.getPrix());
				ticket.setProjection(p);
				ticket.setReserve(false);
				ticketRepository.save(ticket);
				
			});
		});
	}


	

		

}
