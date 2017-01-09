package pl.aptewicz.ftthchecker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import pl.aptewicz.ftthchecker.async.FindFtthCheckerUserForJobAsyncTask;
import pl.aptewicz.ftthchecker.domain.AccessPoint;
import pl.aptewicz.ftthchecker.domain.FtthIssue;
import pl.aptewicz.ftthchecker.domain.FtthJob;
import pl.aptewicz.ftthchecker.domain.FtthJobStatus;
import pl.aptewicz.ftthchecker.dto.FtthIssueDto;
import pl.aptewicz.ftthchecker.exception.FtthRestApiException;
import pl.aptewicz.ftthchecker.exception.FtthRestApiExceptionConstants;
import pl.aptewicz.ftthchecker.repository.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class FtthIssueServiceImpl implements FtthIssueService {

	private static final double DELTA = 0.0003;

	private final FtthCustomerRepository ftthCustomerRepository;

	private final FtthIssueRepository ftthIssueRepository;

	private final FtthJobRepository ftthJobRepository;

	private final FtthCheckerUserRepository ftthCheckerUserRepository;

	private final RouteService routeService;

	private final AccessPointRepository accessPointRepository;

	private final DistanceService distanceService;

	@Autowired
	public FtthIssueServiceImpl(FtthCustomerRepository ftthCustomerRepository, FtthIssueRepository ftthIssueRepository,
			FtthJobRepository ftthJobRepository, FtthCheckerUserRepository ftthCheckerUserRepository,
			RouteService routeService, AccessPointRepository accessPointRepository, DistanceService distanceService) {
		this.ftthCustomerRepository = ftthCustomerRepository;
		this.ftthIssueRepository = ftthIssueRepository;
		this.ftthJobRepository = ftthJobRepository;
		this.ftthCheckerUserRepository = ftthCheckerUserRepository;
		this.routeService = routeService;
		this.accessPointRepository = accessPointRepository;
		this.distanceService = distanceService;
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
		Collection<AccessPoint> accessPointsInArea = accessPointRepository
				.findAccessPointsInArea(issueX - delta, issueY - delta, issueX + delta, issueY + delta);
		int attempts = 0;
		while (accessPointsInArea.isEmpty() && attempts < 10) {
			attempts++;
			delta = delta + DELTA;
			accessPointsInArea = accessPointRepository
					.findAccessPointsInArea(issueX - delta, issueY - delta, issueX + delta, issueY + delta);
		}

		if (!accessPointsInArea.isEmpty()) {
			accessPointsInArea = findNearestAccessPoint(accessPointsInArea, issueX, issueY);
			ftthIssueRepository.save(ftthIssue);
			ftthJob.setAffectedAccessPoints(accessPointsInArea);
			ftthIssue.setFtthJob(ftthJobRepository.save(ftthJob));
			ftthIssueRepository.save(ftthIssue);

			FindFtthCheckerUserForJobAsyncTask findFtthCheckerUserForJobAsyncTask = new FindFtthCheckerUserForJobAsyncTask(
					ftthCheckerUserRepository, DELTA, routeService, issueX, issueY, ftthJob, ftthJobRepository);
			new Thread(findFtthCheckerUserForJobAsyncTask, "FindFtthCheckerUserForJobAsyncTask").start();

			return new FtthIssueDto(ftthIssue);
		}

		throw new FtthRestApiException(FtthRestApiExceptionConstants.NO_EDGES_NEAR_ISSUE_LOCATION_FOUND);
	}

	private Collection<AccessPoint> findNearestAccessPoint(Collection<AccessPoint> accessPointsInArea, double issueX,
			double issueY) {
		List<AccessPoint> accessPoints = new ArrayList<>();
		double minDistance = Double.MAX_VALUE;

		for(AccessPoint accessPoint : accessPointsInArea) {
			double distanceFromIssueLocation = distanceService.getDistance(accessPoint.getNode().getY(), accessPoint
					.getNode().getX
					(), issueY, issueX);
			if(distanceFromIssueLocation < minDistance) {
				minDistance = distanceFromIssueLocation;
				if(accessPoints.size() == 1) {
					accessPoints.remove(0);
				}
				accessPoints.add(0, accessPoint);
			}
		}

		return accessPoints;
	}
}
