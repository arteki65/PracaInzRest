package pl.aptewicz.ftthchecker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import pl.aptewicz.ftthchecker.domain.Edge;
import pl.aptewicz.ftthchecker.domain.FtthIssue;
import pl.aptewicz.ftthchecker.domain.FtthJob;
import pl.aptewicz.ftthchecker.domain.FtthJobStatus;
import pl.aptewicz.ftthchecker.dto.FtthIssueDto;
import pl.aptewicz.ftthchecker.repository.FtthCustomerRepository;
import pl.aptewicz.ftthchecker.repository.FtthIssueRepository;
import pl.aptewicz.ftthchecker.repository.FtthJobRepository;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class FtthIssueServiceImpl implements FtthIssueService {

	private static final double DELTA = 0.0003;

	private final FtthCustomerRepository ftthCustomerRepository;

	private final FtthIssueRepository ftthIssueRepository;

	private final FtthJobRepository ftthJobRepository;

	private final EdgeServiceInterface edgeService;

	@Autowired
	public FtthIssueServiceImpl(FtthCustomerRepository ftthCustomerRepository, FtthIssueRepository ftthIssueRepository,
			FtthJobRepository ftthJobRepository, EdgeServiceInterface edgeService) {
		this.ftthCustomerRepository = ftthCustomerRepository;
		this.ftthIssueRepository = ftthIssueRepository;
		this.ftthJobRepository = ftthJobRepository;
		this.edgeService = edgeService;
	}

	@Override
	public FtthIssueDto saveFtthIssue(FtthIssueDto ftthIssueDto, User user) {
		FtthIssue ftthIssue = new FtthIssue(ftthIssueDto);
		ftthIssue.setFtthCustomer(ftthCustomerRepository.findByUsername(user.getUsername()));

		double y = ftthIssue.getLatitude();
		double x = ftthIssue.getLongitude();

		Collection<Edge> edgesInAres = edgeService.findEdgesInArea(x - DELTA, y - DELTA, x + DELTA, y + DELTA);

		Collection<Edge> affectedEdges = edgesInAres.stream().filter(edge -> {
			double nodeAX = edge.getNodeA().getX();
			double nodeAY = edge.getNodeA().getY();
			double nodeBX = edge.getNodeB().getX();
			double nodeBY = edge.getNodeB().getY();

			return checkIfPointIsInCircle(nodeAX, nodeAY, x, y) || checkIfPointIsInCircle(nodeBX, nodeBY, x, y);
		}).collect(Collectors.toList());

		FtthJob ftthJob = new FtthJob();
		ftthJob.setDescription(ftthIssue.getDescription());
		ftthJob.setFtthIssue(ftthIssue);
		ftthJob.setJobStatus(FtthJobStatus.NEW);
		ftthJob.setAffectedEdges(affectedEdges);

		ftthIssue.setFtthJob(ftthJobRepository.save(ftthJob));
		return new FtthIssueDto(ftthIssueRepository.save(ftthIssue));
	}

	private boolean checkIfPointIsInCircle(double x, double y, double centerX, double centerY) {
		return (Math.pow((x - centerX), 2) + Math.pow((y - centerY), 2)) < Math.pow(DELTA, 2);
	}
}
