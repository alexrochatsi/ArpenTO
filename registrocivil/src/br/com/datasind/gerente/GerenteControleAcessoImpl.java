
package br.com.datasind.gerente;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.List;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.log4j.Logger;

import br.com.datasind.aplicacao.ApplicationException;
import br.com.datasind.cadastro.CadastroCartorio;
import br.com.datasind.cadastro.CadastroCertidaoPedido;
import br.com.datasind.cadastro.CadastroCidade;
import br.com.datasind.cadastro.CadastroControleAcesso;
import br.com.datasind.cadastro.CadastroCorreios;
import br.com.datasind.cadastro.CadastroCustoPedido;
import br.com.datasind.cadastro.CadastroException;
import br.com.datasind.cadastro.CadastroIbgeUF;
import br.com.datasind.cadastro.CadastroModulo;
import br.com.datasind.cadastro.CadastroPerfil;
import br.com.datasind.cadastro.CadastroPermissaoAcesso;
import br.com.datasind.cadastro.CadastroTipoLivro;
import br.com.datasind.cadastro.CadastroUF;
import br.com.datasind.cadastro.CadastroUsuario;
import br.com.datasind.cadastro.CadastroUsuarioModuloPerfil;
import br.com.datasind.cadastro.CadastroUsuarioSite;
import br.com.datasind.cadastro.CadastroUsuarioSitePerfil;
import br.com.datasind.cadastro.ContextoCadastroBasico;
import br.com.datasind.cadastro.FinderException;
import br.com.datasind.conversao.util.Codificador;
import br.com.datasind.entidade.Cartorio;
import br.com.datasind.entidade.CertidaoPedido;
import br.com.datasind.entidade.Cidade;
import br.com.datasind.entidade.Correios;
import br.com.datasind.entidade.CustoPedido;
import br.com.datasind.entidade.IbgeUF;
import br.com.datasind.entidade.Modulo;
import br.com.datasind.entidade.Perfil;
import br.com.datasind.entidade.PermissaoAcesso;
import br.com.datasind.entidade.TipoLivro;
import br.com.datasind.entidade.UF;
import br.com.datasind.entidade.Usuario;
import br.com.datasind.entidade.UsuarioSiteModuloPerfil;
import br.com.datasind.entidade.UsuarioSitePerfil;
import br.com.datasind.entidade.UsuarioSite;
import br.com.datasind.validacao.ValidacaoException;

public class GerenteControleAcessoImpl extends GerentePadrao implements GerenteControleAcesso{

   private static final Logger LOGGER=Logger.getLogger(GerenteControleAcessoImpl.class);

   @SuppressWarnings("unused")
   private Usuario usuarioSessao;

   @SuppressWarnings("unused")
   private UsuarioSite usuarioSiteSessao;

   public GerenteControleAcessoImpl() {
	  super();
   }

