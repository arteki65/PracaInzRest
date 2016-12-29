package pl.aptewicz.ftthchecker.service;

import org.springframework.security.core.userdetails.User;
import pl.aptewicz.ftthchecker.dto.FtthIssueDto;

public interface FtthIssueService {

	FtthIssueDto saveFtthIssue(FtthIssueDto ftthIssueDto, User user);
}
