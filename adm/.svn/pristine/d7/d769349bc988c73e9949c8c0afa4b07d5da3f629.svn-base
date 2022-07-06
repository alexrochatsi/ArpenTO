package br.com.datasind.gerente;

import br.com.datasind.entidade.UsuarioCartorio;



public class UsuarioCartorioThread {
	
	private static UsuarioCartorioThread instance;
	
	
	private UsuarioCartorioThread() {
		
	}
	
	ThreadLocal<UsuarioCartorio> threadLocal = new ThreadLocal<UsuarioCartorio>();
	
	public static synchronized UsuarioCartorioThread getInstance() {
		if (instance == null) {
			instance = new UsuarioCartorioThread();
		}
		return instance;
	}
	
	public void set(UsuarioCartorio usuarioCartorio) {
		threadLocal.set(usuarioCartorio);
	}
	
	public UsuarioCartorio get() {
		return threadLocal.get();
	}
}

