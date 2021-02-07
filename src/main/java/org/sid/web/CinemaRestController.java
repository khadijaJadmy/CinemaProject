package org.sid.web;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.sid.dao.FilmRepository;
import org.sid.dao.TicketRepository;
import org.sid.entities.Film;
import org.sid.entities.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.Data;

@RestController
@CrossOrigin("*")
public class CinemaRestController {
@Autowired
FilmRepository filmRepository;
@Autowired
TicketRepository ticketRepository;
@GetMapping(path="/imageFilm/{id}",produces=MediaType.IMAGE_JPEG_VALUE)
public byte[] images(@PathVariable (name="id")Long id) throws IOException{
	Film f=filmRepository.findById(id).get();
	String photoName=f.getPhoto();
	File file=new File("H:/cinema/images/"+photoName);
	Path path=Paths.get(file.toURI());
	return Files.readAllBytes(path);
}
@PostMapping("/payerTickets")
@Transactional
public List<Ticket> payerTickets( @RequestBody TicketForm ticketForm) {
	List<Ticket> listeTickets=new ArrayList<>();
	ticketForm.getTickets().forEach(idTicket->{
		Ticket ticket=ticketRepository.findById(idTicket).get();
		 ticket.setNameClient(ticketForm.getNomClient());
		 ticket.setReserve(true);
		 ticket.setCodePayement(ticketForm.getCodePayement());
		 ticketRepository.save(ticket);
		 listeTickets.add(ticket);
		 
	});
	return listeTickets;
	
}
}
@Data
class TicketForm{
	private String nomClient;
	private Integer codePayement;
	private List<Long> tickets=new ArrayList<>();
}
