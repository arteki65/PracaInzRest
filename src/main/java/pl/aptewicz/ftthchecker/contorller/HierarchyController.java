package pl.aptewicz.ftthchecker.contorller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pl.aptewicz.ftthchecker.dto.HierarchyDto;
import pl.aptewicz.ftthchecker.service.HierarchyService;

@RestController
@RequestMapping("/hierarchy")
public class HierarchyController {

	private final HierarchyService hierarchyService;

	@Autowired
	public HierarchyController(HierarchyService hierarchyService) {
		this.hierarchyService = hierarchyService;
	}

	@RequestMapping(method = RequestMethod.GET, path = "/findByAccessSiteLike/{like}")
	public ResponseEntity<HierarchyDto> findByAccessSiteLike(@PathVariable("like") String like) {
		return new ResponseEntity<>(hierarchyService.findHierarchyByAccessSiteLike(like), HttpStatus.OK);
	}
}
