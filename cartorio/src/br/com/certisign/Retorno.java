
package br.com.certisign;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
import org.codehaus.jackson.map.ObjectMapper;

import br.com.certisign.certisignon.tools.certificados.NewCripto2;

public class Retorno extends HttpServlet{
   private static final long serialVersionUID=1L;

   public Retorno() {}

   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	  doPost(request , response);
   }

   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	  // obter retorno encriptado da resposta
	  String ret=request.getParameter("cb");
	  ret=request.getParameter("cb");
	  HttpSession session=request.getSession();

	  ret=ret.replace(" " , "");

	  if(ret.equals("ErroaoobterCertificado.")) {
		 session.setAttribute("msgLoginSessao" , "Erro ao capturar dados do certificado!");
	  } else {

		 String decriptado="";
		 try {
			//obter chave que foi feito o download
			InputStream chave=getServletContext().getResourceAsStream("/WEB-INF/keys/CERTISIGNON_PROD_4.pk");

			String schave=IOUtils.toString(chave , "UTF-8");

			//usar client e chave para decriptar o retorno encriptado
			decriptado=NewCripto2.decrypt(ret, schave);

			decriptado=decriptado.replace("CertificadoBean : " , "");
			decriptado=decriptado.replace("'" , "\"");

			//transformando JSON em Objeto
			ObjectMapper mapper=new ObjectMapper();
			CertificadoBean certificado=mapper.readValue(decriptado , CertificadoBean.class);

			session.setAttribute("certificadoBeanSessao" , certificado);

		 } catch (Exception e) {
			e.printStackTrace();
			request.getRequestDispatcher("/login.jsf").forward(request , response);
		 }
	  }
	  request.getRequestDispatcher("/test/valida.jsf").forward(request , response);
   }
}
