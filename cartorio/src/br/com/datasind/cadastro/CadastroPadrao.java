package br.com.datasind.cadastro;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.persister.entity.AbstractEntityPersister;

import br.com.datasind.entidade.EntidadePadrao;
import br.com.datasind.gerente.GerenteTransacao;

@SuppressWarnings("rawtypes")
public class CadastroPadrao implements Cadastro {
	private ContextoCadastro contextoCadastro;

	public static final int NONE = 1;

	public static final int READ = 2;

	public static final int UPGRADE = 3;

	public static final int UPGRADE_NOWAIT = 4;

	private static final Class[] ORDER_CLASSES = new Class[] { String.class, Date.class };

	/**
	 * 
	 */
	public CadastroPadrao() {
		super();
	}

	/**
	 * 
	 */
	public void setContexto(ContextoCadastro contexto) {
		contextoCadastro = contexto;
	}

	/**
	 * 
	 * @return
	 */
	public ContextoCadastro getContexto() {
		return contextoCadastro;
	}

	public List findByQuery(Object queryObject) throws CadastroException {
		try {
			return findByExample(queryObject.getClass(), queryObject, new QueryConfig()).list();

		} catch (HibernateException e) {
			throw new CadastroException(e);
		} catch (CadastroException e) {
			return new ArrayList();
		}
	}

	public Object findUniqueResultByQuery(Object queryObject) throws CadastroException {
		try {
			return findByExample(queryObject.getClass(), queryObject, new QueryConfig()).uniqueResult();

		} catch (HibernateException e) {
			throw new CadastroException(e);

		} catch (CadastroException e) {
			return new ArrayList();
		}
	}

	public List findByQuery(Class clazz, Object queryObject) throws CadastroException {
		try {
			return findByExample(clazz, queryObject, new QueryConfig()).list();

		} catch (HibernateException e) {
			throw new CadastroException(e);

		} catch (CadastroException e) {
			return new ArrayList();
		}
	}

	public List findByQuery(Class clazz, Object queryObject, QueryConfig config) throws CadastroException {
		try {
			return findByExample(clazz, queryObject, config).list();

		} catch (HibernateException e) {
			throw new CadastroException(e);

		} catch (CadastroException e) {
			return new ArrayList();
		}
	}

	public Object findUniqueResultByQuery(Class clazz, Object queryObject) throws CadastroException {
		try {
			return findByExample(clazz, queryObject, new QueryConfig()).uniqueResult();

		} catch (HibernateException e) {
			throw new CadastroException(e);
		}
	}

	private Session session;

	/**
	 * Este metodo e usado apenas com finalidade de testes
	 * 
	 * @param session
	 */
	public void setSession(Session session) {
		this.session = session;
	}

	public Session getSession() {
		if (session == null) {
			GerenteTransacao gerenteTransacao = getContexto().getFabricaGerente().getGerenteTransacao();
			session = (Session) gerenteTransacao.getSessaoAtual();
		}
		return session;
	}

	private ClassInfo getClassMetadata(org.hibernate.SessionFactory sessionFactory, Class clazz) {
		Class original = clazz;
		ClassMetadata metadata = null;
		while (clazz != null && metadata == null) {
			metadata = sessionFactory.getClassMetadata(clazz);
			if (metadata != null) {
				ClassInfo classInfo = new ClassInfo();
				classInfo.classMetadata = metadata;
				classInfo.clazz = clazz;

				return classInfo;
			}
			clazz = clazz.getSuperclass();
		}
		throw new RuntimeException("Nao foi possivel encontrar metadados para " + original);
	}

	private class ClassInfo {
		ClassMetadata classMetadata;
		Class clazz;
	}

