package br.com.datasind.teste;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

/**
 * 
 * @author OsmarJunior
 * @since 04/07/2013
 */

public class ShowCookies {

	public static void main(String[] args) {

		// instância da classe do client HTTP
		DefaultHttpClient client = new DefaultHttpClient();

		// objeto onde serão armazenados as informações relativas aos cookies
		CookieStore cookieStore = new BasicCookieStore();

		// criação de um contexto utilizado nas requisições para que possamos
		// acessar
		// as informações dos cookies
		HttpContext localContext = new BasicHttpContext();
		localContext.setAttribute(ClientContext.COOKIE_STORE, cookieStore);

		// instância o objeto HttpGet
		HttpGet httpget = new HttpGet("http://www.receita.fazenda.gov.br/pessoajuridica/cnpj/cnpjreva/cnpjreva_solicitacao2.asp");

		try {
			// efetua a resquisição GET
			HttpResponse response = client.execute(httpget, localContext);
			response.setLocale(new Locale("en_US"));
			// trabalha a resposta da requisição
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				String responseBody = EntityUtils.toString(entity);

				// exibe o conteúdo do corpo da resposta da requisição GET
				System.out.println(responseBody);

				// exibe os conteúdo dos cookies definidos pelo domínio
				List<Cookie> cookies = cookieStore.getCookies();
				for (Cookie cookie : cookies) {
					System.out.println("Cookie: " + cookie.getName() + "=" + cookie.getValue());
				}
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();

		} catch (IOException e) {
			e.printStackTrace();

		}
	}
}
