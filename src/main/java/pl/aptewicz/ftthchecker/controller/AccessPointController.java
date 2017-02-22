package pl.aptewicz.ftthchecker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.aptewicz.ftthchecker.domain.AccessPoint;
import pl.aptewicz.ftthchecker.dto.AccessPointDto;
import pl.aptewicz.ftthchecker.repository.AccessPointRepository;

import java.util.Collection;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/accessPoint")
public class AccessPointController {

	private final AccessPointRepository accessPointRepository;

	@Autowired
	public AccessPointController(AccessPointRepository accessPointRepository) {
		this.accessPointRepository = accessPointRepository;
	}

	@RequestMapping(method = RequestMethod.GET, path = "/findAccessPointsInArea")
	public ResponseEntity<Collection<AccessPointDto>> findAccessPointsInArea(@RequestParam("x1") String x1,
			@RequestParam("x2") String x2, @RequestParam("y1") String y1, @RequestParam("y2") String y2) {
		Collection<AccessPoint> accessPointsInArea = accessPointRepository
				.findAccessPointsInArea(Double.valueOf(x1), Double.valueOf(y1), Double.valueOf(x2), Double.valueOf(y2));
		return new ResponseEntity<>(accessPointsInArea.stream().map(AccessPointDto::new).collect(Collectors.toList()),
				HttpStatus.OK);
	}
}
