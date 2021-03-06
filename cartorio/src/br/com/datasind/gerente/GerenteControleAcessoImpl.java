
package br.com.datasind.gerente;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.log4j.Logger;

import br.com.datasind.aplicacao.ApplicationException;
import br.com.datasind.cadastro.CadastroBanco;
import br.com.datasind.cadastro.CadastroCartorio;
import br.com.datasind.cadastro.CadastroCertidaoPedido;
import br.com.datasind.cadastro.CadastroCertidaoUpload;
import br.com.datasind.cadastro.CadastroCidade;
import br.com.datasind.cadastro.CadastroContaBancaria;
import br.com.datasind.cadastro.CadastroControleAcesso;
import br.com.datasind.cadastro.CadastroCorreios;
import br.com.datasind.cadastro.CadastroCustoPedido;
import br.com.datasind.cadastro.CadastroException;
import br.com.datasind.cadastro.CadastroIbgeUF;
import br.com.datasind.cadastro.CadastroLocal;
import br.com.datasind.cadastro.CadastroModulo;
import br.com.datasind.cadastro.CadastroOcupacao;
import br.com.datasind.cadastro.CadastroOrgaoEmissor;
import br.com.datasind.cadastro.CadastroPais;
import br.com.datasind.cadastro.CadastroPerfil;
import br.com.datasind.cadastro.CadastroPermissaoAcesso;
import br.com.datasind.cadastro.CadastroRegistroNascimento;
import br.com.datasind.cadastro.CadastroTipoLivro;
import br.com.datasind.cadastro.CadastroUF;
import br.com.datasind.cadastro.CadastroUsuarioCartorio;
import br.com.datasind.cadastro.CadastroUsuarioCartorioModuloPerfil;
import br.com.datasind.cadastro.ContextoCadastroBasico;
import br.com.datasind.cadastro.FinderException;
import br.com.datasind.conversao.util.Codificador;
import br.com.datasind.entidade.Banco;
import br.com.datasind.entidade.Cartorio;
import br.com.datasind.entidade.CertidaoPedido;
import br.com.datasind.entidade.CertidaoUpload;
import br.com.datasind.entidade.Cidade;
import br.com.datasind.entidade.ContaBancaria;
import br.com.datasind.entidade.Correios;
import br.com.datasind.entidade.CustoPedido;
import br.com.datasind.entidade.IbgeUF;
import br.com.datasind.entidade.Local;
import br.com.datasind.entidade.Modulo;
import br.com.datasind.entidade.Ocupacao;
import br.com.datasind.entidade.OrgaoEmissor;
import br.com.datasind.entidade.Pais;
import br.com.datasind.entidade.Perfil;
import br.com.datasind.entidade.PermissaoAcesso;
import br.com.datasind.entidade.RegistroNascimento;
import br.com.datasind.entidade.TipoLivro;
import br.com.datasind.entidade.UF;
import br.com.datasind.entidade.UsuarioCartorioModuloPerfil;
import br.com.datasind.entidade.UsuarioCartorio;
import br.com.datasind.validacao.ValidacaoException;

public class GerenteControleAcessoImpl extends GerentePadrao implements GerenteControleAcesso{

   private static final Logger LOGGER=Logger.getLogger(GerenteControleAcessoImpl.class);

   @SuppressWarnings("unused")
   private UsuarioCartorio usuarioCartorioSessao;

   public GerenteControleAcessoImpl() {
	  super();
   }

   @Override
   public UsuarioCartorio verificarUsuarioCartorio(UsuarioCartorio usuarioCartorio) throws ApplicationException, UsuarioNaoEncontradoException, ValidacaoException, SenhaInvalidaException,
	  UsuarioInativoException {

	  if(usuarioCartorio.getSenha() == null || usuarioCartorio.getNomeLogin() == null) {
		 throw new ValidacaoException("Ambos os campos devem estar preenchidos.");
	  }

	  CadastroUsuarioCartorio cadastroUsuarioCartorio=new CadastroUsuarioCartorio();
	  cadastroUsuarioCartorio.setContexto(new ContextoCadastroBasico(getContexto().getFabricaGerente()));
	  try {
		 // PesquisaEmail no BD os dados do usuario informados no login
		 UsuarioCartorio encontrado=cadastroUsuarioCartorio.findByLogin(usuarioCartorio.getNomeLogin());
		 // Checa se usuario esta inativo no cadastro
		 if( !encontrado.getFlagAtivo()) {
			throw new UsuarioInativoException();
		 }

		 // seta senha MD5 em Hexa
		 usuarioCartorio.setSenha(Codificador.codificaSenhaMD5(usuarioCartorio.getSenha()));

		 LOGGER.debug("Hash da senha do usuario encontrado :" + encontrado.getSenha());

		 LOGGER.debug("Hash da senha entrada pelo usuario :" + usuarioCartorio.getSenha());

		 if(usuarioCartorio.getSenha() == null || !usuarioCartorio.getSenha().equals(encontrado.getSenha())) {
			System.out.println("A SENHA ?? INVALIDA!");
			throw new SenhaInvalidaException();
		 }

		 // seta data do ultimo login
		 try {
			Date date=new Date();
			encontrado.setDataUltimoLogin(date);
			atualizarEntidadeComit(encontrado);
		 } catch (ApplicationException e) {
			throw new ApplicationException("Nao foi possivel atualizar data do ultimo login de usuario.");
		 }

		 setUsuarioCartorioSessao(encontrado);

		 getContexto().getFabricaGerente().getGerenteTransacao().setUserId(String.valueOf(encontrado.getId()));

		 return encontrado;

	  } catch (FinderException e) {
		 throw new UsuarioNaoEncontradoException();

	  } catch (CadastroException e) {
		 throw new ApplicationException(e);
	  }

   }

