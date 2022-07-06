package br.com.datasind.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import br.com.datasind.aplicacao.Configuracao;

/**
 * 
 * @author OsmarJunior
 * @since 19 de nov de 2015
 */

public final class Constantes {

	public static final String REGEX_WHERE_MANDADO_BUSCA_CLASSE = (String) Configuracao.getInstancia().get("sgi.configuracao.regexWhereMandadosBuscaClasse");

	public static final String REGEX_WHERE_MANDADO_BUSCA_MOVIMENTACOES = (String) Configuracao.getInstancia().get("sgi.configuracao.regexWhereMandadosBuscaMov");

	public static final String REGEX_WHERE_BUSCA = (String) Configuracao.getInstancia().get("sgi.configuracao.regexWhereMandadosBusca");

	public static final List<String> LIST_REGEX_WHERE_MANDADO_BUSCA_CLASSE() {
		List<String> listRegexWhereMandadosBusca = new ArrayList<String>();
		String regexWhereMandadosBusca = REGEX_WHERE_MANDADO_BUSCA_CLASSE;
		if (regexWhereMandadosBusca != null) {
			String[] itens = regexWhereMandadosBusca.split(Pattern.quote("|"));
			for (int i = 0; i < itens.length; i++) {
				listRegexWhereMandadosBusca.add(itens[i]);
			}
		}

		return listRegexWhereMandadosBusca;
	}

	public static final List<String> LIST_REGEX_WHERE_MANDADO_BUSCA_MOVIMENTACAO() {
		List<String> listRegexWhereMandadosBusca = new ArrayList<String>();
		String regexWhereMandadosBusca = REGEX_WHERE_MANDADO_BUSCA_MOVIMENTACOES;
		if (regexWhereMandadosBusca != null) {
			String[] itens = regexWhereMandadosBusca.split(Pattern.quote("|"));
			for (int i = 0; i < itens.length; i++) {
				listRegexWhereMandadosBusca.add(itens[i]);
			}
		}

		return listRegexWhereMandadosBusca;
	}

	public static final List<String> LIST_REGEX_WHERE_BUSCA() {
		List<String> listRegexWhereBusca = new ArrayList<String>();
		String regexWhereMandadosBusca = (String) Configuracao.getInstancia().get("sgi.configuracao.regexWhereBusca");
		if (regexWhereMandadosBusca != null) {
			String[] itens = regexWhereMandadosBusca.split(Pattern.quote("|"));
			for (int i = 0; i < itens.length; i++) {
				listRegexWhereBusca.add(itens[i]);
			}
		}

		return listRegexWhereBusca;
	}

	public static final int TIPO_REGISTRO_BUSCAS = 0;
	public static final int TIPO_REGISTRO_MOVIMENTACOES = 1;
	public static final int TIPO_REGISTRO_MANDADOS = 2;
	public static final int TIPO_REGISTRO_ALVARAS = 3;
	public static final String[] DESC_TIPO_REGISTRO = { "Busca e Apreensões", "Movimentações Proc.", "Mandados de Busca", "Alvarás" };

}
