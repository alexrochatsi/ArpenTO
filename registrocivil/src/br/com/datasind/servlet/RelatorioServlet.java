package br.com.datasind.servlet;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import br.com.datasind.relatorio.Relatorio;

public class RelatorioServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public static final String ATTRIBUTE_RELATORIO = "_relatorio";

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession();
		Relatorio relatorio = (Relatorio) session
				.getAttribute(ATTRIBUTE_RELATORIO);

		if (relatorio != null) {

			File f = null;
			FileInputStream fileInputStream = null;
			OutputStream outStrm = null;

			try {

				f = new File(relatorio.getPathPDF());
				fileInputStream = new FileInputStream(f);

				BufferedInputStream bstr = new BufferedInputStream(
						fileInputStream);

				int size = (int) f.length(); // pega o tamanho do arquivo em
												// bytes.
				byte[] data = new byte[size]; // aloca um array de bytes com o
												// tamanho certo.
				bstr.read(data, 0, size); // le o array de bytes.
				bstr.close();

				response.setHeader("META HTTP-EQUIV", "CACHE-CONTROL");
				response.setHeader("CONTENT", "NO-CACHE");
				response.setHeader("Expires", "0");
				response.setHeader("Pragma", "public");
				response.setContentType("application/pdf");
				response.setHeader("Content-Disposition", "inline; filename=\""
						+ request.getSession().getId() + ".pdf\"");

				outStrm = response.getOutputStream();
				outStrm.write(data);
				outStrm.flush();

			} catch (Exception e) {
				System.out.println(e.getMessage());
			} finally {
				if (outStrm != null)
					outStrm.close();
			}

			// OutputStream outputStream = response.getOutputStream();
			// RelatorioBuilder relatorioBuilder =
			// RelatorioBuilder.getInstancia();
			// relatorioBuilder.printTo(relatorio, outputStream);
			// outputStream.flush();

		}
	}
}
