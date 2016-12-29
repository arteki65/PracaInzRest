package pl.aptewicz.ftthchecker.contorller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pl.aptewicz.ftthchecker.dto.FtthIssueDto;
import pl.aptewicz.ftthchecker.service.FtthIssueService;

@RestController
@RequestMapping("/ftthIssue")
public class FtthIssueController {

	private final FtthIssueService ftthIssueService;

	@Autowired
	public FtthIssueController(FtthIssueService ftthIssueService) {
		this.ftthIssueService = ftthIssueService;
	}

	@RequestMapping(method = RequestMethod.PUT)
	public ResponseEntity<FtthIssueDto> createFtthIssue(@RequestBody FtthIssueDto ftthIssueDto,
			@AuthenticationPrincipal User user) {
		return new ResponseEntity<>(ftthIssueService.saveFtthIssue(ftthIssueDto, user), HttpStatus.CREATED);
	}
}
