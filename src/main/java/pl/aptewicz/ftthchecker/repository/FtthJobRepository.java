package pl.aptewicz.ftthchecker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.aptewicz.ftthchecker.domain.FtthCheckerUser;
import pl.aptewicz.ftthchecker.domain.FtthJob;

import java.util.Collection;

public interface FtthJobRepository extends JpaRepository<FtthJob, Long> {

	Collection<FtthJob> findByFtthCheckerUser(FtthCheckerUser ftthCheckerUser);
}
