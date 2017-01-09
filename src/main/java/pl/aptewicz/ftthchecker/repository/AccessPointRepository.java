package pl.aptewicz.ftthchecker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.aptewicz.ftthchecker.domain.AccessPoint;

public interface AccessPointRepository extends JpaRepository<AccessPoint, Long>, AccessPointInAreaRepository {

}
