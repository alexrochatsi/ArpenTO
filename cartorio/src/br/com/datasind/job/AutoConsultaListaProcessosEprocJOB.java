package br.com.datasind.job;

import java.util.List;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;

import br.com.datasind.cadastro.CadastroException;

/**
 * 
 * @author OsmarJunior
 * @since 26 de abr de 2016
 */

public class AutoConsultaListaProcessosEprocJOB implements Job {

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {

		// se o sistema estiver em manutenção não executa os jobs

		int countJobDetalhe = 0;
		int countJobListaProcessos = 0;
		int countJobMandadosBusca = 0;

		List<JobExecutionContext> currentJobs = null;
		try {
			currentJobs = context.getScheduler().getCurrentlyExecutingJobs();
			for (JobExecutionContext jobCtx : currentJobs) {
				if (jobCtx.getJobDetail().getKey().getName().equalsIgnoreCase("AutoConsultaDetalheProcessoEproc")
						&& jobCtx.getJobDetail().getKey().getGroup().equalsIgnoreCase("eproc-to")) {
					countJobDetalhe++;
				} else if (jobCtx.getJobDetail().getKey().getName().equalsIgnoreCase("AutoConsultaListaProcessosEproc")
						&& jobCtx.getJobDetail().getKey().getGroup().equalsIgnoreCase("eproc-to")) {
					countJobListaProcessos++;
				} else if (jobCtx.getJobDetail().getKey().getName().equalsIgnoreCase("AutoConsultaMandadosBusca")
						&& jobCtx.getJobDetail().getKey().getGroup().equalsIgnoreCase("eproc-to")) {
					countJobMandadosBusca++;
				}

			}
		} catch (SchedulerException e) {
			e.printStackTrace();
		}

		if (countJobListaProcessos <= 1 && countJobDetalhe == 0 && countJobMandadosBusca == 0) {// so
																								// executa
																								// se
			ChamaURL chamaURL = new ChamaURL();
			// System.out.println(InfoSingleton.getInstance().getClientesDesatualizados());
			try {
				chamaURL.chamaURL("http://localhost:8080/cartorio/test/consultaDetalheProcAutentic.jsf");

			} catch (CadastroException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}
}
