package br.com.tiagosilvestre.diariodecontas.repository;

import br.com.tiagosilvestre.diariodecontas.config.StorageProperties;
import br.com.tiagosilvestre.diariodecontas.model.Categoria;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class FileCategoriaRepository implements CategoriaRepository {

	private static final String SEPARATOR = "|";

	private final Path filePath;

	public FileCategoriaRepository(StorageProperties storageProperties) {
		this.filePath = Path.of(storageProperties.getCategoriasFile());
	}

	@Override
	public synchronized Categoria salvar(Categoria categoria) {
		List<Categoria> categorias = carregarTodas();

		if (categoria.getId() == null) {
			long proximoId = categorias.stream()
					.mapToLong(Categoria::getId)
					.max()
					.orElse(0L) + 1;
			categoria.setId(proximoId);
			categorias.add(categoria);
		} else {
			boolean encontrada = false;
			for (int i = 0; i < categorias.size(); i++) {
				if (categorias.get(i).getId().equals(categoria.getId())) {
					categorias.set(i, categoria);
					encontrada = true;
					break;
				}
			}
			if (!encontrada) {
				categorias.add(categoria);
			}
		}

		persistir(categorias);
		return categoria;
	}

	@Override
	public synchronized Optional<Categoria> buscarPorId(Long id) {
		return carregarTodas().stream()
				.filter(categoria -> categoria.getId().equals(id))
				.findFirst();
	}

	@Override
	public synchronized List<Categoria> listarTodas() {
		return carregarTodas();
	}

	@Override
	public synchronized boolean excluirPorId(Long id) {
		List<Categoria> categorias = carregarTodas();
		boolean removida = categorias.removeIf(categoria -> categoria.getId().equals(id));
		if (removida) {
			persistir(categorias);
		}
		return removida;
	}

	private List<Categoria> carregarTodas() {
		try {
			List<Categoria> categorias = new ArrayList<>();
			for (String linha : FileStorageHelper.readAll(filePath)) {
				if (linha.isBlank()) {
					continue;
				}
				categorias.add(deserializar(linha));
			}
			return categorias;
		} catch (IOException ex) {
			throw new UncheckedIOException("Erro ao ler categorias do arquivo", ex);
		}
	}

	private void persistir(List<Categoria> categorias) {
		try {
			List<String> linhas = categorias.stream()
					.map(this::serializar)
					.toList();
			FileStorageHelper.writeAll(filePath, linhas);
		} catch (IOException ex) {
			throw new UncheckedIOException("Erro ao salvar categorias no arquivo", ex);
		}
	}

	private String serializar(Categoria categoria) {
		return categoria.getId() + SEPARATOR + categoria.getNome();
	}

	private Categoria deserializar(String linha) {
		String[] partes = linha.split("\\|", 2);
		if (partes.length != 2) {
			throw new IllegalStateException("Linha inválida no arquivo de categorias: " + linha);
		}
		return new Categoria(Long.parseLong(partes[0]), partes[1]);
	}
}
