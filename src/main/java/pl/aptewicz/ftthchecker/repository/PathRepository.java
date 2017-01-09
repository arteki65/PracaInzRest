package pl.aptewicz.ftthchecker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.aptewicz.ftthchecker.domain.Edge;
import pl.aptewicz.ftthchecker.domain.PathEdge;

public interface PathRepository extends JpaRepository<PathEdge, Long> {

	PathEdge findByEdgeAndSinkSiteLike(Edge edge, String sinkSiteLike);
}
