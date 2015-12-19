package pl.aptewicz.ftthchecker.contorller;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import pl.aptewicz.ftthchecker.domain.Edge;
import pl.aptewicz.ftthchecker.repository.EdgeRepository;

@RestController
@Transactional
@RequestMapping({"/edge"})
public class EdgeController {

	private EdgeRepository edgeRepository;

	@Autowired
	public EdgeController(EdgeRepository edgeRepository) {
		this.edgeRepository = edgeRepository;
	}

	@RequestMapping(value = "/{edgeId}", method = RequestMethod.GET)
	public Edge getEdgeById(@PathVariable String edgeId) {
		return edgeRepository.findOne(new Long(edgeId));
	}

	@RequestMapping(value = "/findEdgesInArea", method = RequestMethod.GET)
	public List<Edge> getEdgesInArea(@RequestParam("x1") String x1,
			@RequestParam("x2") String x2, @RequestParam("y1") String y1,
			@RequestParam("y2") String y2, @RequestParam("zoom") String zoom) {

		return edgeRepository.findEdgesInArea(Double.valueOf(x1),
				Double.valueOf(y1), Double.valueOf(x2), Double.valueOf(y2),
				Long.valueOf(Double.valueOf(zoom).longValue()));
	}
}
