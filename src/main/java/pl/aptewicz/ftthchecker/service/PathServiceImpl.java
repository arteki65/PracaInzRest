package pl.aptewicz.ftthchecker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.aptewicz.ftthchecker.domain.*;
import pl.aptewicz.ftthchecker.dto.EdgeDto;
import pl.aptewicz.ftthchecker.repository.FtthIssueRepository;
import pl.aptewicz.ftthchecker.repository.HierarchyRepository;
import pl.aptewicz.ftthchecker.repository.PathRepository;
import pl.aptewicz.ftthchecker.repository.SiteRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@Service
public class PathServiceImpl implements PathService {

	private final FtthIssueRepository ftthIssueRepository;

	private final SiteRepository siteRepository;

	private final PathRepository pathRepository;

	private final HierarchyRepository hierarchyRepository;

	@Autowired
	public PathServiceImpl(FtthIssueRepository ftthIssueRepository, SiteRepository siteRepository,
			PathRepository pathRepository, HierarchyRepository hierarchyRepository) {
		this.ftthIssueRepository = ftthIssueRepository;
		this.siteRepository = siteRepository;
		this.pathRepository = pathRepository;
		this.hierarchyRepository = hierarchyRepository;
	}

	@Override
	public Collection<EdgeDto> findPathForIssue(Long issueId) {
		FtthIssue ftthIssue = ftthIssueRepository.findOne(issueId);

		Optional<AccessPoint> accessPointOptional = ftthIssue.getFtthJob().getAffectedAccessPoints().stream()
				.findFirst();
		if (accessPointOptional.isPresent()) {
			Collection<EdgeDto> path = new ArrayList<>();

			AccessPoint accessPoint = accessPointOptional.get();
			String siteName = accessPoint.getNode().getName() + "_" + accessPoint.getDescription();
			Site site = siteRepository.findByName(siteName);

			String accessSiteLike;
			if(accessPoint.getDescription().contains("/")) {
				accessSiteLike = accessPoint.getNode().getName().toString() + "_" + accessPoint.getDescription()
						.substring(0, accessPoint.getDescription().indexOf("/")) + "%";
			} else {
				accessSiteLike = accessPoint.getNode().getName().toString() + "_" + accessPoint.getDescription()
						.substring(0, accessPoint.getDescription().indexOf("_")) + "%";
			}
			Hierarchy hierarchy = hierarchyRepository.findByAccessSiteLike(accessSiteLike);

			Edge firstEdgeFromSite = site.getFirstEdge();
			path.add(new EdgeDto(firstEdgeFromSite));

			PathEdge pathEdge = pathRepository.findByEdgeAndSinkSiteLike(firstEdgeFromSite, siteName);
			while (!pathEdge.getEdge().equals(pathEdge.getPreviousEdge())) {
				Edge previousEdge = pathEdge.getPreviousEdge();
				path.add(new EdgeDto(previousEdge));

				pathEdge = pathRepository.findByEdgeAndSinkSiteLike(previousEdge, siteName);
			}

			if (hierarchy.getDistributionSite().contains(pathEdge.getEdge().getNodeB().getName().toString())
					|| hierarchy.getDistributionSite().contains(pathEdge.getEdge().getNodeA().getName().toString())) {
				Site distributionSite = siteRepository.findByNameLike(hierarchy.getDistributionSite() + "%");

				Edge distributionSiteFirstEdge = distributionSite.getFirstEdge();
				path.add(new EdgeDto(distributionSiteFirstEdge));

				PathEdge pathEdgeForDistributionSite = pathRepository
						.findByEdgeAndSinkSiteLike(distributionSiteFirstEdge, distributionSite.getName());

				while (!pathEdgeForDistributionSite.getEdge().equals(pathEdgeForDistributionSite.getPreviousEdge())) {
					Edge distributionSitePreviousEdge = pathEdgeForDistributionSite.getPreviousEdge();
					path.add(new EdgeDto(distributionSitePreviousEdge));

					pathEdgeForDistributionSite = pathRepository
							.findByEdgeAndSinkSiteLike(distributionSitePreviousEdge, distributionSite.getName());
				}
			}

			return path;
		}

		throw new RuntimeException("Unexpected exception while searching path!");
	}
}
