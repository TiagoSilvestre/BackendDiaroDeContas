package br.com.tiagosilvestre.diariodecontas.dto;

import java.math.BigDecimal;
import java.util.List;

public record ListagemGastosResponse(
		List<GastoResponse> gastos,
		BigDecimal valorTotal
) {
}
