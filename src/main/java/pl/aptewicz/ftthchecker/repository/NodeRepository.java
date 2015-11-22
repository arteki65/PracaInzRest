package pl.aptewicz.ftthchecker.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.aptewicz.ftthchecker.domain.Node;

public interface NodeRepository extends JpaRepository<Node, Long> {

}