   @Override
   public IbgeUF obterIbgeUFPorId(Integer id) throws ApplicationException {

	  CadastroIbgeUF cadastroIbgeUF=new CadastroIbgeUF();
	  cadastroIbgeUF.setContexto(new ContextoCadastroBasico(getContexto().getFabricaGerente()));

	  try {
		 return cadastroIbgeUF.findById(id);
	  } catch (CadastroException e) {
		 throw new ApplicationException(e);
	  }
   }

   @Override
   public Pais obterPaisPorId(Integer id) throws ApplicationException {

	  CadastroPais cadastroPais=new CadastroPais();
	  cadastroPais.setContexto(new ContextoCadastroBasico(getContexto().getFabricaGerente()));

	  try {
		 return cadastroPais.findById(id);
	  } catch (CadastroException e) {
		 throw new ApplicationException(e);
	  }
   }

   @Override
   public OrgaoEmissor obterOrgaoEmissorPorId(Integer id) throws ApplicationException {

	  CadastroOrgaoEmissor cadastroOrgaoEmissor=new CadastroOrgaoEmissor();
	  cadastroOrgaoEmissor.setContexto(new ContextoCadastroBasico(getContexto().getFabricaGerente()));

	  try {
		 return cadastroOrgaoEmissor.findByIntegerId(id);
	  } catch (CadastroException e) {
		 throw new ApplicationException(e);
	  }
   }

   @Override
   public Ocupacao obterOcupacaoPorId(Integer id) throws ApplicationException {

	  CadastroOcupacao cadastroOcupacao=new CadastroOcupacao();
	  cadastroOcupacao.setContexto(new ContextoCadastroBasico(getContexto().getFabricaGerente()));

	  try {
		 return cadastroOcupacao.findByIntegerId(id);
	  } catch (CadastroException e) {
		 throw new ApplicationException(e);
	  }
   }

   @Override
   public UsuarioCartorio obterUsuarioCartorioPorId(UsuarioCartorio usuarioCartorio) throws ApplicationException, UsuarioNaoEncontradoException, ValidacaoException {

	  CadastroUsuarioCartorio cadastroUsuarioCartorio=new CadastroUsuarioCartorio();
	  cadastroUsuarioCartorio.setContexto(new ContextoCadastroBasico(getContexto().getFabricaGerente()));

	  try {
		 return cadastroUsuarioCartorio.findById(usuarioCartorio);
	  } catch (FinderException e) {
		 throw new UsuarioNaoEncontradoException();
	  } catch (CadastroException e) {
		 throw new ApplicationException(e);
	  }
   }
   
   @Override
   public UsuarioCartorio obterUsuarioCartorioPorCpfCert(String cpfCert) throws ApplicationException, ValidacaoException {

	  CadastroUsuarioCartorio cadastroUsuarioCartorio=new CadastroUsuarioCartorio();
	  cadastroUsuarioCartorio.setContexto(new ContextoCadastroBasico(getContexto().getFabricaGerente()));

	  try {
		 return cadastroUsuarioCartorio.findByCpfCert(cpfCert);
	  } catch (CadastroException e) {
		 throw new ApplicationException(e);
	  }
   }

   @Override
   public UsuarioCartorio obterUsuarioCartorioPorUID(String uid) throws ApplicationException, UsuarioNaoEncontradoException, ValidacaoException {

	  CadastroUsuarioCartorio cadastroUsuarioCartorio=new CadastroUsuarioCartorio();
	  cadastroUsuarioCartorio.setContexto(new ContextoCadastroBasico(getContexto().getFabricaGerente()));

	  try {
		 return cadastroUsuarioCartorio.findByUID(uid);
	  } catch (FinderException e) {
		 throw new UsuarioNaoEncontradoException();
	  } catch (CadastroException e) {
		 throw new ApplicationException(e);
	  }
   }

   public List<PermissaoAcesso> obterPermissoesAcessoModulo(Perfil perfil) throws ApplicationException {

	  CadastroPermissaoAcesso cadastroPermissaoAcesso=new CadastroPermissaoAcesso();
	  cadastroPermissaoAcesso.setContexto(new ContextoCadastroBasico(getContexto().getFabricaGerente()));
	  try {
		 return cadastroPermissaoAcesso.findPermissoesAcessoByPerfil(perfil);

	  } catch (CadastroException e) {
		 throw new ApplicationException(e);
	  }
   }

   public Modulo obterModuloPeloNomeInterno(String nomeInterno) throws ApplicationException {
	  CadastroModulo cadastro=new CadastroModulo();
	  cadastro.setContexto(new ContextoCadastroBasico(getContexto().getFabricaGerente()));

	  return cadastro.findModuloByNomeInterno(nomeInterno);
   }

   @Override
   public Modulo obterModuloPorId(Modulo modulo) throws ApplicationException, UsuarioNaoEncontradoException, ValidacaoException {

	  CadastroModulo cadastroModulo=new CadastroModulo();
	  cadastroModulo.setContexto(new ContextoCadastroBasico(getContexto().getFabricaGerente()));

	  try {
		 return cadastroModulo.findById(modulo);
	  } catch (FinderException e) {
		 throw new UsuarioNaoEncontradoException();
	  } catch (CadastroException e) {
		 throw new ApplicationException(e);
	  }
   }

   @Override
   public Modulo obterModuloPorId(Integer id) throws ApplicationException {

	  CadastroModulo cadastroModulo=new CadastroModulo();
	  cadastroModulo.setContexto(new ContextoCadastroBasico(getContexto().getFabricaGerente()));

	  try {
		 return cadastroModulo.findById(id);
	  } catch (CadastroException e) {
		 throw new ApplicationException(e);
	  }
   }

