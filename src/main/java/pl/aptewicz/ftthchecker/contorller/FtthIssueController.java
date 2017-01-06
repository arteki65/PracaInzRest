package pl.aptewicz.ftthchecker.contorller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;
import pl.aptewicz.ftthchecker.domain.FtthCheckerUser;
import pl.aptewicz.ftthchecker.domain.FtthJob;
import pl.aptewicz.ftthchecker.dto.FtthIssueDto;
import pl.aptewicz.ftthchecker.repository.FtthCheckerUserRepository;
import pl.aptewicz.ftthchecker.repository.FtthJobRepository;
import pl.aptewicz.ftthchecker.service.FtthIssueService;

import java.util.ArrayList;
import java.util.Collection;

@RestController
@RequestMapping("/ftthIssue")
public class FtthIssueController {

	private final FtthIssueService ftthIssueService;

	private final FtthCheckerUserRepository ftthCheckerUserRepository;

	private final FtthJobRepository ftthJobRepository;

	@Autowired
	public FtthIssueController(FtthIssueService ftthIssueService, FtthCheckerUserRepository ftthCheckerUserRepository,
			FtthJobRepository ftthJobRepository) {
		this.ftthIssueService = ftthIssueService;
		this.ftthCheckerUserRepository = ftthCheckerUserRepository;
		this.ftthJobRepository = ftthJobRepository;
	}

	@RequestMapping(method = RequestMethod.PUT)
	public ResponseEntity<FtthIssueDto> createFtthIssue(@RequestBody FtthIssueDto ftthIssueDto,
			@AuthenticationPrincipal User user) {
		return new ResponseEntity<>(ftthIssueService.saveFtthIssue(ftthIssueDto, user), HttpStatus.CREATED);
	}

	@RequestMapping(method = RequestMethod.GET, path = "/{servicemanUsername}")
	public ResponseEntity<Collection<FtthIssueDto>> findIssuesForServiceman(@PathVariable String servicemanUsername) {
		FtthCheckerUser serviceman = ftthCheckerUserRepository.findByUsername(servicemanUsername);
		Collection<FtthJob> ftthJobs = ftthJobRepository.findByFtthCheckerUser(serviceman);
		Collection<FtthIssueDto> ftthIssues = new ArrayList<>();
		ftthJobs.forEach(ftthJob -> ftthIssues.add(new FtthIssueDto(ftthJob.getFtthIssue())));
		return new ResponseEntity<>(ftthIssues, HttpStatus.OK);
	}
}
