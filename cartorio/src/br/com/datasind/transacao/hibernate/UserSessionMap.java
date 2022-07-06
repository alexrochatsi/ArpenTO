package br.com.datasind.transacao.hibernate;

import org.hibernate.Session;

public class UserSessionMap {
	private ThreadLocal<Session> threadLocal = new ThreadLocal<Session>();
	//private Map<String, Session> userSesisonMap = new HashMap<String, Session>();

	public Session get() {
//		ThreadUser threadUser = ThreadUser.getInstance();
//		return userSesisonMap.get(threadUser.getId());
		return threadLocal.get();
	}

	public void set(Session session) {
//		ThreadUser threadUser = ThreadUser.getInstance();
//		userSesisonMap.put(threadUser.getId(), session);
		threadLocal.set(session);
	}
}
