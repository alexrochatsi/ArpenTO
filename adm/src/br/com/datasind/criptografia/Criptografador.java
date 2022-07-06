package br.com.datasind.criptografia;

/**
 * 
 * @author OsmarJunior
 * @since 17/09/2011
 */
public abstract class Criptografador {
	
	public abstract byte[] criptografar(byte[] bytes) throws FalhaCriptografiaException;
	public abstract byte[] descriptografar(byte[] bytes) throws FalhaCriptografiaException;
	
	
	private AlgoritmoCriptografia algoritmoCriptografia;
	
	public AlgoritmoCriptografia getAlgoritmoCriptografia() {
		if(algoritmoCriptografia == null)
			algoritmoCriptografia = new AlgoritmoCriptografia();
		return algoritmoCriptografia;
	}	

	public void setAlgoritmoCriptografia(AlgoritmoCriptografia algoritmoCriptografia) {
		this.algoritmoCriptografia = algoritmoCriptografia;
	}
	
	
}