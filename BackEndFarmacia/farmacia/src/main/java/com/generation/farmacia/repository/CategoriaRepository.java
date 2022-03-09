package com.generation.farmacia.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.generation.farmacia.Model.CategoriaModel;

	@Repository
	public interface CategoriaRepository extends JpaRepository<CategoriaModel, Long>{
		public List<CategoriaModel>findAllByTituloContainingIgnoreCase(String titulo);
		
	}


