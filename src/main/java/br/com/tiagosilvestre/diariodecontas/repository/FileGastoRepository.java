package br.com.tiagosilvestre.diariodecontas.repository;

import br.com.tiagosilvestre.diariodecontas.config.StorageProperties;
import br.com.tiagosilvestre.diariodecontas.model.Gasto;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Repository
public class FileGastoRepository implements GastoRepository {

	private static final String SEPARATOR = "|";

	private final Path filePath;

	public FileGastoRepository(StorageProperties storageProperties) {
		this.filePath = Path.of(storageProperties.getGastosFile());
	}

	@Override
	public synchronized Gasto salvar(Gasto gasto) {
		List<Gasto> gastos = carregarTodos();

		long proximoId = gastos.stream()
				.mapToLong(Gasto::getId)
				.max()
				.orElse(0L) + 1;
		gasto.setId(proximoId);
		gastos.add(gasto);

		persistir(gastos);
		return gasto;
	}

	@Override
	public synchronized boolean existePorCategoriaId(Long categoriaId) {
		return carregarTodos().stream()
				.anyMatch(gasto -> gasto.getCategoriaId().equals(categoriaId));
	}

	private List<Gasto> carregarTodos() {
		try {
			List<Gasto> gastos = new ArrayList<>();
			for (String linha : FileStorageHelper.readAll(filePath)) {
				if (linha.isBlank()) {
					continue;
				}
				gastos.add(deserializar(linha));
			}
			return gastos;
		} catch (IOException ex) {
			throw new UncheckedIOException("Erro ao ler gastos do arquivo", ex);
		}
	}

	private void persistir(List<Gasto> gastos) {
		try {
			List<String> linhas = gastos.stream()
					.map(this::serializar)
					.toList();
			FileStorageHelper.writeAll(filePath, linhas);
		} catch (IOException ex) {
			throw new UncheckedIOException("Erro ao salvar gastos no arquivo", ex);
		}
	}

	private String serializar(Gasto gasto) {
		return gasto.getId()
				+ SEPARATOR + gasto.getValor().toPlainString()
				+ SEPARATOR + gasto.getCategoriaId()
				+ SEPARATOR + gasto.getDescricao();
	}

	private Gasto deserializar(String linha) {
		String[] partes = linha.split("\\|", 4);
		if (partes.length != 4) {
			throw new IllegalStateException("Linha inválida no arquivo de gastos: " + linha);
		}
		return new Gasto(
				Long.parseLong(partes[0]),
				new BigDecimal(partes[1]),
				Long.parseLong(partes[2]),
				partes[3]
		);
	}
}
