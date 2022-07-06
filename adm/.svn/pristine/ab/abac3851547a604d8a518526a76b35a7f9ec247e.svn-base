package br.com.datasind.criptografia;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import br.com.datasind.entidade.Arquivo;



public class AssistenteCriptografiaImpl implements AssistenteCriptografia {

	private Criptografador criptografador;

	public AssistenteCriptografiaImpl(Criptografador criptografador)
			throws FalhaCriptografiaException {
		if (criptografador == null)
			throw new FalhaCriptografiaException("Criptografador Invalido");

		this.criptografador = criptografador;

		getCriptografador() 
				.setAlgoritmoCriptografia(new AlgoritmoCriptografia(
						ParametrosFacesWebCriptografia
								.obterParametro(ParametrosFacesWebCriptografia.ALGORITMO_CRIPTOGRAFIA_CHAVE),
						ParametrosFacesWebCriptografia
								.obterParametro(ParametrosFacesWebCriptografia.ALGORITMO_CRIPTOGRAFIA_CIFRA)));
	}

	private Criptografador getCriptografador() {
		return criptografador;
	}

	public Arquivo criptografar(Arquivo arquivo)
			throws FalhaCriptografiaException {
		try {
			byte[] bytesEntrada = new byte[arquivo.getStream().available()];
			arquivo.getStream().read(bytesEntrada);
			arquivo.setStream(new ByteArrayInputStream(getCriptografador()
					.criptografar(bytesEntrada)));
		} catch (IOException e) {
			throw new FalhaCriptografiaException(e.getMessage());
		}
		return arquivo;
	}

	public byte[] criptografar(byte[] bytes) throws FalhaCriptografiaException {
		return getCriptografador().criptografar(bytes);
	}

	public String criptografar(String texto) throws FalhaCriptografiaException {
		return new String(getCriptografador().criptografar(texto.getBytes()));
	}

	public Arquivo descriptografar(Arquivo arquivo)
			throws FalhaCriptografiaException {
		try {
			byte[] bytesEntrada = new byte[arquivo.getStream().available()];
			arquivo.getStream().read(bytesEntrada);
			arquivo.setStream(new ByteArrayInputStream(getCriptografador()
					.descriptografar(bytesEntrada)));
		} catch (IOException e) {
			throw new FalhaCriptografiaException(e.getMessage());
		}
		return arquivo;
	}

	public byte[] descriptografar(byte[] bytes)
			throws FalhaCriptografiaException {
		return getCriptografador().descriptografar(bytes);
	}

	public String descriptografar(String texto)
			throws FalhaCriptografiaException {
		return new String(getCriptografador().descriptografar(texto.getBytes()));
	}

