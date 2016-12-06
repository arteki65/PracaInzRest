package pl.aptewicz.ftthchecker.contorller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.aptewicz.ftthchecker.domain.Edge;
import pl.aptewicz.ftthchecker.service.EdgeServiceInterface;

import java.util.List;

@RestController
@RequestMapping("/edge")
public class EdgeController {

	private EdgeServiceInterface edgeService;

	@Autowired
	public EdgeController(EdgeServiceInterface edgeService) {
		this.edgeService = edgeService;
	}

	@RequestMapping(value = "/{edgeId}", method = RequestMethod.GET)
	public Edge getEdgeById(@PathVariable String edgeId) {
		return edgeService.findEdgeByName(new Long(edgeId));
	}

	@RequestMapping(value = "/findEdgesInArea", method = RequestMethod.GET)
	public List<Edge> getEdgesInArea(@RequestParam("x1") String x1, @RequestParam("x2") String x2,
			@RequestParam("y1") String y1, @RequestParam("y2") String y2, @RequestParam("zoom") String zoom) {

		return edgeService
				.findEdgesInArea(Double.valueOf(x1), Double.valueOf(y1), Double.valueOf(x2), Double.valueOf(y2),
						Double.valueOf(zoom).longValue());
	}
}
