package br.com.datasind.teste;

import java.io.IOException;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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

@ManagedBean(name = "testaURL")
@SessionScoped
public class TestaURL {

	public String cookieSTR;

	public void teste() {
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
					cookieSTR = "Cookie: " + cookie.getName() + "=" + cookie.getValue();
				}
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();

		} catch (IOException e) {
			e.printStackTrace();

		}

	}

	public void atualiza(ActionEvent e) {
		teste();
	}

	public String getCookieSTR() {
		return cookieSTR;
	}

	public void setCookieSTR(String cookieSTR) {
		this.cookieSTR = cookieSTR;
	}

	public int countReload = 0;

	public String reload() {
		try {
			countReload++;
			if (countReload < 2) {
				return "";
			} else {
				HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
				HttpSession session = request.getSession(false);
				session.invalidate();
				FacesContext temp = FacesContext.getCurrentInstance();
				temp.getExternalContext().redirect("../login.jsf");

			}
		} catch (IOException ex) {

		}
		return null;
	}

}
