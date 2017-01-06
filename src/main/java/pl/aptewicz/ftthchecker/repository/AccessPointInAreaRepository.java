package pl.aptewicz.ftthchecker.repository;

import pl.aptewicz.ftthchecker.domain.AccessPoint;

import java.util.Collection;

public interface AccessPointInAreaRepository {

	Collection<AccessPoint> findAccessPointsInArea(double x1, double y1, double x2, double y2);
}
