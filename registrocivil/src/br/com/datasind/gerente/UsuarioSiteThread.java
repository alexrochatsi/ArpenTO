package br.com.datasind.gerente;

import br.com.datasind.entidade.UsuarioSite;



public class UsuarioSiteThread {
	
	private static UsuarioSiteThread instance;
	
	
	private UsuarioSiteThread() {
		
	}
	
	ThreadLocal<UsuarioSite> threadLocal = new ThreadLocal<UsuarioSite>();
	
	public static synchronized UsuarioSiteThread getInstance() {
		if (instance == null) {
			instance = new UsuarioSiteThread();
		}
		return instance;
	}
	
	public void set(UsuarioSite usuarioSite) {
		threadLocal.set(usuarioSite);
	}
	
	public UsuarioSite get() {
		return threadLocal.get();
	}
}

