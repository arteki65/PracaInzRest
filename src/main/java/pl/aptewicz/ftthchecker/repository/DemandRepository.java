package pl.aptewicz.ftthchecker.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.aptewicz.ftthchecker.domain.Demand;
import pl.aptewicz.ftthchecker.domain.Node;

public interface DemandRepository extends JpaRepository<Demand, Long> {
	public Demand findByNode(Node node);
}
