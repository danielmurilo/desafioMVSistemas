package br.com.mv.desafioMVSistemas.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import br.com.mv.desafioMVSistemas.model.Colaborador;

@Transactional
public interface ColaboradorRepository extends CrudRepository<Colaborador, Long> {

	public List<Colaborador> findByNomeContainingIgnoreCase (String nome);
	
	public Colaborador findByCpf(String cpf);
}
