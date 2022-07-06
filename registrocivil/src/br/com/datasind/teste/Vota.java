package br.com.datasind.teste;

import br.com.datasind.cadastro.CadastroException;
import br.com.datasind.job.ChamaURL;

/**
 * 
 * @author OsmarJunior
 * @since 28 de mar de 2016
 */

public class Vota {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		ChamaURL chamaURL = new ChamaURL();
		try {
			chamaURL.chamaURL(
					"http://palmas2016.blogspot.com.br/&txtclr=%23000&hideq=true&font=normal%20normal%2012px%20Arial%2C%20Tahoma%2C%20Helvetica%2C%20FreeSans%2C%20sans-serif&lnkclr=%2300f&chrtclr=%2300f&v9147838336650106761:selectionBox=300%20HTTP/1.1");
			System.out.println("Teste");
		} catch (CadastroException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Teste");

	}

}
