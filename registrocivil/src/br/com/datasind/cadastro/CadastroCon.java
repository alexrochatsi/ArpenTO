package br.com.datasind.cadastro;

import java.sql.Connection;

public class CadastroCon extends CadastroPadrao {
   
   @SuppressWarnings("deprecation")
	public Connection retornaConnexao() throws CadastroException {
		return getSession().connection();
	}
}
