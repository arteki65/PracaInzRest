package pl.aptewicz.ftthchecker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import pl.aptewicz.ftthchecker.async.FindAffectedEdgesAsyncTask;
import pl.aptewicz.ftthchecker.async.FindFtthCheckerUserForJobAsyncTask;
import pl.aptewicz.ftthchecker.domain.FtthIssue;
import pl.aptewicz.ftthchecker.domain.FtthJob;
import pl.aptewicz.ftthchecker.domain.FtthJobStatus;
import pl.aptewicz.ftthchecker.dto.FtthIssueDto;
import pl.aptewicz.ftthchecker.repository.FtthCheckerUserRepository;
import pl.aptewicz.ftthchecker.repository.FtthCustomerRepository;
import pl.aptewicz.ftthchecker.repository.FtthIssueRepository;
import pl.aptewicz.ftthchecker.repository.FtthJobRepository;

@Service
public class FtthIssueServiceImpl implements FtthIssueService {

	private static final double DELTA = 0.0003;

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
		ftthIssueRepository.save(ftthIssue);

		double y = ftthIssue.getLatitude();
		double x = ftthIssue.getLongitude();

		FtthJob ftthJob = new FtthJob();
		ftthJob.setDescription(ftthIssue.getDescription());
		ftthJob.setFtthIssue(ftthIssue);
		ftthJob.setJobStatus(FtthJobStatus.NEW);

		ftthIssue.setFtthJob(ftthJobRepository.save(ftthJob));

		FindAffectedEdgesAsyncTask findAffectedEdgesAsyncTask = new FindAffectedEdgesAsyncTask(edgeService, x, y, DELTA,
				ftthJob, ftthJobRepository);
		new Thread(findAffectedEdgesAsyncTask, "FindAffectedEdgesAsyncTask").start();

		FindFtthCheckerUserForJobAsyncTask findFtthCheckerUserForJobAsyncTask = new FindFtthCheckerUserForJobAsyncTask(
				ftthCheckerUserRepository, DELTA, routeService, x, y, ftthJob, ftthJobRepository);
		new Thread(findFtthCheckerUserForJobAsyncTask, "FindFtthCheckerUserForJobAsyncTask").start();

		return new FtthIssueDto(ftthIssue);
	}
}
