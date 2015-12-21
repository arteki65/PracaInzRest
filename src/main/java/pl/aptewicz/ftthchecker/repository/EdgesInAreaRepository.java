package pl.aptewicz.ftthchecker.repository;

import java.util.List;

import pl.aptewicz.ftthchecker.domain.Edge;

public interface EdgesInAreaRepository {
	public List<Edge> findEdgesInAreaWithDemand(double x1, double y1, double x2,
			double y2, long zoom);

	public List<Edge> findEdgesInAreaWithoutDemand(double x1, double y1,
			double x2, double y2, long zoom);
}
