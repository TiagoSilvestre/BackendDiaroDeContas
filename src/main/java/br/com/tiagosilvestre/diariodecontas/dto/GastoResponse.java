package br.com.tiagosilvestre.diariodecontas.dto;

import java.math.BigDecimal;

public record GastoResponse(
        Long id,
        BigDecimal valor,
        Long categoriaId,
        String descricao) {
}
