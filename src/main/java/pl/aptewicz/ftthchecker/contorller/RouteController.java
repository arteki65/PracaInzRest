package pl.aptewicz.ftthchecker.contorller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/route")
public class RouteController {

	private static final String GOOGLE_API_KEY = "AIzaSyB-QKyOwRzSMznccH_jPwYwgxSjF2CrhZ8";

	@RequestMapping(method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<String> findRoute(@RequestParam(name = "origin") String origin,
			@RequestParam(name = "destination") String destination) {
		String url =
				"https://maps.googleapis.com/maps/api/directions/json?origin=" + origin + "&destination=" + destination
						+ "&key=" + GOOGLE_API_KEY;

		RestTemplate restTemplate = new RestTemplate();
		String route = restTemplate.getForObject(url, String.class);
		return new ResponseEntity<>(route, HttpStatus.OK);
	}
}
