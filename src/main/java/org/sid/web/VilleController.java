package org.sid.web;

import org.sid.dao.VilleRepository;
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
public class VilleController {
	@Autowired
	VilleRepository villeRepository;

	@GetMapping("/listeVille")
	public String listVille(Model model, @RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "5") int size,
			@RequestParam(name = "keyword", defaultValue = "") String mc) {
		    Page<Ville> pageVille = villeRepository.findByNameContains(mc, PageRequest.of(page, size));


		    
		model.addAttribute("listeVille", pageVille.getContent());
		model.addAttribute("pages", new int[pageVille.getTotalPages()]);
		model.addAttribute("currentPage", page);
		model.addAttribute("keyword", mc);

		return "villeview";
	}
	//fonction qui permet la suppression d'un cinéma by id
	@GetMapping("/supprimerVille")
	public String supprimerVille(Long id) {
		villeRepository.deleteById(id);
		return "redirect:/listeVille";
	}
	@GetMapping("/modifierVille")
	public String modifierVille(Model model, Long id) {
		Ville ville =villeRepository.findById(id).get();
		model.addAttribute("ville",ville);
		return "formVille";
	}
	@PostMapping("/saveVille")
	public String saveVille(Ville ville) {
		villeRepository.save(ville);
		return "confirmationville";
		
	}
	@GetMapping("/formVille")
	public String formVille(Model model) {
		model.addAttribute("ville", new Ville());
		return "formVille";
		
	}
}



