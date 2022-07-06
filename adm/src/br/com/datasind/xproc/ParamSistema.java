package br.com.datasind.xproc;

public class ParamSistema {

	/*
	 * versao atual do sistema
	 */
	private String versaoSistema;	
	private String nomeEmpresa;	
	private String logoEmpresa;	
	private boolean emManutencao;
	private boolean desativaJobs;
	private boolean sistemaUnico;
	private int idUsuarioSistema;
	
	public ParamSistema() {
	}

	public String getVersaoSistema() {
		return versaoSistema;
	}

	public void setVersaoSistema(String versaoSistema) {
		this.versaoSistema = versaoSistema;
	}
	
	public String getNomeEmpresa() {
		return nomeEmpresa;
	}
	
	public void setNomeEmpresa(String nomeEmpresa) {
		this.nomeEmpresa = nomeEmpresa;
	}
	
	public String getLogoEmpresa() {
		return logoEmpresa;
	}
	
	public void setLogoEmpresa(String logoEmpresa) {
		this.logoEmpresa = logoEmpresa;
	}

	public boolean isDesativaJobs() {
		return desativaJobs;
	}
	
	public void setDesativaJobs(boolean desativaJobs) {
		this.desativaJobs = desativaJobs;
	}
	
	public boolean isEmManutencao() {
		return emManutencao;
	}
	
	public void setEmManutencao(boolean emManutencao) {
		this.emManutencao = emManutencao;
	}
	
	public boolean isSistemaUnico() {
		return sistemaUnico;
	}
	
	public void setSistemaUnico(boolean sistemaUnico) {
		this.sistemaUnico = sistemaUnico;
	}
	
	public int getIdUsuarioSistema() {
		return idUsuarioSistema;
	}
	
	public void setIdUsuarioSistema(int idUsuarioSistema) {
		this.idUsuarioSistema = idUsuarioSistema;
	}
}
