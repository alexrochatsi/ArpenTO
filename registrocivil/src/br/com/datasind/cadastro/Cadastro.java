package br.com.datasind.cadastro;

import java.util.List;

import br.com.datasind.entidade.EntidadePadrao;

@SuppressWarnings("rawtypes")
public interface Cadastro {
	public void setContexto(ContextoCadastro contexto);

	public List findByQuery(Object queryObject) throws CadastroException;

	public List findByQuery(Class clazz, Object queryObject) throws CadastroException;

	public EntidadePadrao incluirEntidade(EntidadePadrao objeto) throws CadastroException;

	public EntidadePadrao atualizarEntidade(EntidadePadrao objeto) throws CadastroException;

	public EntidadePadrao excluirEntidade(EntidadePadrao objeto) throws CadastroException;

}
