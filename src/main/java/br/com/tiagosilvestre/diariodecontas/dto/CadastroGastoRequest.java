package br.com.tiagosilvestre.diariodecontas.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record CadastroGastoRequest(
		@NotNull(message = "O valor é obrigatório")
		@DecimalMin(value = "0.01", message = "O valor deve ser maior que zero")
		BigDecimal valor,

		@NotNull(message = "A categoria é obrigatória")
		Long categoriaId,

		@NotBlank(message = "A descrição é obrigatória")
		@Size(max = 200, message = "A descrição deve ter no máximo 200 caracteres")
		String descricao
) {
}
