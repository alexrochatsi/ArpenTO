package br.com.datasind.xproc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParamEproc {

	private String urlEproc; // https://eproc1.tjto.jus.br/eprocV2_prod_1grau/
	private String urlEprocLogin;// https://eproc1.tjto.jus.br/eprocV2_prod_1grau/externo_controlador.php?acao=entrar&alt=21
	private String urlConsulta; // https://eproc1.tjto.jus.br/eprocV2_prod_1grau/externo_controlador.php?acao=processo_consulta_publica&acao_origem=&acao_retorno=processo_consulta_publica
	private String urlConsultaInterna; // https://eproc1.tjto.jus.br/eprocV2_prod_1grau/externo_controlador.php
	private String urlConsultaInternaPrefixo; // https://eproc1.tjto.jus.br/eprocV2_prod_1grau/externo_controlador.php?acao=processo_seleciona_publica&acao_origem=processo_consulta_publica&acao_retorno=processo_consulta_publica&num_processo=
	private String urlConsultaInternaSufixo; // &num_chave=&hash=&num_chave_documento=

	private String regexWhereMandadosBusca; // <![CDATA[
											// [[:<:]]APEENSAO|BUSCA|REINTEGRACAO|CARTA
											// PRECATORIA|MONITORIA|EXECUCAO DE
											// TITULO
											// EXTRAJUDICIAL|DEPOSITO[[:>:]] ]]>
	private List<String> listRegexWhereMandadosBusca;

	private Map<String, String> usuariosEproc = new HashMap<String, String>();

	private Map<String, String> paramsProcessoDetalhe = new HashMap<String, String>();
	private Map<String, String> paramsListaProcessos = new HashMap<String, String>();

	private String charsetProcessoDetalhe; // ISO-8859-1

	private int numeroThreads;

	private int intervaloConsultaProcessosNovos;
	private int intervaloConsultaInternaProcessos;

	private String capaIdDataAutuacao; // txtAutuacao
	private String capaIdSituacao; // txtSituacao
	private String capaIdOrgaoJulgador; // txtOrgaoJulgador
	private String capaIdJuiz; // txtMagistrado
	private String capaIdClasse; // txtClasse

	public ParamEproc() {
	}

	public String getUrlEproc() {
		return urlEproc;
	}

	public void setUrlEproc(String urlEproc) {
		this.urlEproc = urlEproc;
	}

	public String getUrlConsulta() {
		return urlConsulta;
	}

	public void setUrlConsulta(String urlConsulta) {
		this.urlConsulta = urlConsulta;
	}

	public String getUrlConsultaInterna() {
		return urlConsultaInterna;
	}

	public void setUrlConsultaInterna(String urlConsultaInterna) {
		this.urlConsultaInterna = urlConsultaInterna;
	}

	public String getUrlConsultaInternaPrefixo() {
		return urlConsultaInternaPrefixo;
	}

	public void setUrlConsultaInternaPrefixo(String urlConsultaInternaPrefixo) {
		this.urlConsultaInternaPrefixo = urlConsultaInternaPrefixo;
	}

	public String getUrlConsultaInternaSufixo() {
		return urlConsultaInternaSufixo;
	}

	public void setUrlConsultaInternaSufixo(String urlConsultaInternaSufixo) {
		this.urlConsultaInternaSufixo = urlConsultaInternaSufixo;
	}

	// public String getEprocSenha(String usuario) {
	// try{
	// return Utilitaria.decrypt(Constantes.KEY_SEGURANCA,
	// usuariosEproc.get(usuario));
	// }catch(Exception e){
	// return null;
	// }
	// }

	// public void addUsuarioEproc(String usuario, String senha) {
	// try{
	// usuariosEproc.put(usuario, Utilitaria.encrypt(Constantes.KEY_SEGURANCA,
	// senha)) ;
	// }catch(Exception e){
	// e.printStackTrace();
	// }
	// }

	public int getIntervaloConsultaProcessosNovos() {
		return intervaloConsultaProcessosNovos;
	}

	public void setIntervaloConsultaProcessosNovos(
			int intervaloConsultaProcessosNovos) {
		this.intervaloConsultaProcessosNovos = intervaloConsultaProcessosNovos;
	}

	public int getIntervaloConsultaInternaProcessos() {
		return intervaloConsultaInternaProcessos;
	}

	public void setIntervaloConsultaInternaProcessos(
			int intervaloConsultaInternaProcessos) {
		this.intervaloConsultaInternaProcessos = intervaloConsultaInternaProcessos;
	}

	public String getCapaIdDataAutuacao() {
		return capaIdDataAutuacao;
	}

	public void setCapaIdDataAutuacao(String capaIdDataAutuacao) {
		this.capaIdDataAutuacao = capaIdDataAutuacao;
	}

	public String getCapaIdSituacao() {
		return capaIdSituacao;
	}

	public void setCapaIdSituacao(String capaIdSituacao) {
		this.capaIdSituacao = capaIdSituacao;
	}

	public String getCapaIdOrgaoJulgador() {
		return capaIdOrgaoJulgador;
	}

	public void setCapaIdOrgaoJulgador(String capaIdOrgaoJulgador) {
		this.capaIdOrgaoJulgador = capaIdOrgaoJulgador;
	}

	public String getCapaIdJuiz() {
		return capaIdJuiz;
	}

	public void setCapaIdJuiz(String capaIdJuiz) {
		this.capaIdJuiz = capaIdJuiz;
	}

	public String getCapaIdClasse() {
		return capaIdClasse;
	}

	public void setCapaIdClasse(String capaIdClasse) {
		this.capaIdClasse = capaIdClasse;
	}

	public Map<String, String> getUsuariosEproc() {
		return usuariosEproc;
	}

	public void setUsuariosEproc(Map<String, String> usuariosEproc) {
		this.usuariosEproc = usuariosEproc;
	}

	public Map<String, String> getParamsProcessoDetalhe() {
		return paramsProcessoDetalhe;
	}

	public void setParamsProcessoDetalhe(
			Map<String, String> paramsProcessoDetalhe) {
		this.paramsProcessoDetalhe = paramsProcessoDetalhe;
	}

	public Map<String, String> getParamsListaProcessos() {
		return paramsListaProcessos;
	}

	public void setParamsListaProcessos(Map<String, String> paramsListaProcessos) {
		this.paramsListaProcessos = paramsListaProcessos;
	}

	public String getUrlEprocLogin() {
		return urlEprocLogin;
	}

	public void setUrlEprocLogin(String urlEprocLogin) {
		this.urlEprocLogin = urlEprocLogin;
	}

	public int getNumeroThreads() {
		return numeroThreads;
	}

	public void setNumeroThreads(int numeroThreads) {
		this.numeroThreads = numeroThreads;
	}

	public String getCharsetProcessoDetalhe() {
		return charsetProcessoDetalhe;
	}

	public void setCharsetProcessoDetalhe(String charsetProcessoDetalhe) {
		this.charsetProcessoDetalhe = charsetProcessoDetalhe;
	}

	public String getRegexWhereMandadosBusca() {
		return regexWhereMandadosBusca;
	}

	public void setRegexWhereMandadosBusca(String regexWhereMandadosBusca) {
		this.regexWhereMandadosBusca = regexWhereMandadosBusca;
	}

	public List<String> getListRegexWhereMandadosBusca() {
		listRegexWhereMandadosBusca = new ArrayList<String>();

		if (this.regexWhereMandadosBusca != null) {
			String[] itens = this.regexWhereMandadosBusca.split("|");
			for (int i = 0; i < itens.length; i++) {
				listRegexWhereMandadosBusca.add(itens[i]);
			}
		}

		return listRegexWhereMandadosBusca;
	}
}
