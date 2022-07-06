package br.com.datasind.gerente;

import java.sql.Connection;
import java.util.List;

import br.com.datasind.aplicacao.ApplicationException;
import br.com.datasind.entidade.Cidade;
import br.com.datasind.entidade.EntidadeBancaria;
import br.com.datasind.entidade.TipoGuia;

public interface GerenteCadastro extends GerenteModulo{
   public Connection retornaConexao() throws ApplicationException;

   /**************** Cidade ***********************/
   public Cidade obterCidadePorDescCidadeESiglaUF(String descCidade, String siglaUF) throws ApplicationException;

   /**************** ENTIDADE BANCARIA ***********************/
   public List<EntidadeBancaria> obterListaEntidadeBancariaPorNome(String nome) throws ApplicationException;

   /**************** TIPO GUIA ***********************/
   public List<TipoGuia> obterListaTipoGuiaPorDescricao(String descricao) throws ApplicationException;

}
