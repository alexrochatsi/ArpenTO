package br.com.datasind.gerente;

import br.com.datasind.entidade.Usuario;



public class UsuarioThread {
	
	private static UsuarioThread instance;
	
	
	private UsuarioThread() {
		
	}
	
	ThreadLocal<Usuario> threadLocal = new ThreadLocal<Usuario>();
	
	public static synchronized UsuarioThread getInstance() {
		if (instance == null) {
			instance = new UsuarioThread();
		}
		return instance;
	}
	
	public void set(Usuario usuario) {
		threadLocal.set(usuario);
	}
	
	public Usuario get() {
		return threadLocal.get();
	}
}

