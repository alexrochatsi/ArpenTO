package br.com.datasind.xproc;


/**
 * @author Pedro Isaac
 * @description Esta classe representa algumas configuraçoes necessarias para o
 *              sistema
 * 
 */
public class Configuracao {

	/**
	 * Indica qual diretorio da máquina está instalado o sistema
	 */
	private String diretorioDoContexto;

	/**
	 * indica o caminho para o xml de parametrização dos dados do banco
	 */
	private String paramDadosBanco;

	/**
	 * indica o caminho para o xml de parametrização de dados do sistema
	 */
	private String paramSistema;
	private ParamSistema configuracaoSistema;

	/**
	 * indica o caminho para o xml de parametrização do eproc
	 */
	private String paramEproc;

	private ParamEproc configuracaoEproc;

	private String diretorioTemplates;

	/**
	 * indica o caminho para o xml de parametrizacao para o relatorio
	 */
	private String paramRelatorio;

	/**
	 * Traz o contexto da aplicação Em geral "eprev"
	 */
	private String contexto;

	/**
	 * Configuracoes da conexao com banco de dados
	 */
	// private ConexaoBD conexao;
	//
	/**
	 * Objeto estático com as configuracoes setadas
	 */
	private static Configuracao configuracao;

	/**
	 * Diretorio do servidor, relativo ao contexto, onde os relatorios gerados
	 * devem ser guardados. O valor está definido no web.xml como um parametro
	 * de contexto e nao de algum servlet.
	 */
	private String diretorioRelatoriosGerados;

	private String diretorioCaptchasGerados;

	private String diretorioBackupBD;

	private String diretorioDocumentos;

	private String diretorioFotosPessoas;

	private String diretorioFotosUsuarios;

	private String rodapeRelatorio;

	/**
	 * Diretorio do servidor, relativo ao contexto, onde os relatorios do Jasper
	 * (.jasper) estao armazenados. O valor está definido no web.xml como um
	 * parametro de contexto e nao de algum servlet.
	 */
	private String diretorioRelatoriosJasper;

	/**
	 * Traz o caminho absoluto do eProc no servidor. Em geral é algo como:
	 * "c:\tomcat\webapp\ePrev"
	 */
	private String caminhoAbsolutoEprocServidor;

	/**
	 * Usado para saber se o processamento batch deve rodar nessa instancia da
	 * aplicacao. O valor está definido no web.xml como um parametro de contexto
	 * e nao de algum servlet.
	 */
	private String usarProcessamentoBatch;

	/**
	 * cria uma configuracao
	 * 
	 */
	public static Configuracao createConfiguracao() {
		if (configuracao == null) {
			configuracao = new Configuracao();
		}

		return configuracao;
	}

	public static Configuracao getInstance() {
		if (configuracao == null) {
			configuracao = new Configuracao();
		}
		return configuracao;
	}

	/**
	 * @return Returns the conexao.
	 * @deprecated
	 */
	// public ConexaoBD getConexao() {
	// return configuracao.conexao;
	// }

	/**
	 * @param conexao
	 *            The conexao to set.
	 * @deprecated
	 */
	// public void setConexao(ConexaoBD conexao) {
	// this.conexao = conexao;
	// }

	/**
	 * @return Returns the diretorioDoContexto.
	 */
	public String getDiretorioDoContexto() {
		return diretorioDoContexto;
	}

	/**
	 * @param diretorioDoContexto
	 *            The diretorioDoContexto to set.
	 */
	public void setDiretorioDoContexto(String diretorioDoContexto) {
		this.diretorioDoContexto = diretorioDoContexto;
	}

	public String getParamRelatorio() {
		return paramRelatorio;
	}

	public void setParamRelatorio(String paramRelatorio) {
		this.paramRelatorio = paramRelatorio;
	}

	public void setContexto(String contexto) {
		this.contexto = contexto;
	}

	public String getContexto() {
		return contexto;
	}

	public String getParamSistema() {
		return paramSistema;
	}

	public void setParamSistema(String paramSistema) {
		this.paramSistema = paramSistema;
	}

	public String getParamEproc() {
		return paramEproc;
	}

	public void setParamEproc(String paramEproc) {
		this.paramEproc = paramEproc;
	}

	public String getParamDadosBanco() {
		return paramDadosBanco;
	}

	public void setParamDadosBanco(String paramDadosBanco) {
		this.paramDadosBanco = paramDadosBanco;
	}

	public String getUsarProcessamentoBatch() {
		return usarProcessamentoBatch;
	}

