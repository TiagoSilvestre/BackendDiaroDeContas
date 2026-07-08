package br.com.tiagosilvestre.diariodecontas.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "app.storage")
public class StorageProperties {

	private String categoriasFile = "data/categorias.txt";
	private String gastosFile = "data/gastos.txt";

	public String getCategoriasFile() {
		return categoriasFile;
	}

	public void setCategoriasFile(String categoriasFile) {
		this.categoriasFile = categoriasFile;
	}

	public String getGastosFile() {
		return gastosFile;
	}

	public void setGastosFile(String gastosFile) {
		this.gastosFile = gastosFile;
	}
}
