package pl.aptewicz.ftthchecker.repository;

import java.util.List;

import pl.aptewicz.ftthchecker.domain.Edge;

public interface EdgesInAreaRepository {
	public List<Edge> findEdgesInArea(double x1, double y1, double x2,
			double y2, long zoom);
}
