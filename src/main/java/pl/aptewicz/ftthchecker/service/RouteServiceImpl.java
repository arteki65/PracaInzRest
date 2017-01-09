package pl.aptewicz.ftthchecker.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.aptewicz.ftthchecker.googleapi.DistanceMatrixApiResponse;

@Service
public class RouteServiceImpl implements RouteService {

	private static final String GOOGLE_API_KEY = "AIzaSyA1dbK4YfwhnVUhQYNHrUUBy1T3_V8evdA";

	@Override
	public String findRoute(String origin, String destination) {
		String url =
				"https://maps.googleapis.com/maps/api/directions/json?origin=" + origin + "&destination=" + destination
						+ "&key=" + GOOGLE_API_KEY;
		RestTemplate restTemplate = new RestTemplate();
		return restTemplate.getForObject(url, String.class);
	}

	@Override
	public int findDurationInSeconds(String origin, String destination) {
		String url = "https://maps.googleapis.com/maps/api/distancematrix/json?units=metric&language=pl&key="
				+ GOOGLE_API_KEY + "&origins=" + origin + "&destinations=" + destination;
		/*SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
		Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("proxy.schenker.pl", 80));
		requestFactory.setProxy(proxy);*/
		RestTemplate restTemplate = new RestTemplate();
		DistanceMatrixApiResponse distanceMatrixApiResponse = restTemplate
				.getForObject(url, DistanceMatrixApiResponse.class);
		return distanceMatrixApiResponse.getRows().get(0).getElements().get(0).getDuration().getValue();
	}
}
