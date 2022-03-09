package com.generation.farmacia.Model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

	
	@Entity
	@Table(name = "tb_produto")
	
	public class ProdutoModel {
	
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private long id;
		
		@NotBlank
		@Size(min= 5, max=100)
		private String titulo; 
		
		private int preco;
		
		@ManyToOne
		@JsonIgnoreProperties("produto")  
		private CategoriaModel categoria;

		@ManyToOne
		@JsonIgnoreProperties("produto")  
		private UsuarioModel usuario;

		
		public long getId() {
			return id;
		}

		public void setId(long id) {
			this.id = id;
		}

		public String getTitulo() {
			return titulo;
		}

		public void setTitulo(String titulo) {
			this.titulo = titulo;
		}

		public int getPreco() {
			return preco;
		}

		public void setPreco(int preco) {
			this.preco = preco;
		}

		public CategoriaModel getCategoria() {
			return categoria;
		}

		public void setCategoria(CategoriaModel categoria) {
			this.categoria = categoria;
		}
		
	}		