   public PermissaoAcesso obterPermissoesAcessoPorUsuarioEModulo(UsuarioCartorio usuarioCartorio, Modulo modulo) throws ApplicationException {
	  CadastroControleAcesso cadastroControleAcesso=new CadastroControleAcesso();
	  cadastroControleAcesso.setContexto(new ContextoCadastroBasico(getContexto().getFabricaGerente()));
	  try {
		 return cadastroControleAcesso.selecionarPermissoesAcessoPorUsuarioEModulo(usuarioCartorio , modulo);

	  } catch (CadastroException e) {
		 throw new ApplicationException(e);
	  }
   }

   public UsuarioCartorio getUsuarioCartorioSessao() {
	  return UsuarioCartorioThread.getInstance().get();
   }

   private void setUsuarioCartorioSessao(UsuarioCartorio usuarioCartorioSessao) {
	  UsuarioCartorioThread.getInstance().set(usuarioCartorioSessao);
   }

   public List<Modulo> obterListaModulos() throws ApplicationException {
	  CadastroModulo cadastro=new CadastroModulo();
	  cadastro.setContexto(new ContextoCadastroBasico(getContexto().getFabricaGerente()));
	  try {
		 return cadastro.getListaModulos();

	  } catch (CadastroException e) {
		 throw new ApplicationException(e);
	  }
   }

   @SuppressWarnings("unchecked")
   @Override
   public List<Perfil> obterListaPerfisPorPerfilFiltro(Perfil perfilFiltro) throws ApplicationException {
	  CadastroPerfil cadastro=new CadastroPerfil();
	  cadastro.setContexto(new ContextoCadastroBasico(getContexto().getFabricaGerente()));
	  try {
		 if(perfilFiltro.getModulo() != null) {
			return cadastro.findByModulo(perfilFiltro);
		 }
		 return cadastro.findByQuery(perfilFiltro);
	  } catch (CadastroException e) {
		 throw new ApplicationException(e);
	  }
   }

   @Override
   public List<Modulo> obterModulos() throws ApplicationException {
	  CadastroModulo cadastroModulo=new CadastroModulo();
	  cadastroModulo.setContexto(new ContextoCadastroBasico(getContexto().getFabricaGerente()));
	  try {
		 return cadastroModulo.getListaModulos();
	  } catch (CadastroException e) {
		 throw new ApplicationException();
	  }
   }

   @Override
   public UF obterUFPorId(UF uf) throws ApplicationException, UsuarioNaoEncontradoException, ValidacaoException {

	  CadastroUF cadastroUF=new CadastroUF();
	  cadastroUF.setContexto(new ContextoCadastroBasico(getContexto().getFabricaGerente()));

	  try {
		 return cadastroUF.findById(uf);
	  } catch (FinderException e) {
		 throw new UsuarioNaoEncontradoException();
	  } catch (CadastroException e) {
		 throw new ApplicationException(e);
	  }
   }

   @Override
   public UF obterUFPorId(Integer id) throws ApplicationException {

	  CadastroUF cadastroUF=new CadastroUF();
	  cadastroUF.setContexto(new ContextoCadastroBasico(getContexto().getFabricaGerente()));

	  try {
		 return cadastroUF.findById(id);
	  } catch (CadastroException e) {
		 throw new ApplicationException(e);
	  }
   }

   @Override
   public Banco obterBancoPorId(Integer id) throws ApplicationException {

	  CadastroBanco cadastroBanco=new CadastroBanco();
	  cadastroBanco.setContexto(new ContextoCadastroBasico(getContexto().getFabricaGerente()));

	  try {
		 return cadastroBanco.findById(id);
	  } catch (CadastroException e) {
		 throw new ApplicationException(e);
	  }
   }

   @Override
   public Local obterLocalPorId(Integer id) throws ApplicationException {

	  CadastroLocal cadastroLocal=new CadastroLocal();
	  cadastroLocal.setContexto(new ContextoCadastroBasico(getContexto().getFabricaGerente()));

	  try {
		 return cadastroLocal.findById(id);
	  } catch (CadastroException e) {
		 throw new ApplicationException(e);
	  }
   }

   @Override
   public CustoPedido obterCustoPedidoPorId(Integer id) throws ApplicationException {

	  CadastroCustoPedido cadastroCustoPedido=new CadastroCustoPedido();
	  cadastroCustoPedido.setContexto(new ContextoCadastroBasico(getContexto().getFabricaGerente()));

	  try {
		 return cadastroCustoPedido.findByIntegerId(id);
	  } catch (CadastroException e) {
		 throw new ApplicationException(e);
	  }
   }

   @Override
   public List<Cartorio> obterCartorios() throws ApplicationException {
	  CadastroCartorio cadastroCartorio=new CadastroCartorio();
	  cadastroCartorio.setContexto(new ContextoCadastroBasico(getContexto().getFabricaGerente()));
	  try {
		 return cadastroCartorio.findByNomeMunicipio();
	  } catch (CadastroException e) {
		 throw new ApplicationException();
	  }
   }
   
   @Override
   public List<CertidaoUpload> obterListaCertidoesUpload() throws ApplicationException {
	  CadastroCertidaoUpload cadastroCertidaoUpload=new CadastroCertidaoUpload();
	  cadastroCertidaoUpload.setContexto(new ContextoCadastroBasico(getContexto().getFabricaGerente()));
	  try {
		 return cadastroCertidaoUpload.findAll();
	  } catch (CadastroException e) {
		 throw new ApplicationException();
	  }
   }
   
   @Override
   public List<CertidaoUpload> obterListaCertidoesUploadVencidas() throws ApplicationException {
	  CadastroCertidaoUpload cadastroCertidaoUpload=new CadastroCertidaoUpload();
	  cadastroCertidaoUpload.setContexto(new ContextoCadastroBasico(getContexto().getFabricaGerente()));
	  try {
		 return cadastroCertidaoUpload.listaCertidoesVencidas();
	  } catch (CadastroException e) {
		 throw new ApplicationException();
	  }
   }

