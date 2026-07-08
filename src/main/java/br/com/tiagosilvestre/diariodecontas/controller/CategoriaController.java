package br.com.tiagosilvestre.diariodecontas.controller;

import br.com.tiagosilvestre.diariodecontas.dto.CadastroCategoriaRequest;
import br.com.tiagosilvestre.diariodecontas.dto.CategoriaResponse;
import br.com.tiagosilvestre.diariodecontas.service.CategoriaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {

	private final CategoriaService categoriaService;

	public CategoriaController(CategoriaService categoriaService) {
		this.categoriaService = categoriaService;
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CategoriaResponse cadastrar(@Valid @RequestBody CadastroCategoriaRequest request) {
		return categoriaService.cadastrar(request);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void excluir(@PathVariable Long id) {
		categoriaService.excluir(id);
	}
}
