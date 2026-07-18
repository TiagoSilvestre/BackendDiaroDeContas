package br.com.tiagosilvestre.diariodecontas.service;

import br.com.tiagosilvestre.diariodecontas.dto.CadastroCategoriaRequest;
import br.com.tiagosilvestre.diariodecontas.dto.CategoriaResponse;
import br.com.tiagosilvestre.diariodecontas.exception.CategoriaEmUsoException;
import br.com.tiagosilvestre.diariodecontas.exception.CategoriaNaoEncontradaException;
import br.com.tiagosilvestre.diariodecontas.model.Categoria;
import br.com.tiagosilvestre.diariodecontas.repository.CategoriaRepository;
import br.com.tiagosilvestre.diariodecontas.repository.GastoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaService {

	private final CategoriaRepository categoriaRepository;
	private final GastoRepository gastoRepository;

	public CategoriaService(CategoriaRepository categoriaRepository, GastoRepository gastoRepository) {
		this.categoriaRepository = categoriaRepository;
		this.gastoRepository = gastoRepository;
	}

	public List<CategoriaResponse> listar() {
		return categoriaRepository.findAll().stream()
				.map(this::toResponse)
				.toList();
	}

	public CategoriaResponse cadastrar(CadastroCategoriaRequest request) {
		Categoria categoria = new Categoria();
		categoria.setNome(request.nome().trim());
		Categoria salva = categoriaRepository.save(categoria);
		return toResponse(salva);
	}

	public void excluir(Long id) {
		if (!categoriaRepository.existsById(id)) {
			throw new CategoriaNaoEncontradaException(id);
		}

		if (gastoRepository.existsByCategoriaId(id)) {
			throw new CategoriaEmUsoException(id);
		}

		categoriaRepository.deleteById(id);
	}

	private CategoriaResponse toResponse(Categoria categoria) {
		return new CategoriaResponse(categoria.getId(), categoria.getNome());
	}
}
