package br.com.datasind.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JLabel;

import org.jrimum.bopepo.BancosSuportados;
import org.jrimum.utilix.text.Field;
import org.jrimum.utilix.text.Filler;

public class UtilitarioBoleto {

	public static Image getHtmlAsImage(String html) {

		// JLabel label = new JLabel(getHtml1());
		JLabel label = new JLabel();

		// Este e o tamanho do campo no template.
		// Largura: 19cm = 718 px
		// Altura: 8cm = 302px
		label.setSize(718, 302);
		// label.setAlignmentX(label.CENTER_ALIGNMENT);
		// label.setAlignmentY(label.CENTER_ALIGNMENT);
		label.setVerticalAlignment(1);
		label.setText(html);
		label.setBackground(Color.WHITE);

		Image image = new BufferedImage(label.getWidth(), label.getHeight(),
				BufferedImage.TYPE_4BYTE_ABGR);

		// paint the html to an image
		Graphics graphic = image.getGraphics();
		// graphic.setClip(0,0,500,200);
		graphic.setColor(Color.WHITE);
		graphic.setFont(Font.getFont("arial"));
		label.paint(graphic);
		graphic.dispose();

		return image;

	}

	public static BancosSuportados bancoProcurado(Integer codigo) {

		if (codigo == 1) {
			return BancosSuportados.BANCO_DO_BRASIL;
		} else if (codigo == 004) {
			return BancosSuportados.BANCO_DO_NORDESTE_DO_BRASIL;
		} else if (codigo == 21) {
			return BancosSuportados.BANCO_DO_ESTADO_DO_ESPIRITO_SANTO;
		} else if (codigo == 33) {
			return BancosSuportados.BANCO_SANTANDER;
		} else if (codigo == 041) {
			return BancosSuportados.BANCO_DO_ESTADO_DO_RIO_GRANDE_DO_SUL;
		} else if (codigo == 104) {
			return BancosSuportados.CAIXA_ECONOMICA_FEDERAL;
		} else if (codigo == 237) {
			return BancosSuportados.BANCO_BRADESCO;
		} else if (codigo == 341) {
			return BancosSuportados.BANCO_ITAU;
		} else if (codigo == 356) {
			return BancosSuportados.BANCO_ABN_AMRO_REAL;
		} else if (codigo == 399) {
			return BancosSuportados.UNIBANCO;
		} else if (codigo == 422) {
			return BancosSuportados.BANCO_SAFRA;
		} else if (codigo == 453) {
			return BancosSuportados.BANCO_RURAL;
		} else if (codigo == 748) {
			return BancosSuportados.BANCO_SICREDI;
		} else if (codigo == 756) {
			return BancosSuportados.BANCOOB;
		} else {
			return null;
		}

	}

	public static String geraNumeroDocumento(Integer idTipoGuia) {

		String numeroDoc;

		@SuppressWarnings("deprecation")
		Date dataBase = new Date("01/01/2012");
		Date dataAtual = Calendar.getInstance().getTime();
		long timeDiff = dataAtual.getTime() - dataBase.getTime();
		int dias = 0;
		double temp;

		temp = timeDiff / 1000; // Convertendo Segundos;
		temp /= 60; // Convertendo Minutos;
		temp /= 60; // Convertendo Hora;
		dias = (int) (temp / 24); // Convertendo Dia;

		numeroDoc = new Field<String>(idTipoGuia + "", 5, Filler.ZERO_LEFT).write() + new Field<String>(dias + "", 4, Filler.ZERO_LEFT).write();

		return numeroDoc;

	}

	public static String calculaDVNossoNumeroSicoob(String agencia, String cedente, String nossoNumero) {
		String campos = new Field<String>(agencia, 4, Filler.ZERO_LEFT).write()
				+ new Field<String>(cedente, 10, Filler.ZERO_LEFT).write()
				+ new Field<String>(nossoNumero, 7, Filler.ZERO_LEFT).write();
		int[] values = new int[campos.length()];
		for (int i = 0; i < campos.length(); i++) {
			values[i] = Integer.parseInt(campos.charAt(i) + "");
		}
		Integer soma = 0;
		int ordem = 3;
		for (int i = 0; i < values.length; i++) {
			switch (ordem) {
			case 3: {
				soma += values[i] * ordem;
				ordem = 1;
				break;
			}
			case 1: {
				soma += values[i] * ordem;
				ordem = 9;
				break;
			}
			case 9: {
				soma += values[i] * ordem;
				ordem = 7;
				break;
			}
			case 7: {
				soma += values[i] * ordem;
				ordem = 3;
				break;
			}
			}
		}

		// System.out.println(soma);
		Integer dv = 0;

		if (soma < 11) {
			dv = soma - 11;
		} else {
			int resto = soma % 11;
			dv = 11 - resto;
		}

		if (dv > 9)
			dv = 0;

		// System.out.println(dv);

		return dv.toString();

	}
}
