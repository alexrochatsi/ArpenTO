
package br.com.datasind.gerente;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import br.com.datasind.aplicacao.ApplicationException;
import br.com.datasind.cadastro.FinderException;
import br.com.datasind.entidade.Banco;
import br.com.datasind.entidade.Cartorio;
import br.com.datasind.entidade.CertidaoPedido;
import br.com.datasind.entidade.Cidade;
import br.com.datasind.entidade.ContaBancaria;
import br.com.datasind.entidade.Correios;
import br.com.datasind.entidade.CustoPedido;
import br.com.datasind.entidade.IbgeUF;
import br.com.datasind.entidade.Local;
import br.com.datasind.entidade.Modulo;
import br.com.datasind.entidade.Ocupacao;
import br.com.datasind.entidade.OrgaoEmissor;
import br.com.datasind.entidade.Pagamento;
import br.com.datasind.entidade.Pais;
import br.com.datasind.entidade.Perfil;
import br.com.datasind.entidade.PermissaoAcesso;
import br.com.datasind.entidade.RegistroNascimento;
import br.com.datasind.entidade.TipoLivro;
import br.com.datasind.entidade.UF;
import br.com.datasind.entidade.UsuarioAdministrador;
import br.com.datasind.entidade.UsuarioAdministradorModuloPerfil;
import br.com.datasind.entidade.UsuarioCartorio;
import br.com.datasind.validacao.ValidacaoException;

public interface GerenteControleAcesso extends GerenteModulo{

   public UsuarioAdministrador verificarUsuarioAdministrador(UsuarioAdministrador usuarioAdministrador) throws ApplicationException, UsuarioNaoEncontradoException, ValidacaoException, SenhaInvalidaException,
	  UsuarioInativoException;

   public UsuarioCartorio obterUsuarioCartorioPorId(UsuarioCartorio usuarioCartorioId) throws ApplicationException, UsuarioNaoEncontradoException, ValidacaoException;
   
   public UsuarioAdministrador obterUsuarioAdministradorPorId(UsuarioAdministrador usuarioAdministradorId) throws ApplicationException, UsuarioNaoEncontradoException, ValidacaoException;

   public UsuarioAdministrador obterUsuarioAdministradorPorUID(String uid) throws ApplicationException, UsuarioNaoEncontradoException, ValidacaoException;
   
   public UsuarioCartorio obterUsuarioCartorioPorUID(String uid) throws ApplicationException, UsuarioNaoEncontradoException, ValidacaoException;

   public List<UsuarioCartorio> obterUsuariosCartorioPorEmail(String email) throws ApplicationException, UsuarioNaoEncontradoException, ValidacaoException;
   
   public List<UsuarioCartorio> obterUsuariosCartorioPorLogin(String login) throws ApplicationException, UsuarioNaoEncontradoException, ValidacaoException;
   
   public List<UsuarioAdministrador> obterUsuariosAdministradorPorLogin(String login) throws ApplicationException, UsuarioNaoEncontradoException, ValidacaoException;

   public UsuarioAdministrador obterUsuarioAdministradorPorNomeLogin(String nomeLogin) throws ApplicationException, UsuarioNaoEncontradoException, ValidacaoException;
   
   public UsuarioCartorio obterUsuarioCartorioPorEmail(String email) throws ApplicationException, UsuarioNaoEncontradoException, ValidacaoException;

   // public List<String> obterListaEstados() throws ApplicationException;

   public List<CertidaoPedido> obterListaCertidaoPedidos() throws ApplicationException;

   public List<CertidaoPedido> obterListaPedidosPorCartorio(Cartorio cartorio) throws ApplicationException;
   
   public List<CertidaoPedido> obterListaPedidosBoletoGeradosAll(Cartorio cartorio) throws ApplicationException, FinderException;
   
   public List<CertidaoPedido> obterListaPedidosBoletoGerados(Cartorio cartorio, Date data1, Date data2) throws ApplicationException, FinderException;
   
   public BigDecimal obterTotalCustoPedidoMes(Cartorio cartorio, Calendar data) throws ApplicationException;

   public List<Correios> obterListaPrecosCorrreios() throws ApplicationException;

   public List<TipoLivro> obterListaTiposLivro() throws ApplicationException;

   public List<CustoPedido> obterCustosPedido() throws ApplicationException;
   
   public List<Banco> obterBancos() throws ApplicationException;
   
   public List<ContaBancaria> obterContasBancariasCartorio(Cartorio cartorio) throws ApplicationException;

   public List<UsuarioAdministrador> obterListaUsuariosAdministrador() throws ApplicationException;
   
   public List<UsuarioCartorio> obterListaUsuariosCartorio() throws ApplicationException;
   
   public List<Pagamento> obterListaPagamentos() throws ApplicationException;
   
   public List<Pagamento> obterListaPagamentosPeriodo(String mes, String ano) throws ApplicationException;
   
   public List<UsuarioCartorio> obterListaUsuariosCartorioByCartorio(Cartorio cartorio) throws ApplicationException;
   
