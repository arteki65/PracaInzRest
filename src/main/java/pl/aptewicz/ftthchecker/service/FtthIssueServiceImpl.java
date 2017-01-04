package pl.aptewicz.ftthchecker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import pl.aptewicz.ftthchecker.async.FindFtthCheckerUserForJobAsyncTask;
import pl.aptewicz.ftthchecker.domain.Edge;
import pl.aptewicz.ftthchecker.domain.FtthIssue;
import pl.aptewicz.ftthchecker.domain.FtthJob;
import pl.aptewicz.ftthchecker.domain.FtthJobStatus;
import pl.aptewicz.ftthchecker.dto.FtthIssueDto;
import pl.aptewicz.ftthchecker.exception.FtthRestApiException;
import pl.aptewicz.ftthchecker.exception.FtthRestApiExceptionConstants;
import pl.aptewicz.ftthchecker.repository.FtthCheckerUserRepository;
import pl.aptewicz.ftthchecker.repository.FtthCustomerRepository;
import pl.aptewicz.ftthchecker.repository.FtthIssueRepository;
import pl.aptewicz.ftthchecker.repository.FtthJobRepository;

import java.util.Collection;

@Service
public class FtthIssueServiceImpl implements FtthIssueService {

	private static final double DELTA = 0.0009;

	private final FtthCustomerRepository ftthCustomerRepository;

	private final FtthIssueRepository ftthIssueRepository;

	private final FtthJobRepository ftthJobRepository;

	private final FtthCheckerUserRepository ftthCheckerUserRepository;

	private final EdgeServiceInterface edgeService;

	private final RouteService routeService;

	@Autowired
	public FtthIssueServiceImpl(FtthCustomerRepository ftthCustomerRepository, FtthIssueRepository ftthIssueRepository,
			FtthJobRepository ftthJobRepository, FtthCheckerUserRepository ftthCheckerUserRepository,
			EdgeServiceInterface edgeService, RouteService routeService) {
		this.ftthCustomerRepository = ftthCustomerRepository;
		this.ftthIssueRepository = ftthIssueRepository;
		this.ftthJobRepository = ftthJobRepository;
		this.ftthCheckerUserRepository = ftthCheckerUserRepository;
		this.edgeService = edgeService;
		this.routeService = routeService;
	}

	@Override
	public FtthIssueDto saveFtthIssue(FtthIssueDto ftthIssueDto, User user) {
		FtthIssue ftthIssue = new FtthIssue(ftthIssueDto);
		ftthIssue.setFtthCustomer(ftthCustomerRepository.findByUsername(user.getUsername()));

		double issueY = ftthIssue.getLatitude();
		double issueX = ftthIssue.getLongitude();

		FtthJob ftthJob = new FtthJob();
		ftthJob.setDescription(ftthIssue.getDescription());
		ftthJob.setFtthIssue(ftthIssue);
		ftthJob.setJobStatus(FtthJobStatus.NEW);

		double delta = DELTA;
		Collection<Edge> edgesInArea = edgeService
				.findEdgesInArea(issueX - delta, issueY - delta, issueX + delta, issueY + delta);
		int attempts = 0;
		while (edgesInArea.isEmpty() && attempts < 10) {
			attempts++;
			delta = delta + DELTA;
			edgesInArea = edgeService.findEdgesInArea(issueX - delta, issueY - delta, issueX + delta, issueY + delta);
		}

		if(!edgesInArea.isEmpty()) {
			ftthIssueRepository.save(ftthIssue);
			ftthJob.setAffectedEdges(edgesInArea);
			ftthIssue.setFtthJob(ftthJobRepository.save(ftthJob));
			ftthIssueRepository.save(ftthIssue);

			FindFtthCheckerUserForJobAsyncTask findFtthCheckerUserForJobAsyncTask = new FindFtthCheckerUserForJobAsyncTask(
					ftthCheckerUserRepository, DELTA, routeService, issueX, issueY, ftthJob, ftthJobRepository);
			new Thread(findFtthCheckerUserForJobAsyncTask, "FindFtthCheckerUserForJobAsyncTask").start();

			return new FtthIssueDto(ftthIssue);
		}

		throw new FtthRestApiException(FtthRestApiExceptionConstants.NO_EDGES_NEAR_ISSUE_LOCATION_FOUND);
	}
}
