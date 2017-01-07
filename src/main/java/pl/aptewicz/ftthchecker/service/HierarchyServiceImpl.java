package pl.aptewicz.ftthchecker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.aptewicz.ftthchecker.dto.HierarchyDto;
import pl.aptewicz.ftthchecker.dto.NodeDto;
import pl.aptewicz.ftthchecker.repository.HierarchyRepository;
import pl.aptewicz.ftthchecker.repository.NodeRepository;

@Service
public class HierarchyServiceImpl implements HierarchyService {

	private final HierarchyRepository hierarchyRepository;

	private final NodeRepository nodeRepository;

	@Autowired
	public HierarchyServiceImpl(HierarchyRepository hierarchyRepository, NodeRepository nodeRepository) {
		this.hierarchyRepository = hierarchyRepository;
		this.nodeRepository = nodeRepository;
	}

	@Override
	public HierarchyDto findHierarchyByAccessSiteLike(String accessSiteLike) {
		HierarchyDto hierarchyDto = new HierarchyDto(hierarchyRepository.findByAccessSiteLike(accessSiteLike + "%"));

		NodeDto distributionSiteNode = new NodeDto(nodeRepository.findOne(Long.valueOf(
				hierarchyDto.getDistributionSiteDescription()
						.substring(0, hierarchyDto.getDistributionSiteDescription().indexOf("_")))));
		hierarchyDto.setDistributionSiteNode(distributionSiteNode);

		return hierarchyDto;
	}
}