   @Override
   public List<OrgaoEmissor> obterOrgaoEmissores() throws ApplicationException {
	  CadastroOrgaoEmissor cadastroOrgaoEmissor=new CadastroOrgaoEmissor();
	  cadastroOrgaoEmissor.setContexto(new ContextoCadastroBasico(getContexto().getFabricaGerente()));
	  try {
		 return cadastroOrgaoEmissor.findAll();
	  } catch (CadastroException e) {
		 throw new ApplicationException();
	  }
   }

   @Override
   public List<Ocupacao> obterOcupacoes() throws ApplicationException {
	  CadastroOcupacao cadastroOcuapacao=new CadastroOcupacao();
	  cadastroOcuapacao.setContexto(new ContextoCadastroBasico(getContexto().getFabricaGerente()));

	  try {
		 return cadastroOcuapacao.findAll();
	  } catch (CadastroException e) {
		 throw new ApplicationException();
	  }
   }

   @Override
   public List<Cartorio> obterCartoriosPorMunicipio(String s) throws ApplicationException {
	  CadastroCartorio cadastroCartorio=new CadastroCartorio();
	  cadastroCartorio.setContexto(new ContextoCadastroBasico(getContexto().getFabricaGerente()));
	  try {
		 return cadastroCartorio.listaCartorios(s);
	  } catch (CadastroException e) {
		 throw new ApplicationException();
	  }
   }

   @Override
   public Cartorio obterCartorioPorId(Integer id) throws ApplicationException {

	  CadastroCartorio cadastroCartorio=new CadastroCartorio();
	  cadastroCartorio.setContexto(new ContextoCadastroBasico(getContexto().getFabricaGerente()));

	  try {
		 return cadastroCartorio.findByIntegerId(id);
	  } catch (CadastroException e) {
		 throw new ApplicationException(e);
	  }
   }

   @Override
   public Cartorio obterCartorioPorUsuarioCartorio(UsuarioCartorio usuarioCartorio) throws ApplicationException {

	  CadastroCartorio cadastroCartorio=new CadastroCartorio();
	  cadastroCartorio.setContexto(new ContextoCadastroBasico(getContexto().getFabricaGerente()));

	  try {
		 return cadastroCartorio.findByUsuarioCatorioId(usuarioCartorio);
	  } catch (CadastroException e) {
		 throw new ApplicationException(e);
	  }
   }

   @Override
   public Correios obterCorreiosPorId(Integer id) throws ApplicationException {

	  CadastroCorreios cadastroCorreios=new CadastroCorreios();
	  cadastroCorreios.setContexto(new ContextoCadastroBasico(getContexto().getFabricaGerente()));

	  try {
		 return cadastroCorreios.findByIntegerId(id);
	  } catch (CadastroException e) {
		 throw new ApplicationException(e);
	  }
   }

   @Override
   public Cidade obterCidadePorId(Cidade cidade) throws ApplicationException, UsuarioNaoEncontradoException, ValidacaoException {

	  CadastroCidade cadastroCidade=new CadastroCidade();
	  cadastroCidade.setContexto(new ContextoCadastroBasico(getContexto().getFabricaGerente()));

	  try {
		 return cadastroCidade.findById(cidade);
	  } catch (FinderException e) {
		 throw new UsuarioNaoEncontradoException();
	  } catch (CadastroException e) {
		 throw new ApplicationException(e);
	  }
   }

   @Override
   public Cidade obterCidadePorId(Integer id) throws ApplicationException {

	  CadastroCidade cadastroCidade=new CadastroCidade();
	  cadastroCidade.setContexto(new ContextoCadastroBasico(getContexto().getFabricaGerente()));

	  try {
		 return cadastroCidade.findById(id);
	  } catch (CadastroException e) {
		 throw new ApplicationException(e);
	  }
   }

   @Override
   public PermissaoAcesso obterPermissaoAcessoPorId(PermissaoAcesso permissaoAcesso) throws ApplicationException, ValidacaoException {
	  CadastroPermissaoAcesso cadastroPermissaoAcesso=new CadastroPermissaoAcesso();
	  cadastroPermissaoAcesso.setContexto(new ContextoCadastroBasico(getContexto().getFabricaGerente()));

	  try {
		 return cadastroPermissaoAcesso.findById(permissaoAcesso);
	  } catch (CadastroException e) {
		 throw new ApplicationException(e);
	  }
   }

   @Override
   public List<UsuarioCartorioModuloPerfil> obterListaPerfilPorUsuario(UsuarioCartorio usuarioCartorio) throws ApplicationException {

	  CadastroUsuarioCartorioModuloPerfil usuarioCartorioModuloPerfil=new CadastroUsuarioCartorioModuloPerfil();
	  usuarioCartorioModuloPerfil.setContexto(new ContextoCadastroBasico(getContexto().getFabricaGerente()));

	  try {
		 return usuarioCartorioModuloPerfil.obterListaUsuarioCartorioModuloPerfilPorUsuario(usuarioCartorio);
	  } catch (CadastroException e) {
		 throw new ApplicationException(e);
	  }
   }

   @Override
   public List<CertidaoPedido> obterListaCertidaoPedidos() throws ApplicationException {
	  CadastroCertidaoPedido cadastroCertidaoPedido=new CadastroCertidaoPedido();
	  cadastroCertidaoPedido.setContexto(new ContextoCadastroBasico(getContexto().getFabricaGerente()));

	  try {
		 return cadastroCertidaoPedido.findAll();
	  } catch (CadastroException e) {
		 throw new ApplicationException(e);
	  }
   }

