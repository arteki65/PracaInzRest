package pl.aptewicz.ftthchecker.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.aptewicz.ftthchecker.domain.Edge;
import pl.aptewicz.ftthchecker.repository.EdgeRepository;

@Service
public class EdgeServiceImpl implements EdgeServiceInterface {

	private EdgeRepository edgeRepository;

	@Autowired
	public EdgeServiceImpl(EdgeRepository edgeRepository) {
		this.edgeRepository = edgeRepository;
	}

	@Override
	public List<Edge> findEdgesInArea(double x1, double y1, double x2,
			double y2, long zoom) {
		List<Edge> edges = new ArrayList<>();

		edges.addAll(
				edgeRepository.findEdgesInAreaWithDemand(x1, y1, x2, y2, zoom));
		edges.addAll(edgeRepository.findEdgesInAreaWithoutDemand(x1, y1, x2, y2,
				zoom));

		return edges;
	}

	@Override
	public Edge findEdgeByName(Long name) {
		return edgeRepository.findOne(name);
	}

}
