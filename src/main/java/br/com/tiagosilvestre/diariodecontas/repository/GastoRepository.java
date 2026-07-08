package br.com.tiagosilvestre.diariodecontas.repository;

import br.com.tiagosilvestre.diariodecontas.model.Gasto;

import java.util.List;

public interface GastoRepository {

	Gasto salvar(Gasto gasto);

	boolean existePorCategoriaId(Long categoriaId);

	List<Gasto> listarTodos();

	List<Gasto> listarPorCategoriaId(Long categoriaId);
}