	/**
	 * Localiza todos os objetos da entidade passada como parametro com base nas
	 * propriedades da entidade. Por exemplo, se findByQuery e chamada passando
	 * um objeto Funcionario, com a propriedade nomeCompleto preenchida, todos
	 * os Funcionarios com a propriedade nomeCompleto que se adequarem(1) ao
	 * nomeCompleto do paramtro, sera retornados pelo metodo.
	 * 
	 * (1) Adequacao - Em geral adequacao significa para valores primitivos ou
	 * wrappers ==, para objetos nao String Obj.getId() == Par.getId() e para
	 * String temos duas variavies:
	 * <ul>
	 * <li>1a - Quando o caractere '#' e colocado ao final da String, adequacao
	 * significa Obj.equals(Par).</li>
	 * <li>2a - Quando o caractere '#' nao esta presente, adequacao significa
	 * Obj.startsWith(Par)</li>
	 * </ul>
	 * 
	 * @throws CadastroException
	 */
	private Query findByExample(Class clazz, Object example, QueryConfig config) throws CadastroException {
		Session session = null;
		Map<String, Object> nomes = new HashMap<String, Object>();

		Object valor;

		try {
			session = getSession();

			ClassInfo info = getClassMetadata(session.getSessionFactory(), clazz);

			ClassMetadata classMetadata = info.classMetadata;
			clazz = info.clazz;

			String[] properties = classMetadata.getPropertyNames();

			// Usado em MODO_ORDENACAO_PRIMEIRA_STRING
			String primeiraString = null;

			Method getter;
			for (int i = 0; i < properties.length; i++) {
				String name = "get" + Character.toUpperCase(properties[i].charAt(0)) + properties[i].substring(1);
				try {

					getter = getMethodInHierarqui(clazz, name, new Class[] {});
					getter.setAccessible(true);

					valor = getter.invoke(example, new Object[0]);
					if (valor != null && !(valor instanceof Collection) && !(valor instanceof Map)) {
						nomes.put(properties[i], valor);

						if (isSubclassOf(getter.getReturnType(), ORDER_CLASSES) && primeiraString == null) {
							primeiraString = properties[i];
						}
					}

				} catch (IllegalArgumentException e) {
					e.printStackTrace();

				} catch (IllegalAccessException e) {
					e.printStackTrace();

				} catch (InvocationTargetException e) {
					e.printStackTrace();

				} catch (SecurityException e) {
					e.printStackTrace();

				} catch (NoSuchMethodException e) {
					e.printStackTrace();
				}
			}

			StringBuffer queryBuffer = new StringBuffer("from " + clazz.getName() + " o");
			if (nomes.size() > 0) {
				queryBuffer.append(" where");

			} else {
				throw new CadastroException("Objeto exemplo vazio " + example);
			}

			for (Iterator i = nomes.keySet().iterator(); i.hasNext();) {
				String chave = (String) i.next();
				valor = nomes.get(chave);
				if (valor instanceof String) {
					if (((String) valor).endsWith("#")) {
						queryBuffer.append(" o." + chave + " = " + ":" + chave);

					} else {
						queryBuffer.append(" o." + chave + " like " + ":" + chave);
					}

					queryBuffer.append(" and");

				} else {
					queryBuffer.append(" o." + chave + " = " + ":" + chave);
					queryBuffer.append(" and");

				}

			}

			if (nomes.size() > 0) {
				queryBuffer.delete(queryBuffer.length() - 4, queryBuffer.length());
			}

			if (config.getOrderObject() != null) {
				Map<String, Object> map = buscarPropriedadesComAlgumValor(config.getOrderObject().getClass(), config.getOrderObject(), session);

				Set<String> keys = map.keySet();
				if (keys.size() > 0) {
					queryBuffer.append("order by ");
					for (Iterator iter = keys.iterator(); iter.hasNext();) {
						queryBuffer.append("o." + iter.next() + ", ");
					}
					queryBuffer.delete(queryBuffer.length() - 2, queryBuffer.length());
				}
			}
			if (config.getModoOrdenacao() == QueryConfig.MODO_ORDENACAO_PRIMEIRA_STRING) {
				if (primeiraString == null) {
					primeiraString = "id";
				}
				queryBuffer.append(" order by o." + primeiraString);

			} else if (config.getModoOrdenacao() == QueryConfig.MODO_ORDENACAO_ID) {
				queryBuffer.append(" order by o.id");
			}

			Query query = session.createQuery(queryBuffer.toString());
			for (Iterator i = nomes.keySet().iterator(); i.hasNext();) {
				String chave = (String) i.next();
				Object atual = nomes.get(chave);

				if (atual instanceof String) {
					String string = (String) atual;
					if (string.endsWith("#")) {
						query.setParameter(chave, string.substring(0, string.length() - 1));

					} else {
						query.setParameter(chave, "%" + atual + "%");

					}

				} else {
					query.setParameter(chave, atual);

				}

			}

			return query;
		} catch (HibernateException e) {
			throw new CadastroException(e);

		}
	}

