package pl.aptewicz.ftthchecker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pl.aptewicz.ftthchecker.dto.EdgeDto;
import pl.aptewicz.ftthchecker.service.PathService;

import java.util.Collection;

@RestController
@RequestMapping("/path")
public class PathController {

	private final PathService pathService;

	@Autowired
	public PathController(PathService pathService) {
		this.pathService = pathService;
	}

	@RequestMapping(method = RequestMethod.GET, path = "/findPathForIssue/{id}")
	public ResponseEntity<Collection<EdgeDto>> findPathForIssue(@PathVariable Long id) {
		return new ResponseEntity<>(pathService.findPathForIssue(id), HttpStatus.OK);
	}
}