   @Override
   public List<CertidaoPedido> obterListaPedidosPorCartorio(Cartorio cartorio) throws ApplicationException {
	  CadastroCertidaoPedido cadastroCertidaoPedido=new CadastroCertidaoPedido();
	  cadastroCertidaoPedido.setContexto(new ContextoCadastroBasico(getContexto().getFabricaGerente()));

	  try {
		 return cadastroCertidaoPedido.findByCartorioIdParcial(cartorio);
	  } catch (CadastroException e) {
		 throw new ApplicationException(e);
	  }
   }
   
   @Override
   public List<CertidaoPedido> obterListaPedidosPorCartorioFiltrado(Cartorio cartorio) throws ApplicationException {
	  CadastroCertidaoPedido cadastroCertidaoPedido=new CadastroCertidaoPedido();
	  cadastroCertidaoPedido.setContexto(new ContextoCadastroBasico(getContexto().getFabricaGerente()));

	  try {
		 return cadastroCertidaoPedido.findByCartorioIdParcialFiltrado(cartorio);
	  } catch (CadastroException e) {
		 throw new ApplicationException(e);
	  }
   }
   
   @Override
   public List<CertidaoPedido> obterListaPedidosPorId(Integer idPedido) throws ApplicationException {
	  CadastroCertidaoPedido cadastroCertidaoPedido=new CadastroCertidaoPedido();
	  cadastroCertidaoPedido.setContexto(new ContextoCadastroBasico(getContexto().getFabricaGerente()));

	  try {
		 return cadastroCertidaoPedido.findByIntegerIdParcial(idPedido);
	  } catch (CadastroException e) {
		 throw new ApplicationException(e);
	  }
   }
   
   @Override
   public List<CertidaoPedido> obterListaPedidosPorIdComplete(Integer idPedido) throws ApplicationException {
	  CadastroCertidaoPedido cadastroCertidaoPedido=new CadastroCertidaoPedido();
	  cadastroCertidaoPedido.setContexto(new ContextoCadastroBasico(getContexto().getFabricaGerente()));

	  try {
		 return cadastroCertidaoPedido.findByIntegerIdParcialComplete(idPedido);
	  } catch (CadastroException e) {
		 throw new ApplicationException(e);
	  }
   }
   
   @Override
   public List<CertidaoPedido> obterListaPedidosByDate(Cartorio cartorio, Date data1, Date data2) throws ApplicationException, FinderException {
	  CadastroCertidaoPedido cadastroCertidaoPedido=new CadastroCertidaoPedido();
	  cadastroCertidaoPedido.setContexto(new ContextoCadastroBasico(getContexto().getFabricaGerente()));

	  try {
		 return cadastroCertidaoPedido.findByCartorioIdParcialDate(cartorio, data1, data2);
	  } catch (CadastroException e) {
		 throw new ApplicationException(e);
	  }
   }

   @Override
   public CertidaoPedido obterCertidaoPedidoPorId(CertidaoPedido certidao) throws ApplicationException, UsuarioNaoEncontradoException, ValidacaoException {

	  CadastroCertidaoPedido cadastroCertidaoPedido=new CadastroCertidaoPedido();
	  cadastroCertidaoPedido.setContexto(new ContextoCadastroBasico(getContexto().getFabricaGerente()));

	  try {
		 return cadastroCertidaoPedido.findById(certidao);
	  } catch (FinderException e) {
		 throw new UsuarioNaoEncontradoException();
	  } catch (CadastroException e) {
		 throw new ApplicationException(e);
	  }
   }
   
   @Override
   public CertidaoPedido obterCertidaoPedidoPorId(Integer certidao) throws ApplicationException, UsuarioNaoEncontradoException, ValidacaoException {

	  CadastroCertidaoPedido cadastroCertidaoPedido=new CadastroCertidaoPedido();
	  cadastroCertidaoPedido.setContexto(new ContextoCadastroBasico(getContexto().getFabricaGerente()));

	  try {
		 return cadastroCertidaoPedido.findById(certidao);
	  } catch (FinderException e) {
		 throw new UsuarioNaoEncontradoException();
	  } catch (CadastroException e) {
		 throw new ApplicationException(e);
	  }
   }
   
   @Override
   public CertidaoPedido obterCertidaoPedidoPorIdUpload(Integer idCertidaoUpload) throws ApplicationException, UsuarioNaoEncontradoException, ValidacaoException {

	  CadastroCertidaoPedido cadastroCertidaoPedido=new CadastroCertidaoPedido();
	  cadastroCertidaoPedido.setContexto(new ContextoCadastroBasico(getContexto().getFabricaGerente()));

	  try {
		 return cadastroCertidaoPedido.findByIdUpload(idCertidaoUpload);
	  } catch (FinderException e) {
		 throw new UsuarioNaoEncontradoException();
	  } catch (CadastroException e) {
		 throw new ApplicationException(e);
	  }
   }
   
   @Override
   public List<CertidaoPedido> obterListaPedidos(Cartorio cartorio, Date data1, Date data2) throws ApplicationException, FinderException {
	  CadastroCertidaoPedido cadastroCertidaoPedido=new CadastroCertidaoPedido();
	  cadastroCertidaoPedido.setContexto(new ContextoCadastroBasico(getContexto().getFabricaGerente()));

	  try {
		 return cadastroCertidaoPedido.findByCartorioIdParcialDate(cartorio , data1 , data2);
	  } catch (CadastroException e) {
		 throw new FinderException(e);
	  }
   }

   @Override
   public List<CertidaoPedido> obterListaPedidosBoletoGerados(Cartorio cartorio, Date data1, Date data2) throws ApplicationException, FinderException {
	  CadastroCertidaoPedido cadastroCertidaoPedido=new CadastroCertidaoPedido();
	  cadastroCertidaoPedido.setContexto(new ContextoCadastroBasico(getContexto().getFabricaGerente()));

	  try {
		 return cadastroCertidaoPedido.findPedidosBoletoByCartorioId(cartorio , data1 , data2);
	  } catch (CadastroException e) {
		 throw new FinderException(e);
	  }
   }

