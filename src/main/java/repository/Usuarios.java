package repository;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import filter.UsuarioFilter;
import model.Usuario;

@Stateless
public class Usuarios implements Serializable {

	private static final long serialVersionUID = 1L;

	@PersistenceContext(unitName = "safiraPU")
	private Session session;
	


	public Usuario guardar(Usuario usuario) {
		//Session session = em.unwrap(Session.class);
		Usuario usu = new Usuario();
		session.getTransaction().begin();
		usu = (Usuario) session.merge(usuario);
		session.getTransaction().commit();

		return usu;
	}

	@SuppressWarnings("unchecked")
	public List<Usuario> filtrados(UsuarioFilter filtro) {
		//Session session = em.unwrap(Session.class);
		Criteria criteria = session.createCriteria(Usuario.class)
				.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		if (StringUtils.isNotBlank(filtro.getNome())) {
			criteria.add(Restrictions.ilike("nome", filtro.getNome(), MatchMode.ANYWHERE))
			;
		}
		return criteria.addOrder(Order.asc("nome")).list();
	}

	public Usuario porId(Long id) {
		//Session session = em.unwrap(Session.class);
		return (Usuario) session.get(Usuario.class, id);
	}

	public void remover(Usuario usuario) {

		System.out.println("remover usu " + usuario);
		Usuario usu = null;
		usu = session.get(Usuario.class, usuario.getId());
		session.delete(usu);
	}

	public Usuario porEmail(String email) {
		System.out.println("usuarios por email " + email);
		Usuario usuario = null;
		//Session session = em.unwrap(Session.class);
		try {

			usuario = (Usuario) session.createQuery("from Usuario where lower(email) = :email")
					.setParameter("email", email.toLowerCase()).uniqueResult();
		} catch (NoResultException e) {
			System.out.println(e.getMessage());
		}
		return usuario;
	}

	@SuppressWarnings("unchecked")
	public List<Usuario> Usuarios() {
		//Session session = em.unwrap(Session.class);
		return session.createQuery("from Usuario").list();
	}

}
