package br.com.mv.desafioMVSistemas.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import br.com.mv.desafioMVSistemas.model.Item;

@Transactional
public interface ItemRepository extends CrudRepository<Item, Integer> {
	
	public List<Item> findByNomeContainingIgnoreCase (String nome);
	
	public Item findByNome(String nome);

}