   @Override
   public List<CertidaoPedido> obterListaPedidosBoletoGeradosAll(Cartorio cartorio) throws ApplicationException, FinderException {
	  CadastroCertidaoPedido cadastroCertidaoPedido=new CadastroCertidaoPedido();
	  cadastroCertidaoPedido.setContexto(new ContextoCadastroBasico(getContexto().getFabricaGerente()));

	  try {
		 return cadastroCertidaoPedido.findPedidosBoletoByCartorioIdAll(cartorio);
	  } catch (CadastroException e) {
		 e.printStackTrace();
		 throw new FinderException(e);
	  }
   }

   @Override
   public BigDecimal obterTotalCustoPedidoMes(Cartorio cartorio, Calendar data) throws ApplicationException {
	  CadastroCertidaoPedido cadastroCertidaoPedido=new CadastroCertidaoPedido();
	  cadastroCertidaoPedido.setContexto(new ContextoCadastroBasico(getContexto().getFabricaGerente()));

	  try {
		 return cadastroCertidaoPedido.findPedidosByCartorioData(cartorio , data);
	  } catch (CadastroException e) {
		 throw new ApplicationException(e);
	  }
   }

   @Override
   public List<Correios> obterListaPrecosCorrreios() throws ApplicationException {
	  CadastroCorreios cadastroCorreios=new CadastroCorreios();
	  cadastroCorreios.setContexto(new ContextoCadastroBasico(getContexto().getFabricaGerente()));

	  try {
		 return cadastroCorreios.findAll();
	  } catch (CadastroException e) {
		 throw new ApplicationException(e);
	  }
   }

   @Override
   public List<TipoLivro> obterListaTiposLivro() throws ApplicationException {
	  CadastroTipoLivro cadastroTipoLivro=new CadastroTipoLivro();
	  cadastroTipoLivro.setContexto(new ContextoCadastroBasico(getContexto().getFabricaGerente()));

	  try {
		 return cadastroTipoLivro.findAll();
	  } catch (CadastroException e) {
		 throw new ApplicationException(e);
	  }
   }

   @Override
   public List<CustoPedido> obterCustosPedido() throws ApplicationException {
	  CadastroCustoPedido cadastroCustoPedido=new CadastroCustoPedido();
	  cadastroCustoPedido.setContexto(new ContextoCadastroBasico(getContexto().getFabricaGerente()));

	  try {
		 return cadastroCustoPedido.findAll();
	  } catch (CadastroException e) {
		 throw new ApplicationException(e);
	  }
   }

   @Override
   public List<Banco> obterBancos() throws ApplicationException {
	  CadastroBanco cadastroBanco=new CadastroBanco();
	  cadastroBanco.setContexto(new ContextoCadastroBasico(getContexto().getFabricaGerente()));

	  try {
		 return cadastroBanco.listaTodosBancos();
	  } catch (CadastroException e) {
		 throw new ApplicationException(e);
	  }
   }

   @Override
   public List<ContaBancaria> obterContasBancariasCartorio(Cartorio cartorio) throws FinderException {
	  CadastroContaBancaria cadastroContaBancaria=new CadastroContaBancaria();
	  cadastroContaBancaria.setContexto(new ContextoCadastroBasico(getContexto().getFabricaGerente()));

	  try {
		 return cadastroContaBancaria.findByCartorioId(cartorio);
	  } catch (CadastroException e) {
		 throw new FinderException(e);
	  }
   }
   
   @Override
   public ContaBancaria obterContaBancariaByCartorioAtiva(Cartorio cartorio) throws FinderException {
	  CadastroContaBancaria cadastroContaBancaria=new CadastroContaBancaria();
	  cadastroContaBancaria.setContexto(new ContextoCadastroBasico(getContexto().getFabricaGerente()));

	  try {
		 return cadastroContaBancaria.findByContaAtivaId(cartorio);
	  } catch (CadastroException e) {
		 throw new FinderException(e);
	  }
   }

   @Override
   public List<UsuarioCartorio> obterListaUsuariosCartorio() throws ApplicationException {
	  CadastroUsuarioCartorio cadastroUsuarioCartorio=new CadastroUsuarioCartorio();
	  cadastroUsuarioCartorio.setContexto(new ContextoCadastroBasico(getContexto().getFabricaGerente()));

	  try {
		 return cadastroUsuarioCartorio.findAllUsuarioCartorio();
	  } catch (CadastroException e) {
		 throw new ApplicationException(e);
	  }
   }

   @Override
   public List<Perfil> obterListaPerfilPorModulo(Modulo modulo) throws ApplicationException {
	  CadastroPerfil cadastro=new CadastroPerfil();
	  cadastro.setContexto(new ContextoCadastroBasico(getContexto().getFabricaGerente()));
	  try {
		 return cadastro.findPerfisByModulo(modulo);
	  } catch (CadastroException e) {
		 throw new ApplicationException();
	  }
   }

   @Override
   public UsuarioCartorioModuloPerfil obterUsuarioCartorioModuloPerfilPorIdUsuarioIdPerfil(Integer idUsuarioCartorio, Integer idPerfil) throws ApplicationException {
	  CadastroUsuarioCartorioModuloPerfil cadastro=new CadastroUsuarioCartorioModuloPerfil();
	  cadastro.setContexto(new ContextoCadastroBasico(getContexto().getFabricaGerente()));

	  try {
		 return cadastro.findByUsuarioCartorioPerfil(idUsuarioCartorio , idPerfil);
	  } catch (CadastroException e) {
		 throw new ApplicationException();
	  }
   }

