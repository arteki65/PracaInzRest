package pl.aptewicz.ftthchecker.contorller;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import pl.aptewicz.ftthchecker.domain.Node;
import pl.aptewicz.ftthchecker.repository.NodeRepository;

@Transactional
@RestController
@RequestMapping({"/node"})
public class NodeController {

	private NodeRepository nodeRepository;

	@Autowired
	public NodeController(NodeRepository nodeRepository) {
		this.nodeRepository = nodeRepository;
	}

	@RequestMapping(value = "/{nodeId}", method = RequestMethod.GET)
	public Node getNodeById(@PathVariable String nodeId) {
		return nodeRepository.findOne(new Long(nodeId));
	}
}