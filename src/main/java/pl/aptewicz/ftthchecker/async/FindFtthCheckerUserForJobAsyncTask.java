package pl.aptewicz.ftthchecker.async;

import pl.aptewicz.ftthchecker.domain.FtthCheckerUser;
import pl.aptewicz.ftthchecker.domain.FtthJob;
import pl.aptewicz.ftthchecker.repository.FtthCheckerUserInAreaRepository;
import pl.aptewicz.ftthchecker.repository.FtthJobRepository;
import pl.aptewicz.ftthchecker.service.RouteService;

import java.util.List;

public class FindFtthCheckerUserForJobAsyncTask implements Runnable {

	private final FtthCheckerUserInAreaRepository ftthCheckerUserRepository;

	private final double delta;

	private final RouteService routeService;

	private final double x;

	private final double y;

	private final FtthJob ftthJob;

	private final FtthJobRepository ftthJobRepository;

	public FindFtthCheckerUserForJobAsyncTask(FtthCheckerUserInAreaRepository ftthCheckerUserRepository, double delta,
			RouteService routeService, double x, double y, FtthJob ftthJob, FtthJobRepository ftthJobRepository) {
		this.ftthCheckerUserRepository = ftthCheckerUserRepository;
		this.delta = delta;
		this.routeService = routeService;
		this.x = x;
		this.y = y;
		this.ftthJob = ftthJob;
		this.ftthJobRepository = ftthJobRepository;
	}

	@Override
	public void run() {
		List<FtthCheckerUser> usersInArea = ftthCheckerUserRepository
				.findUsersInArea(x - delta, y - delta, x + delta, y + delta);

		double newDelta = delta;
		while (usersInArea.isEmpty()) {
			newDelta = newDelta + 0.0003;
			usersInArea = ftthCheckerUserRepository
					.findUsersInArea(x - newDelta, y - newDelta, x + newDelta, y + newDelta);
		}

		FtthCheckerUser ftthCheckerUserForJob = null;
		int duration = Integer.MAX_VALUE;
		for (FtthCheckerUser ftthCheckerUser : usersInArea) {
			int tmpDuration = routeService.findDurationInSeconds(String.valueOf(y) + "," + String.valueOf(x),
					String.valueOf(ftthCheckerUser.getLastPosition().getLatitude()) + "," + String
							.valueOf(ftthCheckerUser.getLastPosition().getLongitude()));
			if (tmpDuration < duration) {
				duration = tmpDuration;
				ftthCheckerUserForJob = ftthCheckerUser;
			}
		}
		ftthJob.setFtthCheckerUser(ftthCheckerUserForJob);

		ftthJobRepository.save(ftthJob);
	}
}
