package pl.aptewicz.ftthchecker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.aptewicz.ftthchecker.domain.FtthCustomer;

public interface FtthCustomerRepository extends JpaRepository<FtthCustomer, Long> {

	FtthCustomer findByUsername(String username);
}
