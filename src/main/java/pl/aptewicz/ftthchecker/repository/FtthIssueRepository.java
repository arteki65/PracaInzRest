package pl.aptewicz.ftthchecker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.aptewicz.ftthchecker.domain.FtthIssue;

public interface FtthIssueRepository extends JpaRepository<FtthIssue, Long> {
}
