package pl.aptewicz.ftthchecker.async;

import pl.aptewicz.ftthchecker.domain.Edge;
import pl.aptewicz.ftthchecker.domain.FtthJob;
import pl.aptewicz.ftthchecker.repository.FtthJobRepository;
import pl.aptewicz.ftthchecker.service.EdgeServiceInterface;

import java.util.Collection;

public class FindAffectedEdgesAsyncTask implements Runnable {

	private final EdgeServiceInterface edgeService;

	private final double x;

	private final double y;

	private final double delta;

	private final FtthJob ftthJob;

	private final FtthJobRepository ftthJobRepository;

	public FindAffectedEdgesAsyncTask(EdgeServiceInterface edgeService, double x, double y, double delta,
			FtthJob ftthJob, FtthJobRepository ftthJobRepository) {
		this.edgeService = edgeService;
		this.x = x;
		this.y = y;
		this.delta = delta;
		this.ftthJob = ftthJob;
		this.ftthJobRepository = ftthJobRepository;
	}

	@Override
	public void run() {
		Collection<Edge> edgesInArea = edgeService.findEdgesInArea(x - delta, y - delta, x + delta, y + delta);
		ftthJob.setAffectedEdges(edgesInArea);
		ftthJobRepository.save(ftthJob);
	}
}