	private boolean isSubclassOf(Class<?> subclass, Class<?>[] superclasses) {
		for (int i = 0; i < superclasses.length; i++) {
			if (superclasses[i].isAssignableFrom(subclass)) {
				return true;
			}
		}
		return false;
	}

	private Map<String, Object> buscarPropriedadesComAlgumValor(Class clazz, Object object, Session session) throws HibernateException {
		ClassMetadata classMetadata = session.getSessionFactory().getClassMetadata(clazz);
		String[] properties = classMetadata.getPropertyNames();

		Map<String, Object> nomes = new TreeMap<String, Object>();
		Object valor;
		Method getter;
		for (int i = 0; i < properties.length; i++) {
			String name = "get" + Character.toUpperCase(properties[i].charAt(0)) + properties[i].substring(1);
			try {

				getter = getMethodInHierarqui(clazz, name, new Class[] {});
				getter.setAccessible(true);

				valor = getter.invoke(object, new Object[0]);
				if (valor != null && !(valor instanceof Collection) && !(valor instanceof Map)) {
					nomes.put(properties[i], valor);
				}

			} catch (IllegalArgumentException e) {
				e.printStackTrace();

			} catch (IllegalAccessException e) {
				e.printStackTrace();

			} catch (InvocationTargetException e) {
				e.printStackTrace();

			} catch (SecurityException e) {
				e.printStackTrace();

			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			}
		}
		return nomes;
	}

	@SuppressWarnings("unchecked")
	private Method getMethodInHierarqui(Class clazz, String name, Class[] params) throws NoSuchMethodException, SecurityException {

		Method method = null;
		try {
			method = clazz.getDeclaredMethod(name, params);
		} catch (NoSuchMethodException e) {
			if (clazz.getSuperclass() == null) {
				return null;
			}
			return getMethodInHierarqui(clazz.getSuperclass(), name, params);
		}
		return method;
	}

