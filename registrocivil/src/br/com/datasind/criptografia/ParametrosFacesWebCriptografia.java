package br.com.datasind.criptografia;

import br.com.datasind.view.jsf.HttpFacesAdapter;



public class ParametrosFacesWebCriptografia {

	public final static String DIRETORIO_TEMPORARIO_CHAVE_PUBLICA = "tempDirChavesPrivadas";
	public final static String EXTENSAO_CHAVE_PUBLICA = "chavePublicaExtensao";
	public final static String EXTENSAO_CHAVE_PRIVADA = "chavePrivateExtensao";
	
	public final static String ALGORITMO_CRIPTOGRAFIA_CHAVE = "algoritmoCriptgrafiaChave";
	public final static String ALGORITMO_CRIPTOGRAFIA_CIFRA = "algoritmoCriptografiaCifra";
	
	public final static String SUFIXO_CHAVE_PUBLICA = "sufixoChavePublica";
	public final static String SUFIXO_CHAVE_PRIVADA = "sufixoChavePrivada";

	public final static String SUFIXO_CHAVE_SIMETRICA = "sufixoChaveSimetrica";
	
	
	public static String obterParametro(String opcao){
		HttpFacesAdapter adapter = new HttpFacesAdapter();
		return adapter.getInitParameter(opcao);		
	}

}
