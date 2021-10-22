package br.com.mv.desafioMVSistemas.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.TreeSet;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


import lombok.Data;

@Data
@Entity @Table(name = "CAFE_DA_MANHA")
public class CafeDaManha implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	private LinkedHashMap<Colaborador, TreeSet<Item>> itensPorColaborador = new LinkedHashMap<>();
	
		
}
