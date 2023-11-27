package dao;

import java.util.List;
import java.util.Objects;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import entidade.Jogo;
import exception.JSFException;
import util.JPAUtilService;

public class JogoDAO {

	public static List<Jogo> pesquisar() {
		EntityManager entityManager = JPAUtilService.fabricarEntityManager();

		try {
			Query query = entityManager.createQuery("select j from TBL_JGD j");
			List<Jogo> resutado = query.getResultList();
			return resutado;

		} catch (Exception e) {
			throw new JSFException("Erro ao pesquisar jogadas: " + e.getMessage());

		} finally {
			if (entityManager != null) {
				entityManager.close();
			}
		}
	}

	public static List<Jogo> pesquisarPorNomeDoTime(String nomeDoTime) {
		EntityManager entityManager = JPAUtilService.fabricarEntityManager();

		try {
			Integer codigoDoTime = obterCodigoPeloNome(nomeDoTime);
			Query query;
			if (Objects.nonNull(codigoDoTime)) {
				query = entityManager.createQuery(
						"SELECT j FROM Jogo j WHERE j.time1.id = :codigoDoTime OR j.time2.id = :codigoDoTime");

				query.setParameter("codigoDoTime", codigoDoTime);

				List<Jogo> resultado = query.getResultList();
				return resultado;
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
			Query query = entityManager.createQuery("SELECT t.id FROM Time t WHERE t.nome LIKE :nomeDoTime");

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

	public static Jogo getJogador(Integer id) {
		EntityManager entityManager = JPAUtilService.fabricarEntityManager();

		try {
			return entityManager.find(Jogo.class, id);

		} catch (Exception e) {
			throw new JSFException("Erro ao obter a jogada: " + e.getMessage());

		} finally {
			if (entityManager != null) {
				entityManager.close();
			}
		}
	}

	public static void salvar(Jogo jogada) {
		EntityManager entityManager = JPAUtilService.fabricarEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();

		try {
			transaction.begin();
			salvar(jogada, entityManager);
			transaction.commit();

		} catch (Exception e) {
			if (transaction != null && transaction.isActive()) {
				transaction.rollback();
			}
			throw new JSFException("Erro ao salvar a jogada: " + e.getMessage());

		} finally {
			if (entityManager != null) {
				entityManager.close();
			}
		}
	}

	private static void salvar(Jogo jogada, EntityManager entityManager) {
		entityManager.persist(jogada);
	}

	public static void alterar(Jogo jogada) {
		EntityManager entityManager = JPAUtilService.fabricarEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();

		try {
			transaction.begin();
			alterar(jogada, entityManager);
			transaction.commit();

		} catch (Exception e) {
			if (transaction != null && transaction.isActive()) {
				transaction.rollback();
			}
			throw new JSFException("Erro ao alterar a jogada: " + e.getMessage());

		} finally {
			if (entityManager != null) {
				entityManager.close();
			}
		}
	}

	private static void alterar(Jogo jogada, EntityManager entityManager) {
		entityManager.merge(jogada);
	}

	public static void excluir(Integer id) {
		EntityManager entityManager = JPAUtilService.fabricarEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();

		try {
			transaction.begin();
			Jogo jogada = entityManager.find(Jogo.class, id);
			if (jogada != null) {
				excluir(jogada, entityManager);
			}
			transaction.commit();

		} catch (Exception e) {
			if (transaction != null && transaction.isActive()) {
				transaction.rollback();
			}
			throw new JSFException("Erro ao excluir a jogada: " + e.getMessage());

		} finally {
			if (entityManager != null) {
				entityManager.close();
			}
		}
	}

	private static void excluir(Jogo jogada, EntityManager entityManager) {
		entityManager.remove(jogada);
	}
}
