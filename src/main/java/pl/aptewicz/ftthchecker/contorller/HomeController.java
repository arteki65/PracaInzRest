package pl.aptewicz.ftthchecker.contorller;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import pl.aptewicz.ftthchecker.domain.Node;
import pl.aptewicz.ftthchecker.repository.NodeRepository;

@Transactional
@RestController
@RequestMapping({"/"})
public class HomeController {

	private NodeRepository nodeRepository;

	@Autowired
	public HomeController(NodeRepository nodeRepository) {
		this.nodeRepository = nodeRepository;
	}

	@RequestMapping(method = RequestMethod.GET)
	public Node home() {
		return nodeRepository.findOne(new Long("14"));
	}
}
