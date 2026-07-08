package br.com.tiagosilvestre.diariodecontas.model;

import java.math.BigDecimal;

public class Gasto {

	private Long id;
	private BigDecimal valor;
	private Long categoriaId;
	private String descricao;

	public Gasto() {
	}

	public Gasto(Long id, BigDecimal valor, Long categoriaId, String descricao) {
		this.id = id;
		this.valor = valor;
		this.categoriaId = categoriaId;
		this.descricao = descricao;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public Long getCategoriaId() {
		return categoriaId;
	}

	public void setCategoriaId(Long categoriaId) {
		this.categoriaId = categoriaId;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
}
