package br.com.datasind.gerente;

import java.util.Date;
import java.util.List;

import br.com.datasind.aplicacao.ApplicationException;
import br.com.datasind.cadastro.FinderException;
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

public interface GerenteControleAcesso extends GerenteModulo {
	public Usuario verificarUsuario(Usuario usuario) throws ApplicationException, UsuarioNaoEncontradoException, ValidacaoException, SenhaInvalidaException,
			UsuarioInativoException, PrimeiraSenhaException;
	
	public UsuarioSite verificarUsuarioSite(UsuarioSite usuarioSite) throws ApplicationException, UsuarioNaoEncontradoException, ValidacaoException, SenhaInvalidaException,
	UsuarioInativoException;

	public Usuario obterUsuarioPorId(Usuario usuario) throws ApplicationException, UsuarioNaoEncontradoException, ValidacaoException;
	
	public UsuarioSite obterUsuarioSitePorId(UsuarioSite usuarioSiteId) throws ApplicationException, UsuarioNaoEncontradoException, ValidacaoException;
	
	public UsuarioSite obterUsuarioSitePorUID(String uid) throws ApplicationException, UsuarioNaoEncontradoException, ValidacaoException;

	public List<Usuario> obterUsuarioPorLogin(String login) throws ApplicationException, UsuarioNaoEncontradoException, ValidacaoException;
	
	public List<UsuarioSite> obterUsuariosSitePorEmail(String email) throws ApplicationException, UsuarioNaoEncontradoException, ValidacaoException;
	
	public UsuarioSite obterUsuarioSitePorEmail(String email) throws ApplicationException, UsuarioNaoEncontradoException, ValidacaoException;

	public List<Usuario> obterListaUsuarios() throws ApplicationException;
	
	public List<CertidaoPedido> obterListaCertidaoPedidos() throws ApplicationException;
	
	public List<CertidaoPedido> obterListaPedidosUsuarioSite(UsuarioSite usuarioSite) throws ApplicationException;
	
	public List<CertidaoPedido> obterListaPedidosUsuarioSiteCompleto(UsuarioSite usuarioSite) throws ApplicationException;
	
	public List<CertidaoPedido> obterListaPedidosByUsuarioPeriodo(UsuarioSite usuarioSite, Date data1, Date data2) throws ApplicationException, FinderException;
	
	public List<Correios> obterListaPrecosCorrreios() throws ApplicationException;
	
	public List<TipoLivro> obterListaTiposLivro() throws ApplicationException;
	
	public String obterCodigoVerificador(String valores) throws ApplicationException;
	
	public List<CustoPedido> obterCustosPedido() throws ApplicationException;
	
	public List<UsuarioSite> obterListaUsuariosSite() throws ApplicationException;
	
	public List<UsuarioSitePerfil> obterListaUsuariosSitePerfil() throws ApplicationException;

	public List<PermissaoAcesso> obterPermissoesAcessoModulo(Perfil perfil) throws ApplicationException;

	public Modulo obterModuloPeloNomeInterno(String nomeInterno) throws ApplicationException;

	public Modulo obterModuloPorId(Integer id) throws ApplicationException;

	public Modulo obterModuloPorId(Modulo modulo) throws ApplicationException, UsuarioNaoEncontradoException, ValidacaoException;

	public PermissaoAcesso obterPermissoesAcessoPorUsuarioEModulo(UsuarioSite usuarioSite, Modulo modulo) throws ApplicationException;

	public Usuario getUsuarioSessao();
	
	public String obterMensagemAvisoEmail();
	
	public String enviarEmailStatico(String titulo, String mensagem, String destino);

	public List<Modulo> obterListaModulos() throws ApplicationException;

	public List<Perfil> obterListaPerfisPorPerfilFiltro(Perfil perfilFiltro) throws ApplicationException;

	public List<?> obterListaModuloNomeUrlPorUsuario(Usuario usuarioSessao) throws ApplicationException;

	public Perfil obterPerfilPorId(Integer id) throws ApplicationException, UsuarioNaoEncontradoException, ValidacaoException;

	public List<Modulo> obterModulos() throws ApplicationException;

	public UF obterUFPorId(Integer id) throws ApplicationException;
	
	public IbgeUF obterIbgeUFPorId(Integer id) throws ApplicationException;
	
	public CustoPedido obterCustoPedidoPorId(Integer id) throws ApplicationException;
	
	public UF obterUFPorId(UF uf) throws ApplicationException, UsuarioNaoEncontradoException, ValidacaoException;
	
	public Cartorio obterCartorioPorId(Integer id) throws ApplicationException;
	
	public Correios obterCorreiosPorId(Integer id) throws ApplicationException;
	
	public List<Cartorio> obterCartorios() throws ApplicationException;
	
	public List<Cartorio> obterCartoriosPorMunicipio(String s) throws ApplicationException;

	public Cidade obterCidadePorId(Integer id) throws ApplicationException;

	public Cidade obterCidadePorId(Cidade cidade) throws ApplicationException, UsuarioNaoEncontradoException, ValidacaoException;
	
	public CertidaoPedido obterCertidaoPedidoPorId(CertidaoPedido certidao) throws ApplicationException, UsuarioNaoEncontradoException, ValidacaoException;

	public PermissaoAcesso obterPermissaoAcessoPorId(PermissaoAcesso permissaoAcesso) throws ApplicationException, ValidacaoException;

	public List<UsuarioSiteModuloPerfil> obterListaPerfilPorUsuario(Usuario usuario) throws ApplicationException;

	public UsuarioSiteModuloPerfil obterUsuarioModuloPerfilPorId(UsuarioSiteModuloPerfil usuarioModuloPerfil) throws ApplicationException;

	public List<Perfil> obterListaPerfilPorModulo(Modulo modulo) throws ApplicationException;

	public UsuarioSiteModuloPerfil obterUsuarioModuloPerfilPorIdUsuarioIdPerfil(Integer idUsuario, Integer idPerfil) throws ApplicationException;

	public List<Cidade> obterCidades() throws ApplicationException;
	
	public List<Cartorio> obterCidadesCartorioTO(String s) throws ApplicationException;
	
	public List<Cidade> obterCidadesBrasil(String s) throws ApplicationException;

	public List<Cidade> obterCidadesPorUF(UF uf) throws ApplicationException;

	public List<UF> obterUFs() throws ApplicationException;

   public List<IbgeUF> obterMunicipiosPorNomeUF(String cidade, UF ufSelecionada) throws ApplicationException;

}