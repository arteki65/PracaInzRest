package pl.aptewicz.ftthchecker.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;

import pl.aptewicz.ftthchecker.domain.Edge;
import pl.aptewicz.ftthchecker.domain.ZoomMapping;

public class EdgeRepositoryImpl implements EdgesInAreaRepository {

	private static final String EDGES_IN_AREA_QUERY = "Edge.findEdgesInArea";

	@PersistenceContext
	private EntityManager em;

	@Autowired
	private ZoomMappingRepository zoomMappingRepository;

	@Transactional
	@SuppressWarnings("unchecked")
	@Override
	public List<Edge> findEdgesInArea(double x1, double y1, double x2,
			double y2, long zoom) {
		ZoomMapping zoomMapping = zoomMappingRepository.findByZoom(zoom);

		Query query = em.createNamedQuery(EDGES_IN_AREA_QUERY);

		query.setParameter("x1", x1);
		query.setParameter("x2", x2);
		query.setParameter("y1", y1);
		query.setParameter("y2", y2);
		query.setParameter("nodeSize", zoomMapping.getNodeSize());

		return query.getResultList();
	}

}
