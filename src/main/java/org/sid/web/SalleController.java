package org.sid.web;




import org.sid.dao.SalleRepository;

import org.sid.entities.Salle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SalleController {


	@Autowired
	SalleRepository salleRepository;

	@GetMapping("/listeSalle")
	public String listSalle(Model model, @RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "5") int size,
			@RequestParam(name = "keyword", defaultValue = "") String mc) {
		    Page<Salle> pageSalle = salleRepository.findByNameContains(mc, PageRequest.of(page, size));


		    
		model.addAttribute("listeSalle", pageSalle.getContent());
		model.addAttribute("pages", new int[pageSalle.getTotalPages()]);
		model.addAttribute("currentPage", page);
		model.addAttribute("keyword", mc);

		return "salleview";
	}
	//fonction qui permet la suppression d'un cinéma by id
	@GetMapping("/supprimerSalle")
	public String supprimerSalle(Long id) {
		salleRepository.deleteById(id);
		return "redirect:/listeSalle";
	}
	@GetMapping("/modifierSalle")
	public String modifierSalle(Model model, Long id) {
		Salle salle =salleRepository.findById(id).get();
		model.addAttribute("salle",salle);
		return "formSalle";
	}
	@PostMapping("/saveSalle")
	public String saveSalle(Salle salle) {
		salleRepository.save(salle);
		return "confirmationsalle";
		
	}
	@GetMapping("/formSalle")
	public String formSalle(Model model) {
		model.addAttribute("salle", new Salle());
		return "formSalle";
		
	}
}



