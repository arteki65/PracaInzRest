package pl.aptewicz.ftthchecker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.aptewicz.ftthchecker.domain.Site;

public interface SiteRepository extends JpaRepository<Site, Long> {

	Site findByName(String name);

	Site findByNameLike(String name);
}
