package br.com.tiagosilvestre.diariodecontas.controller;

import br.com.tiagosilvestre.diariodecontas.dto.CadastroGastoRequest;
import br.com.tiagosilvestre.diariodecontas.dto.GastoResponse;
import br.com.tiagosilvestre.diariodecontas.service.GastoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/gastos")
public class GastoController {

	private final GastoService gastoService;

	public GastoController(GastoService gastoService) {
		this.gastoService = gastoService;
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public GastoResponse cadastrar(@Valid @RequestBody CadastroGastoRequest request) {
		return gastoService.cadastrar(request);
	}
}
