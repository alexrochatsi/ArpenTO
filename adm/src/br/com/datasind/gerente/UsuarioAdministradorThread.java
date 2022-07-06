package br.com.datasind.gerente;

import br.com.datasind.entidade.UsuarioAdministrador;



public class UsuarioAdministradorThread {
	
	private static UsuarioAdministradorThread instance;
	
	
	private UsuarioAdministradorThread() {
		
	}
	
	ThreadLocal<UsuarioAdministrador> threadLocal = new ThreadLocal<UsuarioAdministrador>();
	
	public static synchronized UsuarioAdministradorThread getInstance() {
		if (instance == null) {
			instance = new UsuarioAdministradorThread();
		}
		return instance;
	}
	
	public void set(UsuarioAdministrador usuarioAdministrador) {
		threadLocal.set(usuarioAdministrador);
	}
	
	public UsuarioAdministrador get() {
		return threadLocal.get();
	}
}