	public List findByQuery(Object queryObject, Object select) throws CadastroException {
		Session session = null;

		if (!queryObject.getClass().isAssignableFrom(select.getClass())) {
			throw new IllegalArgumentException("queryObject e select devem ser da mesma classe");
		}

		Class clazz = queryObject.getClass();
		Method[] methods = clazz.getMethods();

		Map<String, Object> nomesWhere = new HashMap<String, Object>();
		List<String> nomesSelect = new ArrayList<String>();

		List<Method> metodosSelect = new ArrayList<Method>();

		for (int i = 0; i < methods.length; i++) {
			if ((methods[i].getDeclaringClass() != Object.class) && (methods[i].getName().startsWith("get"))) {
				try {
					Object valorWhere = methods[i].invoke(queryObject, new Object[0]);
					if (valorWhere != null && !(valorWhere instanceof Collection) && !(valorWhere instanceof Map)) {

						String nome = methods[i].getName().substring(3);
						nome = nome.substring(0, 1).toLowerCase() + nome.substring(1);

						nomesWhere.put(nome, valorWhere);
					}

					Object valorSelect = methods[i].invoke(select, new Object[0]);
					if ((valorSelect != null && !(valorSelect instanceof Collection) && !(valorSelect instanceof Map)) || methods[i].getName().equals("getId")) {

						String nome = methods[i].getName().substring(3);
						nome = nome.substring(0, 1).toLowerCase() + nome.substring(1);
						nomesSelect.add(nome);

						nome = methods[i].getName().replaceFirst("get", "set");

						Method setter = getMethodInHierarqui(clazz, nome, new Class[] { methods[i].getReturnType() });
						setter.setAccessible(true);

						metodosSelect.add(setter);

					}

				} catch (IllegalArgumentException e) {
					e.printStackTrace();

				} catch (IllegalAccessException e) {
					e.printStackTrace();

				} catch (InvocationTargetException e) {
					e.printStackTrace();

				} catch (SecurityException e) {
					e.printStackTrace();

				} catch (NoSuchMethodException e) {
					e.printStackTrace();
				}
			}
		}

		if (nomesSelect.size() == 0) {
			throw new CadastroException("Pelo menos uma propriedade deve ser selecionada");
		}

		StringBuffer queryBuffer = new StringBuffer("select ");
		for (Iterator i = nomesSelect.iterator(); i.hasNext();) {
			queryBuffer.append("o." + i.next() + " ,");
		}
		queryBuffer.delete(queryBuffer.length() - 2, queryBuffer.length());

		queryBuffer.append(" from " + clazz.getName() + " o");
		if (nomesWhere.size() > 0) {
			queryBuffer.append(" where");
		} else {
			return new ArrayList();
		}

		for (Iterator i = nomesWhere.keySet().iterator(); i.hasNext();) {
			String chave = (String) i.next();
			Object valor = nomesWhere.get(chave);
			if (valor instanceof String) {
				if (((String) valor).endsWith("#")) {
					queryBuffer.append(" o." + chave + " = " + ":" + chave);

				} else {
					queryBuffer.append(" o." + chave + " like " + ":" + chave);
				}

				queryBuffer.append(" and");

			} else {
				queryBuffer.append(" o." + chave + " = " + ":" + chave);
				queryBuffer.append(" and");

			}

		}

		queryBuffer.delete(queryBuffer.length() - 4, queryBuffer.length());

		try {
			GerenteTransacao gerenteTransacao = getContexto().getFabricaGerente().getGerenteTransacao();
			session = (Session) gerenteTransacao.getSessaoAtual();

			Query query = session.createQuery(queryBuffer.toString());
			for (Iterator i = nomesWhere.keySet().iterator(); i.hasNext();) {
				String chave = (String) i.next();
				Object atual = nomesWhere.get(chave);

				if (atual instanceof String) {
					String string = (String) atual;
					if (string.endsWith("#")) {
						query.setParameter(chave, string.substring(0, string.length() - 1));

					} else {
						query.setParameter(chave, atual + "%");

					}

				} else {
					query.setParameter(chave, atual);

				}

			}

			List results = query.list();
			List<Object> objetos = new ArrayList<Object>();
			try {
				for (Iterator i = results.iterator(); i.hasNext();) {
					Object valor = i.next();

					Object[] dados = null;
					if (valor instanceof Object[]) {
						dados = (Object[]) valor;

					} else {
						dados = new Object[1];
						dados[0] = valor;
					}

					valor = clazz.newInstance();
					objetos.add(valor);

					for (int j = 0; j < metodosSelect.size(); j++) {
						((Method) metodosSelect.get(j)).invoke(valor, new Object[] { dados[j] });
					}
				}
			} catch (InstantiationException e) {
				e.printStackTrace();

			} catch (IllegalAccessException e) {
				e.printStackTrace();

			} catch (IllegalArgumentException e) {
				e.printStackTrace();

			} catch (InvocationTargetException e) {
				e.printStackTrace();

			}

			return objetos;
		} catch (HibernateException e) {
			throw new CadastroException(e);

		}
	}

	public Object findById(Object object, Integer id) throws CadastroException {
		return findById(object.getClass(), id);
	}

	public List findAll(Class<? extends EntidadePadrao> clazz) throws CadastroException {
		try {
			GerenteTransacao gerenteTransacao = getContexto().getFabricaGerente().getGerenteTransacao();
			Session session = (Session) gerenteTransacao.getSessaoAtual();

			return session.createQuery("from " + clazz.getName() + " o").list();

		} catch (HibernateException e) {
			throw new CadastroException(e);
		}
	}

	public EntidadePadrao findById(Class clazz, Integer id) throws CadastroException {
		try {
			GerenteTransacao gerenteTransacao = getContexto().getFabricaGerente().getGerenteTransacao();
			Session session = (Session) gerenteTransacao.getSessaoAtual();

			// return session.createCriteria(clazz).add(Expression.eq("id",
			// id)).list();
			return (EntidadePadrao) session.createQuery("from " + clazz.getName() + " o where o.id = :id").setInteger("id", id).uniqueResult();

		} catch (HibernateException e) {
			e.printStackTrace();
			throw new CadastroException(e);
		}
	}

