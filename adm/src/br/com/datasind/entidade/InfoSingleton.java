package br.com.datasind.entidade;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public final class InfoSingleton {

	private static InfoSingleton instancia;

	private static boolean internetOnline = false;
	private static boolean eprocOnline = false;

	private static int clientesDesatualizados = 0; 
	private static int processosDesatualizados = 0;
	private static int mandadosDesatualizados = 0;

	private static Date clientesUltimaConsulta = new Date();
	private static Date processosUltimaConsulta = new Date();
	private static Date mandadosUltimaConsulta = new Date();

	private static Date ultimaConsulta = new Date();

	private static String jobAtual = "";
	private static String jobStatus = "";
	
	private static Map<String, Object> mapPilha = new HashMap<String, Object>(); 
	
	private InfoSingleton(){
		
	}
	
	public static synchronized InfoSingleton getInstance(){
		if(instancia == null){
			instancia = new InfoSingleton();
		}
		return instancia;
	}
	
	public void setInternetOnline(boolean obj){
		internetOnline = obj;
	}

	public boolean isInternetOnline(){
		return internetOnline;
	}

	public void setEprocOnline(boolean obj){
		eprocOnline = obj;
	}

	public boolean isEprocOnline(){
		return eprocOnline;
	}

	public static int getClientesDesatualizados() {
		return clientesDesatualizados;
	}

	public static void setClientesDesatualizados(int clientesDesatualizados) {
		InfoSingleton.clientesDesatualizados = clientesDesatualizados;
	}

	public static int getProcessosDesatualizados() {
		return processosDesatualizados;
	}

	public static void setProcessosDesatualizados(int processosDesatualizados) {
		InfoSingleton.processosDesatualizados = processosDesatualizados;
	}

	public static int getMandadosDesatualizados() {
		return mandadosDesatualizados;
	}

	public static void setMandadosDesatualizados(int mandadosDesatualizados) {
		InfoSingleton.mandadosDesatualizados = mandadosDesatualizados;
	}

	public static String getJobAtual() {
		return jobAtual;
	}

	public static void setJobAtual(String jobAtual) {
		InfoSingleton.jobAtual = jobAtual;
	}

	public static String getJobStatus() {
		return jobStatus;
	}
	
	public static void setJobStatus(String jobStatus) {
		InfoSingleton.jobStatus = jobStatus;
	}

	public static Date getClientesUltimaConsulta() {
		return clientesUltimaConsulta;
	}

	public static void setClientesUltimaConsulta(Date clientesUltimaConsulta) {
		InfoSingleton.clientesUltimaConsulta = clientesUltimaConsulta;
	}

	public static Date getProcessosUltimaConsulta() {
		return processosUltimaConsulta;
	}

	public static void setProcessosUltimaConsulta(Date processosUltimaConsulta) {
		InfoSingleton.processosUltimaConsulta = processosUltimaConsulta;
	}

	public static Date getMandadosUltimaConsulta() {
		return mandadosUltimaConsulta;
	}

	public static void setMandadosUltimaConsulta(Date mandadosUltimaConsulta) {
		InfoSingleton.mandadosUltimaConsulta = mandadosUltimaConsulta;
	}

	public static Date getUltimaConsulta() {
		return ultimaConsulta;
	}
	
	public static void setUltimaConsulta(Date ultimaConsulta) {
		InfoSingleton.ultimaConsulta = ultimaConsulta;
	}
	
	public static Map<String, Object> getMapPilha() {
		return mapPilha;
	}
	
	public static void setMapPilha(Map<String, Object> mapPilha) {
		InfoSingleton.mapPilha = mapPilha;
	}
	
}
