package pl.aptewicz.ftthchecker.repository;

import pl.aptewicz.ftthchecker.domain.Edge;

import java.util.List;

public interface EdgesInAreaRepository {

	List<Edge> findEdgesInAreaWithDemand(double x1, double y1, double x2, double y2);
}
