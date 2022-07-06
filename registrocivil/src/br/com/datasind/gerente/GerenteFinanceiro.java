package br.com.datasind.gerente;

import java.io.File;
import java.math.BigDecimal;
import java.sql.Connection;
import java.util.Date;
import java.util.List;

import org.jrimum.texgit.Record;

import br.com.datasind.aplicacao.ApplicationException;
import br.com.datasind.entidade.EntidadeBancaria;
import br.com.datasind.entidade.GuiaAssistencial;
import br.com.datasind.entidade.Remessa;
import br.com.datasind.entidade.Retorno;
import br.com.datasind.entidade.SegmentoT;
import br.com.datasind.entidade.SegmentoU;

public interface GerenteFinanceiro extends GerenteModulo {

	public Connection retornaConexao() throws ApplicationException;

	/**
	 * Busca somente associados <b>Ativos</b> e com <b>Atividade</b> Preenchida.
	 * 
	 * @author OsmarJunior
	 * @param idUf
	 * @param idCidade
	 * @param idEmpresa
	 * @return List<Associado>
	 * @throws ApplicationException
	 * @since 31/10/2013
	 * 
	 */

	public List<GuiaAssistencial> obterListaGuiaDiversasEmAbertoPorEmpresa(Integer idEmpresa, Date vencimentoInicial, Date vencimentoFinal) throws ApplicationException;

	public List<GuiaAssistencial> obterListaGuiaDiversasAtivaPorAssociado(Integer idAssociado, Date vencimentoInicial, Date vencimentoFinal) throws ApplicationException;

	/************ IMPORTCAO SINDICAL ********************************/

	public Retorno importacaoSindicalHeaderArquivo(Record hd, Retorno retornoSindical) throws ApplicationException;

	public Retorno importacaoSindicalHeaderLote(Record hd, Retorno retornoSindical) throws ApplicationException;

	public Boolean verificaArquivoRetornoJaImportado(Retorno retornoSindical) throws ApplicationException;

	public SegmentoT importacaoSindicalSegmentoT(Record record, SegmentoT segmentoT, Retorno retorno) throws ApplicationException;

	public SegmentoU importacaoSindicalSegmentoU(Record record, SegmentoU segmentoU) throws ApplicationException;

	/****************************
	 * IMPORTACAO ASSISTENCIAL
	 *********************************/

	public Retorno importacaoSIGCB240HeaderArquivo(Record hd, Retorno retorno) throws ApplicationException;

	public Retorno importacaoSIGCB240HeaderLote(Record hd, Retorno retorno) throws ApplicationException;

	public SegmentoT importacaoSIGCB240SegmentoT(Record record, SegmentoT segmentoT, Retorno retorno) throws ApplicationException;

	public SegmentoT importacaoItau240SegmentoT(Record record, SegmentoT segmentoT, Retorno retorno) throws ApplicationException;

	public SegmentoU importacaoSIGCB240SegmentoU(Record record, SegmentoU segmentoU) throws ApplicationException;

	public SegmentoU importacaoItau240SegmentoU(Record record, SegmentoU segmentoU, BigDecimal tarifas) throws ApplicationException;

	public GuiaAssistencial obterGuiaAssistencialPorNossoNumero(String nossoNumero) throws ApplicationException;
	
	/****************************
	 * EXPORTAÇÃO REMESSA
	 *********************************/

	public File geraArquivoRemessa(List<GuiaAssistencial> guias, EntidadeBancaria entBancaria, Remessa remessa) throws ApplicationException;

	public List<Remessa> obterListaRemessa() throws ApplicationException;

}
