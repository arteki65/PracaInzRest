package pl.aptewicz.ftthchecker.service;

public interface RouteService {

	String findRoute(String origin, String destination);

	int findDurationInSeconds(String origin, String destination);
}
