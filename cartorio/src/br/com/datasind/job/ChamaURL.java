package br.com.datasind.job;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.util.EntityUtils;

import br.com.datasind.cadastro.CadastroException;

/**
 * 
 * @author OsmarJunior
 * @since 30 de nov de 2015
 */

public class ChamaURL {

	private HttpResponse resposta;

	private DefaultHttpClient cliente;

	private BasicHttpContext contexto;

	private BasicCookieStore cookie;

	private HttpEntity entidade;

	private String html;

	public HttpResponse getResposta() {

		return resposta;
	}

	public void setResposta(HttpResponse resposta) {
		this.resposta = resposta;
	}

	public DefaultHttpClient getCliente() {
		if (cliente == null) {
			cliente = new DefaultHttpClient();
		}
		return cliente;
	}

	public void setCliente(DefaultHttpClient cliente) {
		this.cliente = cliente;
	}

	public BasicHttpContext getContexto() {
		if (contexto == null) {
			contexto = new BasicHttpContext();
		}
		return contexto;
	}

	public void setContexto(BasicHttpContext contexto) {
		this.contexto = contexto;
	}

	public BasicCookieStore getCookie() {
		if (cookie == null) {
			cookie = new BasicCookieStore();
		}
		return cookie;
	}

	public void setCookie(BasicCookieStore cookie) {
		this.cookie = cookie;
	}

	public HttpEntity getEntidade() {
		return entidade;
	}

	public void setEntidade(HttpEntity entidade) {
		this.entidade = entidade;
	}

	public String getHtml() {
		return html;
	}

	public void setHtml(String html) {
		this.html = html;
	}

	public Boolean chamaURL(String url) throws CadastroException {

		// Adicionando um sistema de redirecao
		getCliente().setRedirectStrategy(new LaxRedirectStrategy());

		// Mantendo a conexao sempre ativa
		getCliente().setKeepAliveStrategy(new DefaultConnectionKeepAliveStrategy());

		// Adicionando o coockie store no contexto de conexao
		getContexto().setAttribute(ClientContext.COOKIE_STORE, getCookie());

		// Criando o metodo de acesso
		HttpGet requisicao1 = new HttpGet(url);

		try {
			resposta = cliente.execute(requisicao1, contexto);
		} catch (IOException exception) {
			throw new CadastroException("Sem resposata do site da Receita Federal");
		}

		// Buscando a entidade
		entidade = getResposta().getEntity();

		// Transformando o conteudo em uma string
		html = null;
		try {
			html = EntityUtils.toString(getEntidade());
		} catch (IOException exception) {
			throw new CadastroException("Sem resposata do site da Receita Federal");

		} catch (ParseException e) {
			throw new CadastroException("Sem resposata do site da Receita Federal");

		}

		return true;

	}
}
