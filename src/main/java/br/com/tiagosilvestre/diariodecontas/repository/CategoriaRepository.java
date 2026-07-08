package br.com.tiagosilvestre.diariodecontas.repository;

import br.com.tiagosilvestre.diariodecontas.model.Categoria;

import java.util.List;
import java.util.Optional;

public interface CategoriaRepository {

	Categoria salvar(Categoria categoria);

	Optional<Categoria> buscarPorId(Long id);

	List<Categoria> listarTodas();

	boolean excluirPorId(Long id);
}
