package br.com.tiagosilvestre.diariodecontas.service;

import br.com.tiagosilvestre.diariodecontas.dto.CadastroGastoRequest;
import br.com.tiagosilvestre.diariodecontas.dto.GastoResponse;
import br.com.tiagosilvestre.diariodecontas.exception.CategoriaNaoEncontradaException;
import br.com.tiagosilvestre.diariodecontas.model.Gasto;
import br.com.tiagosilvestre.diariodecontas.repository.CategoriaRepository;
import br.com.tiagosilvestre.diariodecontas.repository.GastoRepository;
import org.springframework.stereotype.Service;

@Service
public class GastoService {

	private final GastoRepository gastoRepository;
	private final CategoriaRepository categoriaRepository;

	public GastoService(GastoRepository gastoRepository, CategoriaRepository categoriaRepository) {
		this.gastoRepository = gastoRepository;
		this.categoriaRepository = categoriaRepository;
	}

	public GastoResponse cadastrar(CadastroGastoRequest request) {
		if (categoriaRepository.buscarPorId(request.categoriaId()).isEmpty()) {
			throw new CategoriaNaoEncontradaException(request.categoriaId());
		}

		Gasto gasto = new Gasto();
		gasto.setValor(request.valor());
		gasto.setCategoriaId(request.categoriaId());
		gasto.setDescricao(request.descricao().trim());

		Gasto salvo = gastoRepository.salvar(gasto);
		return toResponse(salvo);
	}

	private GastoResponse toResponse(Gasto gasto) {
		return new GastoResponse(
				gasto.getId(),
				gasto.getValor(),
				gasto.getCategoriaId(),
				gasto.getDescricao()
		);
	}
}
