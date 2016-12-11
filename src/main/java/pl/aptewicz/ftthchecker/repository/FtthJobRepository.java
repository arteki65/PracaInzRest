package pl.aptewicz.ftthchecker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.aptewicz.ftthchecker.domain.FtthJob;

public interface FtthJobRepository extends JpaRepository<FtthJob, Long> {

}
