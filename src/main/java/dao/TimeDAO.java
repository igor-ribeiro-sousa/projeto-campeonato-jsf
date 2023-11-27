package dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import entidade.Time;
import exception.JSFException;
import util.JPAUtilService;

public class TimeDAO {

	public static List<Time> pesquisar() {
		EntityManager entityManager = JPAUtilService.fabricarEntityManager();

		try {
			Query query = entityManager.createQuery("select t from Time t");
			List<Time> resutado = query.getResultList();
			return resutado;

		} catch (Exception e) {
			throw new JSFException("Erro ao pesquisar times: " + e.getMessage());

		} finally {
			if (entityManager != null) {
				entityManager.close();
			}
		}
	}

	public static Time getTime(Integer id) {
		EntityManager entityManager = JPAUtilService.fabricarEntityManager();

		try {
			return entityManager.find(Time.class, id);

		} catch (Exception e) {
			throw new JSFException("Erro ao obter a times: " + e.getMessage());

		} finally {
			if (entityManager != null) {
				entityManager.close();
			}
		}
	}

	public static void salvar(Time time) {
		EntityManager entityManager = JPAUtilService.fabricarEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();

		try {
			transaction.begin();
			salvar(time, entityManager);
			transaction.commit();

		} catch (Exception e) {
			if (transaction != null && transaction.isActive()) {
				transaction.rollback();
			}
			throw new JSFException("Erro ao salvar a time: " + e.getMessage());

		} finally {
			if (entityManager != null) {
				entityManager.close();
			}
		}
	}

	private static void salvar(Time time, EntityManager entityManager) {
		entityManager.persist(time);
	}

	public static void alterar(Time time) {
		EntityManager entityManager = JPAUtilService.fabricarEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();

		try {
			transaction.begin();
			alterar(time, entityManager);
			transaction.commit();

		} catch (Exception e) {
			if (transaction != null && transaction.isActive()) {
				transaction.rollback();
			}
			throw new JSFException("Erro ao alterar a time: " + e.getMessage());

		} finally {
			if (entityManager != null) {
				entityManager.close();
			}
		}
	}

	private static void alterar(Time time, EntityManager entityManager) {
		entityManager.merge(time);
	}

	public static void excluir(Integer id) {
		EntityManager entityManager = JPAUtilService.fabricarEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();
		
		try {
			transaction.begin();
			Time time = entityManager.find(Time.class, id);
			if (time != null) {
                excluir(time, entityManager);
            }
			transaction.commit();

		} catch (Exception e) {
			if (transaction != null && transaction.isActive()) {
				transaction.rollback();
			}
			throw new JSFException("Erro ao excluir a time: " + e.getMessage());

		} finally {
			if (entityManager != null) {
				entityManager.close();
			}
		}
	}

	private static void excluir(Time time, EntityManager entityManager) {
		entityManager.remove(time);
	}
}
