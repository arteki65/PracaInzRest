package pl.aptewicz.ftthchecker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.aptewicz.ftthchecker.domain.FtthCheckerUser;

public interface FtthCheckerUserRepository extends JpaRepository<FtthCheckerUser, Long> {

	FtthCheckerUser findByUsername(String username);
}
