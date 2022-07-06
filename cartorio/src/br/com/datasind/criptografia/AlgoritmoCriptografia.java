package br.com.datasind.criptografia;

public class AlgoritmoCriptografia {

	private String algorimoCriptografiaChave;
	private String algorimoCriptografiaCifra;
	
	
	public AlgoritmoCriptografia(String algorimoCriptografiaChave, String algorimoCriptografiaCifra) {
		super();
		this.algorimoCriptografiaChave = algorimoCriptografiaChave;
		this.algorimoCriptografiaCifra = algorimoCriptografiaCifra;
	}

	public AlgoritmoCriptografia() {
		super();
	}
	
	public String getAlgorimoCriptografiaChave() {
		return algorimoCriptografiaChave;
	}
	
	public String getAlgorimoCriptografiaCifra() {
		return algorimoCriptografiaCifra;
	}
	
	public void setAlgorimoCriptografiaChave(String algorimoCriptografiaChave) {
		this.algorimoCriptografiaChave = algorimoCriptografiaChave;
	}
	
	public void setAlgorimoCriptografiaCifra(String algorimoCriptografiaCifra) {
		this.algorimoCriptografiaCifra = algorimoCriptografiaCifra;
	}
}