	/**
	 * Inclui uma entidade
	 * 
	 * @param objeto
	 * @return Retorna o objeto incluido com o id preenchido.
	 * @throws CadastroException
	 */
	public EntidadePadrao incluirEntidade(EntidadePadrao objeto) throws CadastroException {
		try {
			GerenteTransacao gerenteTransacao = getContexto().getFabricaGerente().getGerenteTransacao();
			Session session = (Session) gerenteTransacao.getSessaoAtual();

			session.save(objeto);

		} catch (HibernateException e) {
			throw new CadastroException(e);

		}
		return objeto;
	}

	/**
	 * Inclui uma entidade
	 * 
	 * @param objeto
	 * @return Retorna o objeto incluido com o id preenchido.
	 * @throws CadastroException
	 */
	public EntidadePadrao incluirEntidadeEComit(EntidadePadrao objeto) throws CadastroException {
		try {
			GerenteTransacao gerenteTransacao = getContexto().getFabricaGerente().getGerenteTransacao();
			Session session = (Session) gerenteTransacao.getSessaoAtual();

			session.save(objeto);
			session.flush();
			session.beginTransaction().commit();

		} catch (HibernateException e) {
			throw new CadastroException(e);

		}
		return objeto;
	}

	/**
	 * Atualiza a entidade
	 * 
	 * @param objeto
	 * @return Retorna o objeto atualizado.
	 * @throws CadastroException
	 */
	public EntidadePadrao atualizarEntidade(EntidadePadrao objeto) throws CadastroException {
		try {
			GerenteTransacao gerenteTransacao = getContexto().getFabricaGerente().getGerenteTransacao();
			Session session = (Session) gerenteTransacao.getSessaoAtual();
			session.update(objeto);

		} catch (HibernateException e) {
			throw new CadastroException(e);
		}
		return objeto;
	}

	/**
	 * 
	 * @author OsmarJunior
	 * @param objeto
	 * @return
	 * @throws CadastroException
	 * @since 14/11/2012
	 * 
	 */
	public EntidadePadrao atualizarEntidadeEComit(EntidadePadrao objeto) throws CadastroException {
		try {
			GerenteTransacao gerenteTransacao = getContexto().getFabricaGerente().getGerenteTransacao();
			Session session = (Session) gerenteTransacao.getSessaoAtual();
			session.update(objeto);
			session.flush();
			session.beginTransaction().commit();

		} catch (HibernateException e) {
			throw new CadastroException(e);
		}
		return objeto;
	}

	/**
	 * Exclui a entidade.
	 * 
	 * @param objeto
	 * @return Retorna o objeto excluido.
	 * @throws CadastroException
	 */
	public EntidadePadrao excluirEntidade(EntidadePadrao objeto) throws CadastroException {
		try {
			GerenteTransacao gerenteTransacao = getContexto().getFabricaGerente().getGerenteTransacao();
			Session session = (Session) gerenteTransacao.getSessaoAtual();

			session.delete(objeto);

		} catch (HibernateException e) {
			throw new CadastroException(e);
		}
		return objeto;
	}

	public EntidadePadrao excluirEntidadeEComit(EntidadePadrao objeto) throws CadastroException {
		try {
			GerenteTransacao gerenteTransacao = getContexto().getFabricaGerente().getGerenteTransacao();
			Session session = (Session) gerenteTransacao.getSessaoAtual();
			session.delete(objeto);
			session.flush();
			session.beginTransaction().commit();

		} catch (HibernateException e) {
			throw new CadastroException(e);
		}
		return objeto;
	}

	public void atualizarSessaoAtual(Object objecto) throws CadastroException {
		try {
			GerenteTransacao gerenteTransacao = getContexto().getFabricaGerente().getGerenteTransacao();
			Session session = (Session) gerenteTransacao.getSessaoAtual();

			if (!session.contains(objecto)) {
				session.clear();
				session.lock(objecto, LockMode.NONE);
			}

		} catch (HibernateException e) {
			throw new CadastroException(e);
		}
	}

	public void inicializarProxy(Object object) throws CadastroException {
		if (!Hibernate.isInitialized(object)) {
			try {
				Hibernate.initialize(object);
			} catch (HibernateException e) {
				throw new CadastroException(e);
			}
		}
	}