   @Override
   public UsuarioCartorioModuloPerfil obterUsuarioCartorioModuloPerfilPorId(UsuarioCartorioModuloPerfil usuarioCartorioModuloPerfil) throws ApplicationException {

	  CadastroUsuarioCartorioModuloPerfil cadastro=new CadastroUsuarioCartorioModuloPerfil();
	  cadastro.setContexto(new ContextoCadastroBasico(getContexto().getFabricaGerente()));

	  try {
		 return cadastro.findById(usuarioCartorioModuloPerfil);

	  } catch (CadastroException e) {
		 throw new ApplicationException();
	  }
   }

   @Override
   public List<Cidade> obterCidades() throws ApplicationException {
	  CadastroCidade cadastroCidade=new CadastroCidade();
	  cadastroCidade.setContexto(new ContextoCadastroBasico(getContexto().getFabricaGerente()));

	  try {
		 return cadastroCidade.listaTodasCidades();
	  } catch (CadastroException e) {
		 throw new ApplicationException();
	  }
   }

   @Override
   public List<Cartorio> obterCidadesCartorioTO(String s) throws ApplicationException {
	  CadastroCartorio cadastroCartorio=new CadastroCartorio();
	  cadastroCartorio.setContexto(new ContextoCadastroBasico(getContexto().getFabricaGerente()));
	  try {
		 return cadastroCartorio.listaCidadesTOAgrupadas(s);
	  } catch (CadastroException e) {
		 throw new ApplicationException();
	  }
   }

   @Override
   public List<Cidade> obterCidadesBrasil(String s) throws ApplicationException {
	  CadastroCidade cadastroCidade=new CadastroCidade();
	  cadastroCidade.setContexto(new ContextoCadastroBasico(getContexto().getFabricaGerente()));
	  try {
		 return cadastroCidade.listaCidadesBRAgrupadas(s);
	  } catch (CadastroException e) {
		 throw new ApplicationException();
	  }
   }

   @Override
   public List<Cidade> obterCidadesPorUF(UF uf) throws ApplicationException {
	  CadastroCidade cadastroCidade=new CadastroCidade();
	  cadastroCidade.setContexto(new ContextoCadastroBasico(getContexto().getFabricaGerente()));

	  try {
		 return cadastroCidade.listaTodasCidades(uf);
	  } catch (CadastroException e) {
		 // System.out.println("\n\n\n"+e.toString());
		 throw new ApplicationException();
	  }
   }

   @Override
   public List<IbgeUF> obterIbgeUFPorNomeCidade(String cidade) throws ApplicationException {
	  CadastroIbgeUF cadastroIbgeUF=new CadastroIbgeUF();
	  cadastroIbgeUF.setContexto(new ContextoCadastroBasico(getContexto().getFabricaGerente()));

	  try {
		 return cadastroIbgeUF.listaIbgeUFsPorCidade(cidade);
	  } catch (CadastroException e) {
		 // System.out.println("\n\n\n"+e.toString());
		 throw new ApplicationException();
	  }
   }

   @Override
   public List<IbgeUF> obterMunicipiosPorNomeUF(String cidade, UF ufSelecionada) throws ApplicationException {
	  CadastroIbgeUF cadastroIbgeUF=new CadastroIbgeUF();
	  cadastroIbgeUF.setContexto(new ContextoCadastroBasico(getContexto().getFabricaGerente()));

	  try {
		 return cadastroIbgeUF.listaIbgeUFsPorCidadeUF(cidade , ufSelecionada);
	  } catch (CadastroException e) {
		 // System.out.println("\n\n\n"+e.toString());
		 throw new ApplicationException();
	  }
   }

   @Override
   public List<IbgeUF> obterIbgeUFPorUF(UF ufSelecionada) throws ApplicationException {
	  CadastroIbgeUF cadastroIbgeUF=new CadastroIbgeUF();
	  cadastroIbgeUF.setContexto(new ContextoCadastroBasico(getContexto().getFabricaGerente()));

	  try {
		 return cadastroIbgeUF.listaIbgeUFsPorUF(ufSelecionada.getSiglaUF());
	  } catch (CadastroException e) {
		 // System.out.println("\n\n\n"+e.toString());
		 throw new ApplicationException();
	  }
   }

   @Override
   public List<Pais> obterPaisPorNomeCidade(String cidade) throws ApplicationException {
	  CadastroPais cadastroIbgeUF=new CadastroPais();
	  cadastroIbgeUF.setContexto(new ContextoCadastroBasico(getContexto().getFabricaGerente()));

	  try {
		 return cadastroIbgeUF.listaIbgeUFsPorCidade(cidade);
	  } catch (CadastroException e) {
		 // System.out.println("\n\n\n"+e.toString());
		 throw new ApplicationException();
	  }
   }

   @Override
   public List<UF> obterUFs() throws ApplicationException {
	  CadastroUF cadastroUF=new CadastroUF();
	  cadastroUF.setContexto(new ContextoCadastroBasico(getContexto().getFabricaGerente()));

	  try {
		 return cadastroUF.listaTodosUFs();
	  } catch (CadastroException e) {
		 throw new ApplicationException();
	  }
   }

   @Override
   public List<Local> obterLocais() throws ApplicationException {
	  CadastroLocal cadastroLocal=new CadastroLocal();
	  cadastroLocal.setContexto(new ContextoCadastroBasico(getContexto().getFabricaGerente()));

	  try {
		 return cadastroLocal.listaTodosLocais();
	  } catch (CadastroException e) {
		 throw new ApplicationException();
	  }
   }

   @Override
   public List<Pais> obterPaises() throws ApplicationException {
	  CadastroPais cadastroPais=new CadastroPais();
	  cadastroPais.setContexto(new ContextoCadastroBasico(getContexto().getFabricaGerente()));

	  try {
		 return cadastroPais.listaTodosIbgeUFs();
	  } catch (CadastroException e) {
		 throw new ApplicationException();
	  }
   }

