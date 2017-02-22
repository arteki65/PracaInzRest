package pl.aptewicz.ftthchecker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.aptewicz.ftthchecker.service.RouteService;

@RestController
@RequestMapping("/route")
public class RouteController {

	private final RouteService routeService;

	@Autowired
	public RouteController(RouteService routeService) {
		this.routeService = routeService;
	}

	@RequestMapping(method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<String> findRoute(@RequestParam(name = "origin") String origin,
			@RequestParam(name = "destination") String destination) {
		return new ResponseEntity<>(routeService.findRoute(origin, destination), HttpStatus.OK);
	}
}
