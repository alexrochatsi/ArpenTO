
package br.com.datasind.job;


import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import br.com.datasind.cadastro.CadastroException;

/**
 * 
 * @author OsmarJunior
 * @since 4 de mar de 2016
 */

public class UpdateTabelasTemporariasJOB implements Job{

   @Override
   public void execute(JobExecutionContext context) throws JobExecutionException {

	  ChamaURL chamaURL=new ChamaURL();
	  try {
		 chamaURL.chamaURL("http://localhost:8080/cartorio/test/limpaCertidoes.jsf");
	  } catch (CadastroException e) {
		e.printStackTrace();
	  }
   }

}
