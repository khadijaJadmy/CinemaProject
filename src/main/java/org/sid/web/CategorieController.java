package org.sid.web;

import javax.validation.Valid;

import org.sid.dao.CategorieRepository;
import org.sid.entities.Categorie;
import org.sid.entities.Film;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CategorieController {
	@Autowired
	private CategorieRepository categorieRepository;
	@GetMapping("/category")
	public String category(Model model,
		@RequestParam(name="page",defaultValue = "0") int page,
		@RequestParam(name="size",defaultValue = "5") int size,
		@RequestParam(name="keyword",defaultValue = "")String mc
	){
		Page<Categorie> pageCategorie=categorieRepository.findByNameContains(mc,PageRequest.of(page, size));
		model.addAttribute("listCategories",pageCategorie.getContent());
		model.addAttribute("pages",new int[pageCategorie.getTotalPages()]);
		
		model.addAttribute("currentPage",page);
		model.addAttribute("size",size);
		model.addAttribute("keyword",mc);
		
		return "category";
	}
	
	  @GetMapping(path="/deleteCategory")
	  public String delete(Long id,String keyword,int page,int size) {
		 
		  categorieRepository.deleteById(id);

		  return "redirect:/category?page="+page+"&size="+size+"&keyword="+keyword; 
	  }
	  @GetMapping(path="/formCategory")
	  public String formCategory(Model model) {
		  model.addAttribute("categorie",new Categorie());
		  model.addAttribute("mode","new");
		  return "formCategory"; 
	  }
	
	  @PostMapping(path="/saveCategory")
	  public String saveFilm(Model model,@Valid Categorie categorie, BindingResult bindingResult) {
		  if(bindingResult.hasErrors()) {return "formCategory";}
		  model.addAttribute("categorie",categorie);
		  categorieRepository.save(categorie);
		  return "confirmationCategory"; 
	  }
	  
	  @GetMapping(path="/editCategory")
	  public String editFilm(Model model ,Long id) {
		  Categorie f= categorieRepository.findById(id).get();
		  model.addAttribute("categorie",f);
		  model.addAttribute("mode","edit");
		  return "formCategory"; 
	  }
}
