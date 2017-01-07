package pl.aptewicz.ftthchecker.service;

import pl.aptewicz.ftthchecker.dto.HierarchyDto;

public interface HierarchyService {

	HierarchyDto findHierarchyByAccessSiteLike(String accessSiteLike);
}
