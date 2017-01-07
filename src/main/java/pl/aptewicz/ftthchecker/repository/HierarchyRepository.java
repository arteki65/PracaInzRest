package pl.aptewicz.ftthchecker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.aptewicz.ftthchecker.domain.Hierarchy;

public interface HierarchyRepository extends JpaRepository<Hierarchy, Long> {

	Hierarchy findByAccessSiteLike(String accessSite);
}
