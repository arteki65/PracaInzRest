package pl.aptewicz.ftthchecker.service;

import org.springframework.stereotype.Service;

@Service
public class DistanceServiceImpl implements DistanceService {

	@Override
	public double getDistance(double latitude, double longitude, double latitude2, double longitude2) {
		int earthRadiusInKm = 6371;

		double degLat = deg2rad(latitude2 - latitude);
		double degLon = deg2rad(longitude2 - longitude);

		double a =
				Math.sin(degLat / 2) * Math.sin(degLat / 2) + Math.cos(deg2rad(latitude)) * Math.cos(deg2rad(latitude2))
						* Math.sin(degLon / 2) * Math.sin(degLon / 2);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		return earthRadiusInKm * c;
	}

	private double deg2rad(double deg) {
		return deg * (Math.PI / 180);
	}
}
