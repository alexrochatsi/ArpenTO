
package br.com.datasind.controller.sca;

import java.io.IOException;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.primefaces.model.menu.MenuModel;

import br.com.datasind.controller.CadastroControler;
import br.com.datasind.entidade.Modulo;
import br.com.datasind.entidade.Usuario;
import br.com.datasind.entidade.UsuarioSiteModuloPerfil;

/**
 * @Author osmar
 * @since 08/06/2011
 *
 **/
@ManagedBean(name="menu")
@RequestScoped
public class MenuController extends CadastroControler{

   /**
    *
    */
   private static final long serialVersionUID=1828861351437965739L;

   private Usuario usuario;

   private Modulo modulo;

   private UsuarioSiteModuloPerfil usuarioModuloPerfil;

   private MenuModel menuModel;

   public Usuario getUsuario() {
	  if(usuario == null) {
		 usuario=getUsuarioSessao();
	  }
	  return usuario;
   }

   public void setUsuario(Usuario usuario) {
	  this.usuario=usuario;
   }

   public Modulo getModulo() {
	  if(modulo == null) {
		 modulo=getModuloAtual();
	  }
	  return modulo;
   }

   public void setModulo(Modulo modulo) {
	  this.modulo=modulo;
   }

   public MenuModel getMenuModel() {
	  menuModel.generateUniqueIds();
	  return menuModel;
   }

   public void setMenuModel(MenuModel menuModel) {
	  this.menuModel=menuModel;
   }

   public UsuarioSiteModuloPerfil getUsuarioModuloPerfil() {
	  if(usuarioModuloPerfil == null) {
		 usuarioModuloPerfil=new UsuarioSiteModuloPerfil();
	  }
	  return usuarioModuloPerfil;
   }

   public void setUsuarioModuloPerfil(UsuarioSiteModuloPerfil usuarioModuloPerfil) {
	  this.usuarioModuloPerfil=usuarioModuloPerfil;
   }

   public String getTeste() {
	  String teste="Isso e teste";
	  return teste;
   }

   public String encerraSessao() {
	  HttpSession session=(HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);

	  if(session != null) {
		 session.invalidate();
		 session=null;
	  }
	  try {
		 FacesContext.getCurrentInstance().getExternalContext().redirect("../index.jsp");
	  } catch (IOException e) {
		 e.printStackTrace();
	  }
	  return "login";
   }

   public String alterarSenha() {
	  HttpSession session=(HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
	  try {
		 if(session == null) {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../index.jsp");
			session=null;
		 } else {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../portal/alterarSenha.jsf");
		 }

	  } catch (IOException e) {
		 e.printStackTrace();
	  }
	  return "login";
   }

   public String portal() {
	  HttpSession session=(HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
	  try {
		 if(session == null) {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../index.jsp");
			session=null;
		 } else {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../portal/portal.jsf");
		 }

	  } catch (IOException e) {
		 e.printStackTrace();
	  }
	  return "login";
   }
}
