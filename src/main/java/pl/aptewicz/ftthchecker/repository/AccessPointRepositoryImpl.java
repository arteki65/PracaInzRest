package pl.aptewicz.ftthchecker.repository;

import pl.aptewicz.ftthchecker.domain.AccessPoint;

import java.util.Collection;

public class AccessPointRepositoryImpl extends AbstractCustomRepository<AccessPoint>
		implements AccessPointInAreaRepository {

	@Override
	String getQueryName() {
		return "AccesPoint.findAPInArea";
	}

	@Override
	public Collection<AccessPoint> findAccessPointsInArea(double x1, double y1, double x2, double y2) {
		return findInArea(x1, y1, x2, y2);
	}
}
