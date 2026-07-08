package br.com.tiagosilvestre.diariodecontas.exception;

public class CategoriaEmUsoException extends RuntimeException {

	public CategoriaEmUsoException(Long id) {
		super("Não é possível excluir a categoria " + id + " pois existem gastos vinculados a ela");
	}
}
