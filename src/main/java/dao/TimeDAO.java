package dao;

import java.util.List;
import java.util.Objects;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import entidade.Time;
import exception.JSFException;
import util.JPAUtilService;

public class TimeDAO {

	public static List<Time> pesquisar() {
		EntityManager entityManager = JPAUtilService.fabricarEntityManager();

		try {
			Query query = entityManager.createQuery("select t from Time t WHERE t.flagAtivo = 'S'");
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

	public static Time pesquisarPorNomeDoTime(String nomeDoTime) {
		EntityManager entityManager = JPAUtilService.fabricarEntityManager();

		try {
			Integer codigoDoTime = obterCodigoPeloNome(nomeDoTime);
			Query query;
			if (Objects.nonNull(codigoDoTime)) {
				return getTime(codigoDoTime);
			}
			return null;

		} catch (Exception e) {
			return null;

		} finally {
			if (entityManager != null) {
				entityManager.close();
			}
		}
	}

	public static Integer obterCodigoPeloNome(String nomeDoTime) {
		EntityManager entityManager = JPAUtilService.fabricarEntityManager();

		try {
			Query query = entityManager
					.createQuery("SELECT t.id FROM Time t WHERE t.nome LIKE :nomeDoTime AND t.flagAtivo = 'S'");

			query.setParameter("nomeDoTime", "%" + nomeDoTime + "%");

			Integer codigoDoTime = (Integer) query.getSingleResult();
			return codigoDoTime;
			
		} catch (NoResultException e) {
			return null;
		} catch (Exception e) {
			return null;
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
