package br.com.tiagosilvestre.diariodecontas.service;

import br.com.tiagosilvestre.diariodecontas.dto.GastoRequest;
import br.com.tiagosilvestre.diariodecontas.dto.GastoResponse;
import br.com.tiagosilvestre.diariodecontas.dto.ListagemGastosResponse;
import br.com.tiagosilvestre.diariodecontas.exception.CategoriaNaoEncontradaException;
import br.com.tiagosilvestre.diariodecontas.model.Categoria;
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
			if (!categoriaRepository.existsById(categoriaId)) {
				throw new CategoriaNaoEncontradaException(categoriaId);
			}

			gastos = gastoRepository.findByCategoriaId(categoriaId);
		} else {
			gastos = gastoRepository.findAll();
		}

		List<GastoResponse> gastosResponse = gastos.stream()
				.map(this::toResponse)
				.toList();

		BigDecimal valorTotal = gastos.stream()
				.map(Gasto::getValor)
				.reduce(BigDecimal.ZERO, BigDecimal::add);

		return new ListagemGastosResponse(gastosResponse, valorTotal);
	}

	public GastoResponse cadastrar(GastoRequest request) {
		Categoria categoria = categoriaRepository.findById(request.categoriaId())
				.orElseThrow(() ->
						new CategoriaNaoEncontradaException(request.categoriaId()));

		Gasto gasto = new Gasto();
		gasto.setValor(request.valor());
		gasto.setDescricao(request.descricao().trim());
		gasto.setCategoria(categoria);

		Gasto salvo = gastoRepository.save(gasto);

		return toResponse(salvo);
	}

	private GastoResponse toResponse(Gasto gasto) {
		return new GastoResponse(
				gasto.getId(),
				gasto.getValor(),
				gasto.getCategoria().getId(),
				gasto.getDescricao()
		);
	}
}
