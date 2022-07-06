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
 * @since 4 de mar de 2016
 */

public class UpdateTabelasTemporariasJOB implements Job {

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {

		List<JobExecutionContext> currentJobs = null;
		int countJob = 0;

		try {
			currentJobs = context.getScheduler().getCurrentlyExecutingJobs();

			// for (JobExecutionContext jobCtx : currentJobs) {
			// if
			// (jobCtx.getJobDetail().getKey().getName().equalsIgnoreCase("TabelasTemporarias")
			// &&
			// jobCtx.getJobDetail().getKey().getGroup().equalsIgnoreCase("eproc-to"))
			// {
			// verifica = true;
			// }
			// }

			currentJobs = context.getScheduler().getCurrentlyExecutingJobs();
			for (JobExecutionContext jobCtx : currentJobs) {
				if (jobCtx.getJobDetail().getKey().getName().equalsIgnoreCase("TabelasTemporarias")
						&& jobCtx.getJobDetail().getKey().getGroup().equalsIgnoreCase("eproc-to")) {
					countJob++;
				}

			}

			if (countJob <= 1) {
				ChamaURL chamaURL = new ChamaURL();
				chamaURL.chamaURL("http://localhost:8080/adm/test/atualizaTabTemporaria.jsf");
				// System.out.println("Teste");
			}

		} catch (SchedulerException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (CadastroException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// System.out.println(InfoSingleton.getInstance().getClientesDesatualizados());

	}

}
