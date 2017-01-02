package pl.aptewicz.ftthchecker.service;

import pl.aptewicz.ftthchecker.domain.Edge;

import java.util.List;

public interface EdgeServiceInterface {
	public List<Edge> findEdgesInArea(double x1, double y1, double x2,
			double y2);

	public Edge findEdgeByName(Long name);
}
