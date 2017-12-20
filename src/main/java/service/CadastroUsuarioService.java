package service;

import java.io.Serializable;

import javax.inject.Inject;

import model.Usuario;
import repository.Usuarios;

public class CadastroUsuarioService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Usuarios usuarios;

	public Usuario salvar(Usuario usuario) {
		return usuarios.guardar(usuario);
	}

}
