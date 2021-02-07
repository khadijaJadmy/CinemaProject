package org.sid.web;

import java.util.List;

import org.sid.dao.CinemaRepository;
import org.sid.dao.VilleRepository;
import org.sid.entities.Categorie;
import org.sid.entities.Cinema;
import org.sid.entities.Ville;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CinemaController {
	@Autowired
	CinemaRepository cinemaRepository;
	@Autowired
    VilleRepository villeRepository;
	@GetMapping("/listeCinema")
	public String listCinema(Model model,Ville ville, @RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "5") int size,
			@RequestParam(name = "keyword", defaultValue = "") String mc) {
		    Page<Cinema> pageCinema = cinemaRepository.findByNameContains(mc, PageRequest.of(page, size));

//List <Cinema> pageCinema=cinemaRepository.findAll();
//model.addAttribute("listeCinema",pageCinema);
		    
		
		model.addAttribute("listeCinema", pageCinema.getContent());
		model.addAttribute("pages", new int[pageCinema.getTotalPages()]);
		model.addAttribute("currentPage", page);
		model.addAttribute("keyword", mc);
		model.addAttribute("ville",ville);

		return "cinemaview";
	}
	/*
	@GetMapping("/listeVille")
	public List<Ville> allVille() {
		return villeRepository.findAll();
	}*/

	//fonction qui permet la suppression d'un cinéma by id
	@GetMapping("/supprimerCinema")
	public String supprimerCinema(Long id) {
		cinemaRepository.deleteById(id);
		return "redirect:/listeCinema";
	}
	@GetMapping("/modifierCinema")
	public String modifierCinema(Model model, Long id) {
		Cinema cinema =cinemaRepository.findById(id).get();
		model.addAttribute("cinema",cinema);
		return "formCinema";
	}
	@PostMapping("/saveCinema")
	public String saveCinema(Cinema cinema) {
		cinemaRepository.save(cinema);
		
		return "confirmation";
		
	}
	@GetMapping("/formCinema")
	public String formCinema(Model model,Ville ville) {
		model.addAttribute("cinema", new Cinema());
		model.addAttribute("ville",villeRepository.findAll());
		return "formCinema";
		
	}

}
