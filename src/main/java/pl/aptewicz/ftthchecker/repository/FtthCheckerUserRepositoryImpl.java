package pl.aptewicz.ftthchecker.repository;

import pl.aptewicz.ftthchecker.domain.FtthCheckerUser;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

public class FtthCheckerUserRepositoryImpl implements FtthCheckerUserInAreaRepository {

	private static final String USERS_IN_AREA_QUERY = "FtthCheckerUser.findUsersInArea";

	@PersistenceContext
	private EntityManager em;

	@Override
	public List<FtthCheckerUser> findUsersInArea(double x1, double y1, double x2, double y2) {
		Query query = em.createNamedQuery(USERS_IN_AREA_QUERY);

		query.setParameter("x1", x1);
		query.setParameter("x2", x2);
		query.setParameter("y1", y1);
		query.setParameter("y2", y2);

		return query.getResultList();
	}
}
