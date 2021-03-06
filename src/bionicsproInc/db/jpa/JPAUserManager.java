package bionicsproInc.db.jpa;

import java.security.MessageDigest;

import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;

import bionicsproInc.db.ifaces.UserManager;
import bionicsproInc.db.pojos.Engineer;
import bionicsproInc.db.pojos.users.Role;
import bionicsproInc.db.pojos.users.User;

public class JPAUserManager implements UserManager {

	private EntityManager em;

	@Override
	public void connect() {
		em = Persistence.createEntityManagerFactory("user-provider").createEntityManager();
		em.getTransaction().begin();
		em.createNativeQuery("PRAGMA foreign_keys=ON").executeUpdate();
		em.getTransaction().commit();
		List<Role> existingRoles = this.getRoles();
		if (existingRoles.size() < 2) {

			this.newRole(new Role("engineer"));
			this.newRole(new Role("customer"));

		}
	}

	@Override
	public void disconnect() {
		em.close();
	}

	@Override
	public void newUser(User u) {
		em.getTransaction().begin();
		em.persist(u);
		em.getTransaction().commit();
	}

	@Override
	public void newRole(Role r) {
		em.getTransaction().begin();
		em.persist(r);
		em.getTransaction().commit();
	}

	@Override
	public Role getRole(int id) {
		Query q = em.createNativeQuery("SELECT * FROM roles WHERE id = ?", Role.class);
		q.setParameter(1, id);
		return (Role) q.getSingleResult();

	}

	@Override
	public List<Role> getRoles() {
		Query q = em.createNativeQuery("SELECT * FROM roles", Role.class);
		return (List<Role>) q.getResultList();
	}

	@Override
	public User checkPassword(String email, String password) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(password.getBytes());
			byte[] hash = md.digest();
			Query q = em.createNativeQuery("SELECT * FROM users WHERE email = ? AND password = ?", User.class);
			q.setParameter(1, email);
			q.setParameter(2, hash);
			return (User) q.getSingleResult();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoResultException nre) {
			return null;
		}
		return null;
	}

	public void quitEngineer(String email) {
		Query q = em.createNativeQuery(" SELECT * FROM users WHERE email = ?", User.class);
		q.setParameter(1, email);
		User nini = (User) q.getSingleResult();
		em.getTransaction().begin();
		em.remove(nini);
		em.getTransaction().commit();
	}

	public User updateEngineerPassword(String email, byte[] newPass) {
		Query q = em.createNativeQuery(" SELECT * FROM users WHERE email = ?", User.class);
		q.setParameter(1, email);
		q.setParameter(2, newPass);
		User us = (User) q.getSingleResult();
		em.getTransaction().begin();
		us.setPassword(newPass);
		em.getTransaction().commit();
		return (User) q.getSingleResult();

	}

}
