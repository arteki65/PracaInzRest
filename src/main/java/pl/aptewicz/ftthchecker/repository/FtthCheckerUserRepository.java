package pl.aptewicz.ftthchecker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.aptewicz.ftthchecker.domain.FtthCheckerUser;
import pl.aptewicz.ftthchecker.domain.FtthCheckerUserRole;

import java.util.Collection;

public interface FtthCheckerUserRepository extends JpaRepository<FtthCheckerUser, Long> {

	FtthCheckerUser findByUsername(String username);

	Collection<FtthCheckerUser> findByFtthUserRole(FtthCheckerUserRole role);
}