   public Usuario verificarUsuario(Usuario usuario) throws ApplicationException, UsuarioNaoEncontradoException, ValidacaoException, SenhaInvalidaException, UsuarioInativoException,
	  PrimeiraSenhaException {

	  if(usuario.getSenha() == null || usuario.getLogin() == null) {
		 throw new ValidacaoException("Ambos os campos devem estar preenchidos.");
	  }

	  CadastroUsuario cadastroUsuario=new CadastroUsuario();
	  cadastroUsuario.setContexto(new ContextoCadastroBasico(getContexto().getFabricaGerente()));
	  try {
		 // System.out.println(Codificador.codificaSenhaMD5(usuario.getSenha()));
		 // PesquisaDocumento no BD os dados do usuario informados no login
		 Usuario encontrado=cadastroUsuario.findByLogin(usuario.getLogin());

		 // Checa se usuario esta inativo no cadastro
		 if( !encontrado.getFlagAtivo()) {
			throw new UsuarioInativoException();
		 }

		 // seta senha MD5 em Hexa
		 usuario.setSenha(Codificador.codificaSenhaMD5(usuario.getSenha()));

		 LOGGER.debug("Hash da senha do usuario encontrado :" + encontrado.getSenha());

		 LOGGER.debug("Hash da senha entrada pelo usuario :" + usuario.getSenha());

		 if(usuario.getSenha() == null || !usuario.getSenha().equals(encontrado.getSenha())) {
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

		 setUsuarioSessao(encontrado);

		 // Se o usuario esta entrando com a primeira senha
		 if(getUsuarioSessao().getSenha().equals(Codificador.codificaSenhaMD5(Usuario.PRIMEIRA_SENHA))) {
			throw new PrimeiraSenhaException();
		 }

		 getContexto().getFabricaGerente().getGerenteTransacao().setUserId(String.valueOf(encontrado.getId()));

		 return encontrado;

	  } catch (FinderException e) {
		 throw new UsuarioNaoEncontradoException();

	  } catch (CadastroException e) {
		 throw new ApplicationException(e);
	  }

   }

   @Override
   public UsuarioSite verificarUsuarioSite(UsuarioSite usuarioSite) throws ApplicationException, UsuarioNaoEncontradoException, ValidacaoException, SenhaInvalidaException, UsuarioInativoException {

	  if(usuarioSite.getSenha() == null || usuarioSite.getEmail() == null) {
		 throw new ValidacaoException("Ambos os campos devem estar preenchidos.");
	  }

	  CadastroUsuarioSite cadastroUsuarioSite=new CadastroUsuarioSite();
	  cadastroUsuarioSite.setContexto(new ContextoCadastroBasico(getContexto().getFabricaGerente()));
	  try {
		 // PesquisaEmail no BD os dados do usuario informados no login
		 UsuarioSite encontrado=cadastroUsuarioSite.findByEmail(usuarioSite.getEmail());
		 // Checa se usuario esta inativo no cadastro
		 if( !encontrado.getFlagAtivo()) {
			throw new UsuarioInativoException();
		 }

		 // seta senha MD5 em Hexa
		 usuarioSite.setSenha(Codificador.codificaSenhaMD5(usuarioSite.getSenha()));

		 LOGGER.debug("Hash da senha do usuario encontrado :" + encontrado.getSenha());

		 LOGGER.debug("Hash da senha entrada pelo usuario :" + usuarioSite.getSenha());

		 if(encontrado.getValidaEmail() == null || encontrado.getValidaEmail() == false) {
			System.out.println("EMAIL INATIVO!");
			throw new EmailInativoException();
		 }

		 if(usuarioSite.getSenha() == null || !usuarioSite.getSenha().equals(encontrado.getSenha())) {
			System.out.println("A SENHA É INVALIDA!");
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

		 setUsuarioSiteSessao(encontrado);

		 getContexto().getFabricaGerente().getGerenteTransacao().setUserId(String.valueOf(encontrado.getId()));

		 return encontrado;

	  } catch (FinderException e) {
		 throw new UsuarioNaoEncontradoException();

	  } catch (CadastroException e) {
		 throw new ApplicationException(e);
	  }

   }

   @Override
   public Usuario obterUsuarioPorId(Usuario usuario) throws ApplicationException, UsuarioNaoEncontradoException, ValidacaoException {

	  CadastroUsuario cadastroUsuario=new CadastroUsuario();
	  cadastroUsuario.setContexto(new ContextoCadastroBasico(getContexto().getFabricaGerente()));

	  try {
		 return cadastroUsuario.findById(usuario);
	  } catch (FinderException e) {
		 throw new UsuarioNaoEncontradoException();
	  } catch (CadastroException e) {
		 throw new ApplicationException(e);
	  }
   }

   @Override
   public UsuarioSite obterUsuarioSitePorId(UsuarioSite usuarioSite) throws ApplicationException, UsuarioNaoEncontradoException, ValidacaoException {

	  CadastroUsuarioSite cadastroUsuarioSite=new CadastroUsuarioSite();
	  cadastroUsuarioSite.setContexto(new ContextoCadastroBasico(getContexto().getFabricaGerente()));

	  try {
		 return cadastroUsuarioSite.findById(usuarioSite);
	  } catch (FinderException e) {
		 throw new UsuarioNaoEncontradoException();
	  } catch (CadastroException e) {
		 throw new ApplicationException(e);
	  }
   }

   @Override
   public UsuarioSite obterUsuarioSitePorUID(String uid) throws ApplicationException, UsuarioNaoEncontradoException, ValidacaoException {

	  CadastroUsuarioSite cadastroUsuarioSite=new CadastroUsuarioSite();
	  cadastroUsuarioSite.setContexto(new ContextoCadastroBasico(getContexto().getFabricaGerente()));

	  try {
		 return cadastroUsuarioSite.findByUID(uid);
	  } catch (FinderException e) {
		 throw new UsuarioNaoEncontradoException();
	  } catch (CadastroException e) {
		 throw new ApplicationException(e);
	  }
   }

   @Override
   public List<Usuario> obterUsuarioPorLogin(String login) throws ApplicationException, UsuarioNaoEncontradoException, ValidacaoException {

	  CadastroUsuario cadastroUsuario=new CadastroUsuario();
	  cadastroUsuario.setContexto(new ContextoCadastroBasico(getContexto().getFabricaGerente()));

	  try {
		 return cadastroUsuario.findByLoginForValidacao(login);
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

   public PermissaoAcesso obterPermissoesAcessoPorUsuarioEModulo(UsuarioSite usuarioSite, Modulo modulo) throws ApplicationException {
	  CadastroControleAcesso cadastroControleAcesso=new CadastroControleAcesso();
	  cadastroControleAcesso.setContexto(new ContextoCadastroBasico(getContexto().getFabricaGerente()));
	  try {
		 return cadastroControleAcesso.selecionarPermissoesAcessoPorUsuarioEModulo(usuarioSite , modulo);

	  } catch (CadastroException e) {
		 throw new ApplicationException(e);
	  }
   }

   public Usuario getUsuarioSessao() {
	  return UsuarioThread.getInstance().get();
   }

   private void setUsuarioSessao(Usuario usuarioSessao) {
	  UsuarioThread.getInstance().set(usuarioSessao);
   }

   public UsuarioSite getUsuarioSiteSessao() {
	  return UsuarioSiteThread.getInstance().get();
   }

   private void setUsuarioSiteSessao(UsuarioSite usuarioSiteSessao) {
	  UsuarioSiteThread.getInstance().set(usuarioSiteSessao);
   }

   @Override
   public List<CertidaoPedido> obterListaPedidosByUsuarioPeriodo(UsuarioSite usuarioSite, Date data1, Date data2) throws ApplicationException, FinderException {
	  CadastroCertidaoPedido cadastroCertidaoPedido=new CadastroCertidaoPedido();
	  cadastroCertidaoPedido.setContexto(new ContextoCadastroBasico(getContexto().getFabricaGerente()));

	  try {
		 return cadastroCertidaoPedido.findByUsuarioIdParcialDate(usuarioSite , data1 , data2);
	  } catch (CadastroException e) {
		 throw new FinderException(e);
	  }
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

   public List<?> obterListaModuloNomeUrlPorUsuario(Usuario usuarioSessao) throws ApplicationException {
	  CadastroModulo cadastro=new CadastroModulo();
	  cadastro.setContexto(new ContextoCadastroBasico(getContexto().getFabricaGerente()));
	  try {
		 return cadastro.findListaModuloNomeUrlPorUsuario(usuarioSessao);

	  } catch (Exception e) {
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
		 return cadastroCartorio.findAll();
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
   public List<UsuarioSiteModuloPerfil> obterListaPerfilPorUsuario(Usuario usuario) throws ApplicationException {

	  CadastroUsuarioModuloPerfil usuarioModuloPerfil=new CadastroUsuarioModuloPerfil();
	  usuarioModuloPerfil.setContexto(new ContextoCadastroBasico(getContexto().getFabricaGerente()));

	  try {
		 return usuarioModuloPerfil.obterListaUsuarioModuloPerfilPorUsuario(usuario);
	  } catch (CadastroException e) {
		 throw new ApplicationException(e);
	  }
   }

   @Override
   public List<Usuario> obterListaUsuarios() throws ApplicationException {
	  CadastroUsuario cadastroUsuario=new CadastroUsuario();
	  cadastroUsuario.setContexto(new ContextoCadastroBasico(getContexto().getFabricaGerente()));

	  try {
		 return cadastroUsuario.findAllUsuario();
	  } catch (CadastroException e) {
		 throw new ApplicationException(e);
	  }
   }
   /*
    * @Override public List<String> obterListaEstados() throws ApplicationException {
    * System.out.println("CHEGUEI AQUI 1"); CadastroIbgeUF cadastroIbgeUF=new CadastroIbgeUF();
    * cadastroIbgeUF.setContexto(new ContextoCadastroBasico(getContexto().getFabricaGerente()));
    * 
    * try { return cadastroIbgeUF.listaTodosStringUFs(); } catch (CadastroException e) { throw new
    * ApplicationException(e); } }
    */

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
   public List<CertidaoPedido> obterListaPedidosUsuarioSite(UsuarioSite usuarioSite) throws ApplicationException {
	  CadastroCertidaoPedido cadastroCertidaoPedido=new CadastroCertidaoPedido();
	  cadastroCertidaoPedido.setContexto(new ContextoCadastroBasico(getContexto().getFabricaGerente()));

	  try {
		 return cadastroCertidaoPedido.findByUsuarioSiteIdParcial(usuarioSite);
	  } catch (CadastroException e) {
		 throw new ApplicationException(e);
	  }
   }

   @Override
   public List<CertidaoPedido> obterListaPedidosUsuarioSiteCompleto(UsuarioSite usuarioSite) throws ApplicationException {
	  CadastroCertidaoPedido cadastroCertidaoPedido=new CadastroCertidaoPedido();
	  cadastroCertidaoPedido.setContexto(new ContextoCadastroBasico(getContexto().getFabricaGerente()));

	  try {
		 return cadastroCertidaoPedido.findByUsuarioSiteId(usuarioSite);
	  } catch (CadastroException e) {
		 throw new ApplicationException(e);
	  }
   }

   @Override
   public List<Correios> obterListaPrecosCorrreios() throws ApplicationException {
	  CadastroCorreios cadastroCorreios=new CadastroCorreios();
	  cadastroCorreios.setContexto(new ContextoCadastroBasico(getContexto().getFabricaGerente()));

	  try {
		 return cadastroCorreios.find2Primeiros();
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
   public String obterCodigoVerificador(String valores) throws ApplicationException {
	  CadastroTipoLivro cadastroTipoLivro=new CadastroTipoLivro();
	  cadastroTipoLivro.setContexto(new ContextoCadastroBasico(getContexto().getFabricaGerente()));

	  String cv=cadastroTipoLivro.obterCV(valores);
	  return cv;
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
   public List<UsuarioSite> obterListaUsuariosSite() throws ApplicationException {
	  CadastroUsuarioSite cadastroUsuarioSite=new CadastroUsuarioSite();
	  cadastroUsuarioSite.setContexto(new ContextoCadastroBasico(getContexto().getFabricaGerente()));

	  try {
		 return cadastroUsuarioSite.findAllUsuarioSite();
	  } catch (CadastroException e) {
		 throw new ApplicationException(e);
	  }
   }

   @Override
   public List<UsuarioSitePerfil> obterListaUsuariosSitePerfil() throws ApplicationException {
	  CadastroUsuarioSitePerfil cadastroUsuarioSitePerfil=new CadastroUsuarioSitePerfil();
	  cadastroUsuarioSitePerfil.setContexto(new ContextoCadastroBasico(getContexto().getFabricaGerente()));

	  try {
		 return cadastroUsuarioSitePerfil.findAllUsuarioSitePerfil();
	  } catch (CadastroException e) {
		 throw new ApplicationException(e);
	  }
   }

   @Override
   public UsuarioSiteModuloPerfil obterUsuarioModuloPerfilPorId(UsuarioSiteModuloPerfil usuarioModuloPerfil) throws ApplicationException {

	  CadastroUsuarioModuloPerfil cadastro=new CadastroUsuarioModuloPerfil();
	  cadastro.setContexto(new ContextoCadastroBasico(getContexto().getFabricaGerente()));

	  try {
		 return cadastro.findById(usuarioModuloPerfil);

	  } catch (CadastroException e) {
		 throw new ApplicationException();
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
   public UsuarioSiteModuloPerfil obterUsuarioModuloPerfilPorIdUsuarioIdPerfil(Integer idUsuario, Integer idPerfil) throws ApplicationException {
	  CadastroUsuarioModuloPerfil cadastro=new CadastroUsuarioModuloPerfil();
	  cadastro.setContexto(new ContextoCadastroBasico(getContexto().getFabricaGerente()));

	  try {
		 return cadastro.findByUsuarioPerfil(idUsuario , idPerfil);
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
   public List<UsuarioSite> obterUsuariosSitePorEmail(String email) throws ApplicationException {
	  CadastroUsuarioSite cadastroUsuarioSite=new CadastroUsuarioSite();
	  cadastroUsuarioSite.setContexto(new ContextoCadastroBasico(getContexto().getFabricaGerente()));

	  try {
		 return cadastroUsuarioSite.findByEmailForValidacao(email);
	  } catch (FinderException e) {
		 throw new UsuarioNaoEncontradoException();
	  } catch (CadastroException e) {
		 throw new ApplicationException(e);
	  }
   }

   @Override
   public UsuarioSite obterUsuarioSitePorEmail(String email) throws ApplicationException {
	  CadastroUsuarioSite cadastroUsuarioSite=new CadastroUsuarioSite();
	  cadastroUsuarioSite.setContexto(new ContextoCadastroBasico(getContexto().getFabricaGerente()));

	  try {
		 return cadastroUsuarioSite.findByEmailForValidacaoSingle(email);
	  } catch (FinderException e) {
		 throw new UsuarioNaoEncontradoException();
	  } catch (CadastroException e) {
		 throw new ApplicationException(e);
	  }
   }

   @Override
   public String enviarEmailStatico(String titulo, String mensagem, String destino) {

	  HtmlEmail email=new HtmlEmail();

	  String userName="naoresponda@arpento.org.br";
	  String password="@NkWBJruBe3@ArPenTO";
	  Integer tamanho=mensagem.length();

	  try {

		 URL url=new URL("http://arpento.org.br/wp-content/uploads/2016/08/confirme_solicitacao.jpg");
		 String cid=email.embed(url , "ARPENTO");
		 String imagem="<img src=\"cid:" + cid + "\">";
		 // posiciona a imagem depois do <html>
		 StringBuilder stringBuilder=new StringBuilder(mensagem);
		 stringBuilder.insert((tamanho - tamanho) + 6 , imagem);

		 mensagem=stringBuilder.toString();

		 email.setHostName("smtp.arpento.org.br");
		 email.setSmtpPort(587);
		 email.setAuthentication(userName , password);
		 email.setSSLOnConnect(false); // Webmail da CENTRALSERVER na trabalha com SSL
		 email.setCharset("UTF-8"); // Acertando as Acentuações no EMAIL
		 email.setFrom("naoresponda@arpento.org.br" , "ARPENTO - Associação de Registradores de Pessoas Naturais do Tocantins");
		 email.setSubject(titulo); // Assunto
		 email.setHtmlMsg(mensagem);
		 email.addTo(destino);
		 // email.setCharset("UTF-8"); // Acertando as Acentuações no EMAIL
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
   public String obterMensagemAvisoEmail() {
	  String mensagemAviso="<hr /><p><strong>AVISO LEGAL</strong>: Esta mensagem &eacute; destinada exclusivamente para a(s) pessoa(s)"
		 + " a quem &eacute; dirigida, podendo conter informa&ccedil;&atilde;o confidencial e/ou legalmente privilegiada."
		 + " Se voc&ecirc; n&atilde;o for destinat&aacute;rio desta mensagem, desde j&aacute; fica notificado de abster-se"
		 + " a divulgar, copiar, distribuir, examinar ou, de qualquer forma, utilizar a informa&ccedil;&atilde;o contida"
		 + " nesta mensagem, por ser ilegal. Caso voc&ecirc; tenha recebido esta mensagem por engano, pedimos que nos"
		 + " retorne este E-Mail, promovendo, desde logo, a elimina&ccedil;&atilde;o do seu conte&uacute;do em sua base"
		 + " de dados, registros ou sistema de controle. Fica desprovida de efic&aacute;cia e validade a mensagem que"
		 + " contiver v&iacute;nculos obrigacionais, expedida por quem n&atilde;o detenha poderes de" + " representa&ccedil;&atilde;o.</p></html>";
	  return mensagemAviso;
   }

}
