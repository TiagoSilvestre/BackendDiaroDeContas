package br.com.tiagosilvestre.diariodecontas.repository;

import br.com.tiagosilvestre.diariodecontas.model.Gasto;

public interface GastoRepository {

	Gasto salvar(Gasto gasto);

	boolean existePorCategoriaId(Long categoriaId);
}
