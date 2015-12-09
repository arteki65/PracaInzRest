package pl.aptewicz.ftthchecker.contorller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import pl.aptewicz.ftthchecker.domain.Node;
import pl.aptewicz.ftthchecker.repository.NodeRepository;

@RestController
@RequestMapping({"/node"})
public class NodeController {

	private NodeRepository nodeRepository;

	@Autowired
	public NodeController(NodeRepository nodeRepository) {
		this.nodeRepository = nodeRepository;
	}

	@RequestMapping(value = "/{nodeId}", method = RequestMethod.GET)
	public ResponseEntity<Node> getNodeById(@PathVariable String nodeId) {
		Node node = nodeRepository.findOne(new Long(nodeId));
		HttpStatus status = node != null ? HttpStatus.OK : HttpStatus.NOT_FOUND;
		return new ResponseEntity<Node>(node, status);
	}
}
