package br.com.datasind.cadastro;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;

@SuppressWarnings("rawtypes")
public class ResultsMapList extends ArrayList {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6601110855445426090L;

	public static final Logger LOGGER = Logger.getLogger(ResultsMapList.class);

	private Object[][] valores;

	private String[] chaves;

	public ResultsMapList(String[] chaves, Object[][] valores) {
		setChaves(chaves);
		setValores(valores);
		atualizarMapas();
		LOGGER.debug("Novo ResultsMapList criado " + toString());
	}

	public ResultsMapList(String chaves, Collection valores) {
		setChaves(chaves);
		setValores(valores);
		atualizarMapas();
		LOGGER.debug("Novo ResultsMapList criado " + toString());
	}

	public ResultsMapList(String chaves, ResultSet valores) throws CadastroException {
		setChaves(chaves);
		setValores(valores);
		atualizarMapas();
		LOGGER.debug("Novo ResultsMapList criado " + toString());
	}

	public ResultsMapList(ResultSet valores) throws CadastroException {
		setChaves(valores);
		setValores(valores);
		atualizarMapas();
		LOGGER.debug("Novo ResultsMapList criado " + toString());
	}

	public ResultsMapList(String[] chaves, ResultSet valores) throws CadastroException {
		setChaves(chaves);
		setValores(valores);
		atualizarMapas();
		LOGGER.debug("Novo ResultsMapList criado " + toString());
	}

	public ResultsMapList(String[] chaves, Collection valores) {
		setChaves(chaves);
		setValores(valores);
		atualizarMapas();
		LOGGER.debug("Novo ResultsMapList criado " + toString());
	}

	protected void setChaves(String[] chaves) {
		if (this.chaves == null) {
			this.chaves = chaves;
		}
	}

	protected void setValores(Object[][] valores) {
		if (this.valores == null) {
			this.valores = valores;
		}
	}

	protected void setValores(Collection valores) {
		int size = 0;
		if (valores != null) {
			size = valores.size();
		}

		Object[][] array = new Object[size][chaves.length];
		if (size > 0) {
			int i = 0;
			for (Iterator iter = valores.iterator(); iter.hasNext(); i++) {
				Object[] row = (Object[]) iter.next();
				for (int j = 0; j < row.length; j++) {
					array[i][j] = row[j];
				}
			}
		}
		this.valores = array;
	}

	public void setValores(ResultSet resultSet) throws CadastroException {
		ArrayList<Object[]> linhas = new ArrayList<Object[]>();
		Object[] colunas;

		try {
			while (resultSet.next()) {
				colunas = new Object[chaves.length];
				for (int i = 1; i <= chaves.length; i++) {
					colunas[i - 1] = resultSet.getObject(i);
				}
				linhas.add(colunas);
			}
			valores = new Object[linhas.size()][chaves.length];
			int j = 0;
			for (Iterator i = linhas.iterator(); i.hasNext(); j++) {
				valores[j] = (Object[]) i.next();
			}
		} catch (SQLException e) {
			throw new CadastroException(e);
		}
	}

	protected void setChaves(String chaves) {
		String[] array = chaves.split(", ");
		for (int i = 0; i < array.length; i++) {
			array[i] = array[i].substring(array[i].indexOf('.') + 1);
			// array[i] = array[i].replace('.', ':');
		}
		this.chaves = array;
	}

	protected void setChaves(ResultSet resultSet) throws CadastroException {
		ResultSetMetaData metadata;
		try {
			metadata = resultSet.getMetaData();
			chaves = new String[metadata.getColumnCount()];
			for (int i = 0; i < chaves.length; i++) {
				chaves[i] = metadata.getColumnName(i + 1).toLowerCase();
			}
		} catch (SQLException e) {
			throw new CadastroException(e);
		}
	}

	@SuppressWarnings("unchecked")
	protected void atualizarMapas() {
		clear();
		if (valores.length < 1) {
			return;
		}

		if (chaves.length != valores[0].length) {
			throw new RuntimeException("A quantidade de keys e de colunas no values deve ser a mesma");
		}

		Map<String, Object> map;
		for (int i = 0; i < valores.length; i++) {
			map = new HashMap<String, Object>() {
				private static final long serialVersionUID = 1L;

				@Override
				public Object get(Object key) {

					return super.get(key);
				}
			};
			for (int j = 0; j < valores[i].length; j++) {
				map.put(chaves[j], valores[i][j]);
			}
			add(map);
		}
	}
}
