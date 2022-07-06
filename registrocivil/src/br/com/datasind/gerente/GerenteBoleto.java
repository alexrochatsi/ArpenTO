package br.com.datasind.gerente;

import java.math.BigDecimal;
import java.sql.Connection;

import org.jrimum.bopepo.Boleto;
import org.jrimum.domkee.financeiro.banco.febraban.Sacado;

import br.com.datasind.aplicacao.ApplicationException;

public interface GerenteBoleto extends GerenteModulo {
	public Connection retornaConexao() throws ApplicationException;

	public Boleto geraBoletoSICOOB(Sacado sacado, BigDecimal valor) throws ApplicationException;

}