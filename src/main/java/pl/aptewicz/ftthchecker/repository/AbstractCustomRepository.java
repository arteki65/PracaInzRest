package pl.aptewicz.ftthchecker.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Collection;

abstract class AbstractCustomRepository<T> {

	@PersistenceContext
	EntityManager em;

	@SuppressWarnings("unchecked")
	Collection<T> findInArea(double x1, double y1, double x2, double y2) {
		Query query = em.createNamedQuery(getQueryName());

		query.setParameter("x1", x1);
		query.setParameter("x2", x2);
		query.setParameter("y1", y1);
		query.setParameter("y2", y2);

		return query.getResultList();
	}

	abstract String getQueryName();
}
