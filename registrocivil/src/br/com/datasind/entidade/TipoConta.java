/**
 * 
 */
package br.com.datasind.entidade;

/**
 * @author Rodrigo
 *11:58:30
 */
public enum TipoConta {
   
   CONTA_CORRENTE("Conta Corrente"), 
   POUPANCA("Poupança"),
   DINHEIRO("Dinheiro");
   
   public String tipoConta;
   
   private TipoConta(String valor) {
	  tipoConta = valor;
   }
   
   public String getTipoConta(){
	  return tipoConta;
   }

}
