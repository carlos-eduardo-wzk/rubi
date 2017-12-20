package security;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.faces.context.FacesContext;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import model.Grupo;
import model.Usuario;
import repository.Usuarios;
import util.cdi.CDIServiceLocator;


public class AppUserDetailsService implements UserDetailsService, Serializable {

	private static final long serialVersionUID = 1L;

	public AppUserDetailsService() {
		System.out.println("const AppUserDetailsServic ");

	}

	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		System.out.println("loadUserByUsername :" + email);
		
		Usuarios usuarios = CDIServiceLocator.getBean(Usuarios.class);
	
		
		Usuario usuario = findEmail(email);
		
		UsuarioSistema user = null;
		
		if (usuario != null) {
			user = new UsuarioSistema(usuario, getGrupos(usuario));
		} else {
			throw new UsernameNotFoundException("Usuário não encontrado.");
		}
		return user;
	}

	private Collection<? extends GrantedAuthority> getGrupos(Usuario usuario) {
		List<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();

		for (Grupo grupo : usuario.getGrupos()) {
			authorities.add(new SimpleGrantedAuthority(grupo.getNome().toUpperCase()));
			System.out.println(grupo.getNome().toUpperCase());
		}

		return authorities;
	}

	public Usuario findEmail(String email) {

		String username = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
				.get("username");
		String Schema2 = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(username);

		System.out.println("=========>>>>" + Schema2);
		System.out.println("Antes do session factory am appUser");
		System.out.println("findEmail com criteria " + email);

		Usuarios usuarios = CDIServiceLocator.getBean(Usuarios.class);
		Usuario usuario = usuarios.porEmail(email);
		

		System.out.println("=========>" + usuario.getNome());

		return usuario;
	}

}