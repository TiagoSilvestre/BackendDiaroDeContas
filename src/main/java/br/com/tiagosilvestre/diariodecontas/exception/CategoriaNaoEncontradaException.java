package br.com.tiagosilvestre.diariodecontas.exception;

public class CategoriaNaoEncontradaException extends RuntimeException {

	public CategoriaNaoEncontradaException(Long id) {
		super("Categoria não encontrada com id: " + id);
	}
}
