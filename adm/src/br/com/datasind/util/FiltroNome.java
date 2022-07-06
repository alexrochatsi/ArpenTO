package br.com.datasind.util;

import java.io.File;
import java.io.FilenameFilter;
/**
 * 
 * @author OsmarJunior
 * @since 17/09/2011
 */
public class FiltroNome implements FilenameFilter {

	public FiltroNome(String nome) {
		setNome(nome);
	}

	private String nome;

	public boolean accept(File dir, String name) {
		int index = name.lastIndexOf('.');
		if (index > 0) {
			return getNome().equals(name.substring(0, index));

		} else {
			return getNome().equals(name);

		}
		
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
}