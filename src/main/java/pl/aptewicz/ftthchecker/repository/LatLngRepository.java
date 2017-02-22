package pl.aptewicz.ftthchecker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.aptewicz.ftthchecker.domain.LatLng;

public interface LatLngRepository extends JpaRepository<LatLng, Long> {

}