	public void liberarSessao(Object objecto) throws CadastroException {
		try {
			GerenteTransacao gerenteTransacao = getContexto().getFabricaGerente().getGerenteTransacao();
			Session session = (Session) gerenteTransacao.getSessaoAtual();

			if (!(objecto instanceof Collection)) {
				session.evict(objecto);
			} else {
				Collection c = (Collection) objecto;
				for (Iterator iter = c.iterator(); iter.hasNext();) {
					session.evict(iter.next());
				}
			}

		} catch (HibernateException e) {
			throw new CadastroException(e);
		}
	}

	public Object incluirOuAtualizarEntidade(Object entidade) throws CadastroException {
		try {
			GerenteTransacao gerenteTransacao = getContexto().getFabricaGerente().getGerenteTransacao();
			Session session = (Session) gerenteTransacao.getSessaoAtual();

			EntidadePadrao obj = (EntidadePadrao) entidade;

			if (obj.getId() == null) {
				session.save(obj);
			}

			else {
				session.update(obj);
			}
			return obj;

		} catch (HibernateException e) {
			throw new CadastroException(e);
		}
	}

	public void makeDetached(EntidadePadrao entidade) throws CadastroException {
		try {
			GerenteTransacao gerenteTransacao = getContexto().getFabricaGerente().getGerenteTransacao();
			Session session = (Session) gerenteTransacao.getSessaoAtual();
			session.evict(entidade);

		} catch (HibernateException e) {
			throw new CadastroException(e);
		}
	}

	public void lock(Object object, int modo) throws CadastroException {
		try {
			GerenteTransacao gerenteTransacao = getContexto().getFabricaGerente().getGerenteTransacao();
			Session session = (Session) gerenteTransacao.getSessaoAtual();

			session.lock(object, getLockMode(modo));

		} catch (HibernateException e) {
			throw new CadastroException(e);
		}
	}

	@SuppressWarnings("deprecation")
	public void lockEntity(Class<? extends EntidadePadrao> clazz) throws CadastroException {

		try {
			GerenteTransacao gerenteTransacao = getContexto().getFabricaGerente().getGerenteTransacao();
			Session session = (Session) gerenteTransacao.getSessaoAtual();

			Connection connection = session.connection();
			AbstractEntityPersister classMetadata = (AbstractEntityPersister) session.getSessionFactory().getClassMetadata(clazz);

			Statement stmt = connection.createStatement();
			stmt.executeUpdate("lock table " + classMetadata.getTableName() + " in exclusive mode");

		} catch (HibernateException e) {
			throw new CadastroException(e);

		} catch (SQLException e) {
			throw new CadastroException(e);
		}
	}

	private LockMode getLockMode(int modo) {
		switch (modo) {
		case 1:
			return LockMode.NONE;
		case 2:
			return LockMode.READ;
		case 3:
			return LockMode.UPGRADE;
		case 4:
			return LockMode.UPGRADE_NOWAIT;
		case 5:
			return LockMode.WRITE;
		default:
			throw new IllegalArgumentException("Modo de lock nao suportado: " + modo);
		}
	}

	@SuppressWarnings("deprecation")
	public Map findByNumeroSequencia(String tabela, Integer tipo, Integer instituicao) throws CadastroException {

		Session session = null;
		try {
			GerenteTransacao gerenteTransacao = getContexto().getFabricaGerente().getGerenteTransacao();
			session = (Session) gerenteTransacao.getSessaoAtual();

			Connection connection = session.connection();

			CallableStatement statement = connection.prepareCall("{call SGI.SP_RETORNASEQUENCIAANUAL(?,?,?,?,?,?,?)}");

			int i = 1;
			statement.setString(i++, tabela);
			statement.setInt(i++, instituicao);
			statement.setInt(i++, tipo);

			statement.registerOutParameter(i++, Types.INTEGER);
			statement.registerOutParameter(i++, Types.INTEGER);
			statement.registerOutParameter(i++, Types.VARCHAR);
			statement.registerOutParameter(i++, Types.VARCHAR);

			statement.execute();

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("numerogerado", statement.getString(4));
			map.put("sequencia", statement.getString(5));
			map.put("ano", statement.getString(6));
			map.put("erro", statement.getString(7));

			return map;

		} catch (HibernateException e) {
			throw new CadastroException(e);

		} catch (SQLException e) {
			throw new CadastroException(e);
		}
	}
}