	/*
	 * private String algoritmoChave; private String algoritmoCifra;
	 * 
	 * public String getAlgoritmoChavePadrao() { if(algoritmoChave == null)
	 * algoritmoChave = AlgoritmoCriptografia.VALOR_PADRAO_ALGORITMO_CHAVE;
	 * return algoritmoChave; }
	 * 
	 * public String getAlgoritmoCifra() { if(algoritmoChave == null)
	 * algoritmoChave = AlgoritmoCriptografia.VALOR_PADRAO_ALGORITMO_CIFRA;
	 * return algoritmoCifra; }
	 * 
	 * public void setAlgoritmoChave(String algoritmoChave) {
	 * this.algoritmoChave = algoritmoChave; }
	 * 
	 * public void setAlgoritmoCifra(String algoritmoCifra) {
	 * this.algoritmoCifra = algoritmoCifra; }
	 * 
	 * public File criptografarArquivo(File f, ParChave parChave) throws
	 * FalhaCriptografiaException{ try { FileInputStream stream = new
	 * FileInputStream(f); byte[] dados = new byte[stream.available()];
	 * stream.read(dados); byte[] dadosEncriptados =
	 * criptografar(dados,parChave);
	 * 
	 * FileOutputStream fileOutputStream = new FileOutputStream(f.getName());
	 * fileOutputStream.write(dadosEncriptados); return f; } catch
	 * (FileNotFoundException e) { throw new
	 * FalhaCriptografiaException(e.getMessage()); } catch (IOException e) {
	 * throw new FalhaCriptografiaException(e.getMessage()); } }
	 * 
	 * public byte[] criptografarTexto(String texto,ParChave parChave) throws
	 * FalhaCriptografiaException{ return
	 * criptografar(texto.getBytes(),parChave); }
	 * 
	 * public File descriptografarArquivo(File f,ParChave parChave) throws
	 * FalhaCriptografiaException{ try { FileInputStream stream = new
	 * FileInputStream(f); byte[] dados = new byte[stream.available()];
	 * stream.read(dados); byte[] dadosDescriptados =
	 * descriptografar(dados,parChave);
	 * 
	 * FileOutputStream fileOutputStream = new FileOutputStream(f.getName());
	 * fileOutputStream.write(dadosDescriptados); return f; } catch
	 * (FileNotFoundException e) { throw new
	 * FalhaCriptografiaException(e.getMessage()); } catch (IOException e) {
	 * throw new FalhaCriptografiaException(e.getMessage()); } catch
	 * (FalhaCriptografiaException e) { throw new
	 * FalhaCriptografiaException(e.getMessage()); } }
	 * 
	 * public String descriptografarTexto(byte[] texto,ParChave parChave) throws
	 * FalhaCriptografiaException{ return new
	 * String(descriptografar(texto,parChave)); }
	 * 
	 * private SecretKey obterChaveSecreta(ParChave parChave) throws
	 * NoSuchAlgorithmException, InvalidKeyException{ KeyAgreement ka =
	 * KeyAgreement.getInstance(getAlgoritmoChave());
	 * ka.init(parChave.getChavePrivada());
	 * ka.doPhase(parChave.getChavePublica(), true); return
	 * ka.generateSecret(getAlgoritmoCifra()); }
	 * 
	 * private byte[] criptografar(byte[] entrada, ParChave parChave) throws
	 * FalhaCriptografiaException { Cipher cipher = null; try { SecretKey key =
	 * obterChaveSecreta(parChave); cipher =
	 * Cipher.getInstance(getAlgoritmoCifra()); cipher.init(Cipher.ENCRYPT_MODE,
	 * key); return cipher.doFinal(entrada); } catch (NoSuchAlgorithmException
	 * e) { throw new FalhaCriptografiaException(e.getMessage()); } catch
	 * (NoSuchPaddingException e) { throw new
	 * FalhaCriptografiaException(e.getMessage()); } catch (InvalidKeyException
	 * e) { throw new FalhaCriptografiaException(e.getMessage()); } catch
	 * (IllegalStateException e) { throw new
	 * FalhaCriptografiaException(e.getMessage()); } catch
	 * (IllegalBlockSizeException e) { throw new
	 * FalhaCriptografiaException(e.getMessage()); } catch (BadPaddingException
	 * e) { throw new FalhaCriptografiaException(e.getMessage()); } }
	 * 
	 * private byte[] descriptografar(byte[] entrada, ParChave parChave) throws
	 * FalhaCriptografiaException { Cipher cipher = null; try { SecretKey key =
	 * obterChaveSecreta(parChave); cipher =
	 * Cipher.getInstance(getAlgoritmoCifra()); cipher.init(Cipher.ENCRYPT_MODE,
	 * key); return cipher.doFinal(entrada); } catch (NoSuchAlgorithmException
	 * e) { throw new FalhaCriptografiaException(e.getMessage()); } catch
	 * (NoSuchPaddingException e) { throw new
	 * FalhaCriptografiaException(e.getMessage()); } catch (InvalidKeyException
	 * e) { throw new FalhaCriptografiaException(e.getMessage()); } catch
	 * (IllegalStateException e) { throw new
	 * FalhaCriptografiaException(e.getMessage()); } catch
	 * (IllegalBlockSizeException e) { throw new
	 * FalhaCriptografiaException(e.getMessage()); } catch (BadPaddingException
	 * e) { throw new FalhaCriptografiaException(e.getMessage()); } }
	 * 
	 * public Arquivo criptografarArquivo(Arquivo arquivo, ParChave parChave)
	 * throws FalhaCriptografiaException { Cipher cipher = null; try { SecretKey
	 * key = obterChaveSecreta(parChave); cipher =
	 * Cipher.getInstance(getAlgoritmoCifra()); cipher.init(Cipher.ENCRYPT_MODE,
	 * key);
	 * 
	 * byte[] bytesEntrada = new byte[arquivo.getStream().available()];
	 * arquivo.getStream().read(bytesEntrada); arquivo.setStream(new
	 * ByteArrayInputStream(cipher.doFinal(bytesEntrada))); return arquivo;
	 *  } catch (NoSuchAlgorithmException e) { throw new
	 * FalhaCriptografiaException(e.getMessage()); } catch
	 * (NoSuchPaddingException e) { throw new
	 * FalhaCriptografiaException(e.getMessage()); } catch (InvalidKeyException
	 * e) { throw new FalhaCriptografiaException(e.getMessage()); } catch
	 * (IllegalStateException e) { throw new
	 * FalhaCriptografiaException(e.getMessage()); } catch
	 * (IllegalBlockSizeException e) { throw new
	 * FalhaCriptografiaException(e.getMessage()); } catch (BadPaddingException
	 * e) { throw new FalhaCriptografiaException(e.getMessage()); } catch
	 * (IOException e) { throw new FalhaCriptografiaException(e.getMessage()); } }
	 * 
	 * public Arquivo descriptografarArquivo(Arquivo arquivo, ParChave parChave)
	 * throws FalhaCriptografiaException { Cipher cipher = null; try { SecretKey
	 * key = obterChaveSecreta(parChave); cipher =
	 * Cipher.getInstance(getAlgoritmoCifra()); cipher.init(Cipher.DECRYPT_MODE,
	 * key);
	 * 
	 * byte[] bytesEntrada = new byte[arquivo.getStream().available()];
	 * arquivo.getStream().read(bytesEntrada); arquivo.setStream(new
	 * ByteArrayInputStream(cipher.doFinal(bytesEntrada))); return arquivo;
	 *  } catch (NoSuchAlgorithmException e) { throw new
	 * FalhaCriptografiaException(e.getMessage()); } catch
	 * (NoSuchPaddingException e) { throw new
	 * FalhaCriptografiaException(e.getMessage()); } catch (InvalidKeyException
	 * e) { throw new FalhaCriptografiaException(e.getMessage()); } catch
	 * (IllegalStateException e) { throw new
	 * FalhaCriptografiaException(e.getMessage()); } catch
	 * (IllegalBlockSizeException e) { throw new
	 * FalhaCriptografiaException(e.getMessage()); } catch (BadPaddingException
	 * e) { throw new FalhaCriptografiaException(e.getMessage()); } catch
	 * (IOException e) { throw new FalhaCriptografiaException(e.getMessage()); } }
	 * 
	 * public PublicKey restaurarChaveAssimetricaPublica(byte[] bytesKey) throws
	 * FalhaCriptografiaException { X509EncodedKeySpec x509KeySpec = new
	 * X509EncodedKeySpec(bytesKey); KeyFactory keyFact; try { keyFact =
	 * KeyFactory.getInstance(this.getAlgoritmoChave()); return
	 * keyFact.generatePublic(x509KeySpec); } catch (NoSuchAlgorithmException e) {
	 * throw new FalhaCriptografiaException(e.getMessage()); } catch
	 * (InvalidKeySpecException e) { throw new
	 * FalhaCriptografiaException(e.getMessage()); }
	 *  }
	 * 
	 * public PrivateKey restaurarChaveAssimetricaPrivada(byte[] bytesKey)
	 * throws FalhaCriptografiaException { X509EncodedKeySpec x509KeySpec = new
	 * X509EncodedKeySpec(bytesKey); KeyFactory keyFact; try { keyFact =
	 * KeyFactory.getInstance(this.getAlgoritmoChave()); return
	 * keyFact.generatePrivate(x509KeySpec); } catch (NoSuchAlgorithmException
	 * e) { throw new FalhaCriptografiaException(e.getMessage()); } catch
	 * (InvalidKeySpecException e) { throw new
	 * FalhaCriptografiaException(e.getMessage()); }
	 *  }
	 */
}
