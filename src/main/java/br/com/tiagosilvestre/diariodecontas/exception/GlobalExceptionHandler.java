package br.com.tiagosilvestre.diariodecontas.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(CategoriaNaoEncontradaException.class)
	public ResponseEntity<Map<String, String>> handleCategoriaNaoEncontrada(CategoriaNaoEncontradaException ex) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("mensagem", ex.getMessage()));
	}

	@ExceptionHandler(CategoriaEmUsoException.class)
	public ResponseEntity<Map<String, String>> handleCategoriaEmUso(CategoriaEmUsoException ex) {
		return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("mensagem", ex.getMessage()));
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> handleValidacao(MethodArgumentNotValidException ex) {
		Map<String, String> erros = new LinkedHashMap<>();
		ex.getBindingResult().getFieldErrors()
				.forEach(erro -> erros.put(erro.getField(), erro.getDefaultMessage()));
		return ResponseEntity.badRequest().body(erros);
	}
}