   public List<Pagamento> obterListaPagamentosByCartorioPeriodo(Cartorio cartorio, String mes, String ano) throws ApplicationException;
   
   public List<CertidaoPedido> obterListaCertidoesPagarByCartorioPeriodo(Cartorio cartorio, String mes, String ano) throws ApplicationException;
   
   public List<CertidaoPedido> obterListaCertidoesPagarByPeriodo(String mes, String ano) throws ApplicationException;

   public List<PermissaoAcesso> obterPermissoesAcessoModulo(Perfil perfil) throws ApplicationException;

   public Modulo obterModuloPeloNomeInterno(String nomeInterno) throws ApplicationException;

   public Modulo obterModuloPorId(Integer id) throws ApplicationException;

   public IbgeUF obterIbgeUFPorId(Integer id) throws ApplicationException;

   public Pais obterPaisPorId(Integer id) throws ApplicationException;

   public List<Pais> obterPaises() throws ApplicationException;

   public OrgaoEmissor obterOrgaoEmissorPorId(Integer id) throws ApplicationException;

   public Ocupacao obterOcupacaoPorId(Integer id) throws ApplicationException;

   public Modulo obterModuloPorId(Modulo modulo) throws ApplicationException, UsuarioNaoEncontradoException, ValidacaoException;

   public PermissaoAcesso obterPermissoesAcessoPorUsuarioEModulo(UsuarioAdministrador usuarioAdministrador, Modulo modulo) throws ApplicationException;

   public String enviarEmailStatico(String titulo, String mensagem, String destino);

   public List<Modulo> obterListaModulos() throws ApplicationException;

   public List<Perfil> obterListaPerfisPorPerfilFiltro(Perfil perfilFiltro) throws ApplicationException;

   public Perfil obterPerfilPorId(Integer id) throws ApplicationException, UsuarioNaoEncontradoException, ValidacaoException;

   public List<Modulo> obterModulos() throws ApplicationException;

   public UF obterUFPorId(Integer id) throws ApplicationException;
   
   public Banco obterBancoPorId(Integer id) throws ApplicationException;

   public Local obterLocalPorId(Integer id) throws ApplicationException;

   public CustoPedido obterCustoPedidoPorId(Integer id) throws ApplicationException;
   
   public UF obterUFPorId(UF uf) throws ApplicationException, UsuarioNaoEncontradoException, ValidacaoException;

   public Cartorio obterCartorioPorId(Integer id) throws ApplicationException;

   public Cartorio obterCartorioPorUsuarioCartorio(UsuarioCartorio usuarioCartorio) throws ApplicationException;

   public Correios obterCorreiosPorId(Integer id) throws ApplicationException;

   public List<Cartorio> obterCartorios() throws ApplicationException;

   public List<OrgaoEmissor> obterOrgaoEmissores() throws ApplicationException;

   public List<Ocupacao> obterOcupacoes() throws ApplicationException;

   public List<Cartorio> obterCartoriosPorMunicipio(String s) throws ApplicationException;

   public Cidade obterCidadePorId(Integer id) throws ApplicationException;

   public Cidade obterCidadePorId(Cidade cidade) throws ApplicationException, UsuarioNaoEncontradoException, ValidacaoException;

   public PermissaoAcesso obterPermissaoAcessoPorId(PermissaoAcesso permissaoAcesso) throws ApplicationException, ValidacaoException;

   public List<UsuarioAdministradorModuloPerfil> obterListaPerfilPorUsuario(UsuarioCartorio usuarioCartorio) throws ApplicationException;

   public UsuarioAdministradorModuloPerfil obterUsuarioCartorioModuloPerfilPorId(UsuarioAdministradorModuloPerfil usuarioCartorioModuloPerfil) throws ApplicationException;

   public List<Perfil> obterListaPerfilPorModulo(Modulo modulo) throws ApplicationException;

   public UsuarioAdministradorModuloPerfil obterUsuarioCartorioModuloPerfilPorIdUsuarioIdPerfil(Integer idUsuario, Integer idPerfil) throws ApplicationException;

   public List<Cidade> obterCidades() throws ApplicationException;

   public List<Cartorio> obterCidadesCartorioTO(String s) throws ApplicationException;

   public List<Cidade> obterCidadesBrasil(String s) throws ApplicationException;

   public List<Cidade> obterCidadesPorUF(UF uf) throws ApplicationException;

   public List<IbgeUF> obterIbgeUFPorNomeCidade(String cidade) throws ApplicationException;

   public List<IbgeUF> obterMunicipiosPorNomeUF(String cidade, UF ufSelecionada) throws ApplicationException;

   public List<IbgeUF> obterIbgeUFPorUF(UF ufSelecionada) throws ApplicationException;

   public List<Pais> obterPaisPorNomeCidade(String cidade) throws ApplicationException;

   public List<UF> obterUFs() throws ApplicationException;

   public List<Local> obterLocais() throws ApplicationException;

   public List<RegistroNascimento> obterRegistrosNascimentoUsuarioCartorio(UsuarioCartorio usuarioCartorio);

}
