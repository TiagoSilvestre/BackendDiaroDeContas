package br.com.tiagosilvestre.diariodecontas.service;

import br.com.tiagosilvestre.diariodecontas.dto.CadastroGastoRequest;
import br.com.tiagosilvestre.diariodecontas.dto.GastoResponse;
import br.com.tiagosilvestre.diariodecontas.dto.ListagemGastosResponse;
import br.com.tiagosilvestre.diariodecontas.exception.CategoriaNaoEncontradaException;
import br.com.tiagosilvestre.diariodecontas.model.Gasto;
import br.com.tiagosilvestre.diariodecontas.repository.CategoriaRepository;
import br.com.tiagosilvestre.diariodecontas.repository.GastoRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class GastoService {

	private final GastoRepository gastoRepository;
	private final CategoriaRepository categoriaRepository;

	public GastoService(GastoRepository gastoRepository, CategoriaRepository categoriaRepository) {
		this.gastoRepository = gastoRepository;
		this.categoriaRepository = categoriaRepository;
	}

	public ListagemGastosResponse listar(Long categoriaId) {
		List<Gasto> gastos;
		if (categoriaId != null) {
			if (categoriaRepository.buscarPorId(categoriaId).isEmpty()) {
				throw new CategoriaNaoEncontradaException(categoriaId);
			}
			gastos = gastoRepository.listarPorCategoriaId(categoriaId);
		} else {
			gastos = gastoRepository.listarTodos();
		}

		List<GastoResponse> gastosResponse = gastos.stream()
				.map(this::toResponse)
				.toList();

		BigDecimal valorTotal = gastos.stream()
				.map(Gasto::getValor)
				.reduce(BigDecimal.ZERO, BigDecimal::add);

		return new ListagemGastosResponse(gastosResponse, valorTotal);
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
