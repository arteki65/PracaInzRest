package pl.aptewicz.ftthchecker.repository;

import org.springframework.data.repository.CrudRepository;
import pl.aptewicz.ftthchecker.domain.FtthCheckerUser;

public interface FtthCheckerUserRepository extends CrudRepository<FtthCheckerUser, Long> {

	FtthCheckerUser findByUsername(String username);
}
