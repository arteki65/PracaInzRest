package pl.aptewicz.ftthchecker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.aptewicz.ftthchecker.domain.Hierarchy;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HierarchyDto {

	private Long id;

	private String accessSiteDescription;

	private NodeDto accessSiteNode;

	private String distributionSiteDescription;

	private NodeDto distributionSiteNode;

	private String centralSiteDescription;

	private NodeDto centralSiteNode;

	public HierarchyDto(Hierarchy hierarchy) {
		id = hierarchy.getId();
		accessSiteDescription = hierarchy.getAccessSite();
		distributionSiteDescription = hierarchy.getDistributionSite();
		centralSiteDescription = hierarchy.getCentralSite();
	}
}
