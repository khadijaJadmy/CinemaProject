package org.sid.dao;

import org.sid.entities.Categorie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategorieRepository extends JpaRepository<Categorie, Long>{
	
	public Page<Categorie>  findByNameContains(String mc, Pageable pageable);


}
