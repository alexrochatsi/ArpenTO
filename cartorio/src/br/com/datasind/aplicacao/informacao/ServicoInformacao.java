package br.com.datasind.aplicacao.informacao;

import java.util.LinkedList;
import java.util.Map;

import org.apache.log4j.Logger;

public class ServicoInformacao {

	private static final Logger logger = Logger
			.getLogger(ServicoInformacao.class);

	private PoolWorker[] threads;

	private LinkedList<Execution> queue;

	private static ServicoInformacao instance;

	public static synchronized ServicoInformacao getInstance() {
		if (instance == null) {
			instance = new ServicoInformacao();
		}
		return instance;
	}

	public ServicoInformacao() {
		try {
			setupQueue();

		} catch (Exception e) {
			throw new RuntimeException(
					"Problemas configurando o ServicoInformacao", e);
		}
	}

	private void setupQueue() {
		queue = new LinkedList<Execution>();
		threads = new PoolWorker[3];

		for (int i = 0; i < threads.length; i++) {
			threads[i] = new PoolWorker();
			threads[i].start();
		}
	}

	public static void registra(RegistroInformacao r, Map<String, Object> params) {
		getInstance().exec(r, params);
	}

	private void exec(RegistroInformacao r, Map<String, Object> params) {
		synchronized (queue) {
			queue.addLast(new Execution(r, params));
			queue.notify();
		}
	}

	private class PoolWorker extends Thread {
		public void run() {
			Execution r;
			while (true) {
				synchronized (queue) {
					while (queue.isEmpty()) {
						try {
							queue.wait();
						} catch (InterruptedException ignored) {
						}
					}
					r = queue.removeFirst();
				}

				// If we don't catch RuntimeException,
				// the pool could leak thread
				try {
					r.registroInformacao.execute(r.params);

				} catch (Exception e) {
					logger.error(e);
				}
			}
		}
	}

	private class Execution {
		public RegistroInformacao registroInformacao;
		public Map<String, Object> params;

		public Execution(RegistroInformacao registroInformacao, Map<String, Object> params) {
			this.registroInformacao = registroInformacao;
			this.params = params;
		}
	}
}