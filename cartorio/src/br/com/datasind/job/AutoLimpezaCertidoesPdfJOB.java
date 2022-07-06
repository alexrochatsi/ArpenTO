
package br.com.datasind.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import br.com.datasind.cadastro.CadastroException;

/**
 * 
 * @author AlexRocha
 * @since 23 de Nov de 2016
 */

public class AutoLimpezaCertidoesPdfJOB implements Job {

   /**
    * 
    */
   //executa a página que irá limpar todos os pdfs das certidões fora do prazo de 30 dias
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