   @Override
   public List<UsuarioCartorio> obterUsuariosCartorioPorEmail(String email) throws ApplicationException {
	  CadastroUsuarioCartorio cadastroUsuarioCartorio=new CadastroUsuarioCartorio();
	  cadastroUsuarioCartorio.setContexto(new ContextoCadastroBasico(getContexto().getFabricaGerente()));

	  try {
		 return cadastroUsuarioCartorio.findByEmailForValidacao(email);
	  } catch (FinderException e) {
		 throw new UsuarioNaoEncontradoException();
	  } catch (CadastroException e) {
		 throw new ApplicationException(e);
	  }
   }

   @Override
   public List<UsuarioCartorio> obterUsuariosCartorioPorLogin(String login) throws ApplicationException {
	  CadastroUsuarioCartorio cadastroUsuarioCartorio=new CadastroUsuarioCartorio();
	  cadastroUsuarioCartorio.setContexto(new ContextoCadastroBasico(getContexto().getFabricaGerente()));

	  try {
		 return cadastroUsuarioCartorio.findByLoginForValidacao(login);
	  } catch (FinderException e) {
		 throw new UsuarioNaoEncontradoException();
	  } catch (CadastroException e) {
		 throw new ApplicationException(e);
	  }
   }

   @Override
   public UsuarioCartorio obterUsuarioCartorioPorEmail(String email) throws ApplicationException {
	  CadastroUsuarioCartorio cadastroUsuarioCartorio=new CadastroUsuarioCartorio();
	  cadastroUsuarioCartorio.setContexto(new ContextoCadastroBasico(getContexto().getFabricaGerente()));

	  try {
		 return cadastroUsuarioCartorio.findByEmailForValidacaoSingle(email);
	  } catch (FinderException e) {
		 throw new UsuarioNaoEncontradoException();
	  } catch (CadastroException e) {
		 throw new ApplicationException(e);
	  }
   }

   @Override
   public String enviarEmailStatico(String titulo, String mensagem, String destino) {

	  HtmlEmail email=new HtmlEmail();

	  String userName="alexrocha.tecnotins";
	  String password="@lexsilv@";
	  Integer tamanho=mensagem.length();

	  try {

		 URL url=new URL("http://tecnotins.com.br/wp/wp-content/themes/oxygen-theme/images/logo-tec.png");
		 String cid=email.embed(url , "Logo Tecnotins");
		 String imagem="<img src=\"cid:" + cid + "\">";
		 // posiciona a imagem depois do <html>
		 StringBuilder stringBuilder=new StringBuilder(mensagem);
		 stringBuilder.insert((tamanho - tamanho) + 6 , imagem);

		 mensagem=stringBuilder.toString();

		 email.setHostName("smtp.gmail.com");
		 email.setSmtpPort(465);
		 email.setAuthentication(userName , password);
		 email.setSSLOnConnect(true);
		 email.setFrom("alexrocha.tecnotins@gmail.com" , "TECNOTINS");
		 email.setSubject(titulo); // Assunto
		 email.setHtmlMsg(mensagem);
		 email.addTo(destino);
		 email.send();
	  } catch (EmailException | MalformedURLException e) {
		 e.printStackTrace();
	  }

	  return null;
   }

   @Override
   public Perfil obterPerfilPorId(Integer id) throws ApplicationException, UsuarioNaoEncontradoException, ValidacaoException {

	  CadastroPerfil cadastroPerfil=new CadastroPerfil();
	  cadastroPerfil.setContexto(new ContextoCadastroBasico(getContexto().getFabricaGerente()));

	  try {
		 return cadastroPerfil.findById(id);
	  } catch (FinderException e) {
		 throw new UsuarioNaoEncontradoException();
	  } catch (CadastroException e) {
		 throw new ApplicationException(e);
	  }
   }
   /* ___________________________________________ */

   @Override
   public List<RegistroNascimento> obterRegistrosNascimentoUsuarioCartorio(UsuarioCartorio usuarioCartorio) {
	  CadastroRegistroNascimento cadastroRegistroNascimento=new CadastroRegistroNascimento();
	  cadastroRegistroNascimento.setContexto(new ContextoCadastroBasico(getContexto().getFabricaGerente()));
	  List<RegistroNascimento> lista=new ArrayList<>();
	  try {
		 lista=cadastroRegistroNascimento.findByUsuarioCartorioId(usuarioCartorio);
	  } catch (CadastroException e) {
		 e.printStackTrace();
	  }
	  return lista;
   }
   
   public RegistroNascimento obterRegistrosNascimentoByID(RegistroNascimento registroNascimento) {
	  CadastroRegistroNascimento cadastroRegistroNascimento=new CadastroRegistroNascimento();
	  cadastroRegistroNascimento.setContexto(new ContextoCadastroBasico(getContexto().getFabricaGerente()));
	  RegistroNascimento registro= new RegistroNascimento();
	  try {
		 registro = cadastroRegistroNascimento.findById(registroNascimento);
	  } catch (CadastroException e) {
		e.printStackTrace();
	  }
	  return registro;
   }

   @Override
   public List<RegistroNascimento> obterRegistrosNascimentoUsuarioCartorioDate(UsuarioCartorio usuarioCartorio, Date data1, Date data2) throws ApplicationException, FinderException {
	  CadastroRegistroNascimento cadastroRegistroNascimento=new CadastroRegistroNascimento();
	  cadastroRegistroNascimento.setContexto(new ContextoCadastroBasico(getContexto().getFabricaGerente()));
	  List<RegistroNascimento> lista=new ArrayList<>();
	  try {
		 lista=cadastroRegistroNascimento.findByUsuarioCartorioDateId(usuarioCartorio , data1 , data2);
	  } catch (CadastroException e) {
		 throw new FinderException(e);
	  }
	  return lista;
   }
}