	/**
	 * @param usarProcessamentoBatch
	 * @author mborges
	 * @since 08/03/2006
	 */
	public void setUsarProcessamentoBatch(String usarProcessamentoBatch) {
		this.usarProcessamentoBatch = usarProcessamentoBatch;
	}

	/**
	 * Diretorio do servidor, relativo ao contexto, onde os relatorios gerados
	 * devem ser guardados. O valor está definido no web.xml como um parametro
	 * de contexto e nao de algum servlet.
	 * 
	 * @return diretorioRelatoriosGerados
	 * @author Pedro Isaac
	 * @since 28/07/2012
	 */
	public String getDiretorioRelatoriosGerados() {
		return diretorioRelatoriosGerados;
	}

	/**
	 * Diretorio do servidor, relativo ao contexto, onde os relatorios gerados
	 * devem ser guardados. O valor está definido no web.xml como um parametro
	 * de contexto e nao de algum servlet.
	 * 
	 * @param diretorioRelatoriosGerados
	 * @author Pedro Isaac
	 * @since 28/07/2012
	 */
	public void setDiretorioRelatoriosGerados(String diretorioRelatoriosGerados) {
		this.diretorioRelatoriosGerados = diretorioRelatoriosGerados;
	}

	public String getDiretorioCaptchasGerados() {
		return diretorioCaptchasGerados;
	}

	public void setDiretorioCaptchasGerados(String diretorioCaptchasGerados) {
		this.diretorioCaptchasGerados = diretorioCaptchasGerados;
	}

	/**
	 * Diretorio do servidor, relativo ao contexto, onde os relatorios do Jasper
	 * (.jasper) estao armazenados. O valor está definido no web.xml como um
	 * parametro de contexto e nao de algum servlet.
	 * 
	 * @return diretorioRelatoriosJasper
	 * @author Pedro Isaac
	 * @since 28/07/2012
	 */
	public String getDiretorioRelatoriosJasper() {
		return diretorioRelatoriosJasper;
	}

	/**
	 * Diretorio do servidor, relativo ao contexto, onde os relatorios do Jasper
	 * (.jasper) estao armazenados. O valor está definido no web.xml como um
	 * parametro de contexto e nao de algum servlet.
	 * 
	 * @param diretorioRelatoriosJasper
	 * @author Pedro Isaac
	 * @since 28/07/2012
	 */
	public void setDiretorioRelatoriosJasper(String diretorioRelatoriosJasper) {
		this.diretorioRelatoriosJasper = diretorioRelatoriosJasper;
	}

	public String getCaminhoAbsolutoEprocServidor() {
		return caminhoAbsolutoEprocServidor;
	}

	public void setCaminhoAbsolutoEprocServidor(
			String caminhoAbsolutoEprocServidor) {
		this.caminhoAbsolutoEprocServidor = caminhoAbsolutoEprocServidor;
	}

	public String getDiretorioBackupBD() {
		return this.diretorioBackupBD;
	}

	public void setDiretorioBackupBD(String diretorioBackupBD) {
		this.diretorioBackupBD = diretorioBackupBD;
	}

	public String getDiretorioDocumentos() {
		return diretorioDocumentos;
	}

	public void setDiretorioDocumentos(String diretorioDocumentos) {
		this.diretorioDocumentos = diretorioDocumentos;
	}

	public String getDiretorioFotosPessoas() {
		return diretorioFotosPessoas;
	}

	public void setDiretorioFotosPessoas(String diretorioFotosPessoas) {
		this.diretorioFotosPessoas = diretorioFotosPessoas;
	}

	public String getDiretorioFotosUsuarios() {
		return diretorioFotosUsuarios;
	}

	public void setDiretorioFotosUsuarios(String diretorioFotosUsuarios) {
		this.diretorioFotosUsuarios = diretorioFotosUsuarios;
	}

	public String getRodapeRelatorio() {
		return rodapeRelatorio;
	}

	public void setRodapeRelatorio(String rodapeRelatorio) {
		this.rodapeRelatorio = rodapeRelatorio;
	}

	public ParamEproc getConfiguracaoEproc() {
		return configuracaoEproc;
	}

	public void setConfiguracaoEproc(ParamEproc configuracaoEproc) {
		this.configuracaoEproc = configuracaoEproc;
	}

	public ParamSistema getConfiguracaoSistema() {
		return configuracaoSistema;
	}

	public void setConfiguracaoSistema(ParamSistema configuracaoSistema) {
		this.configuracaoSistema = configuracaoSistema;
	}

	public String getDiretorioTemplates() {
		return diretorioTemplates;
	}

	public void setDiretorioTemplates(String diretorioTemplates) {
		this.diretorioTemplates = diretorioTemplates;
	}
}
