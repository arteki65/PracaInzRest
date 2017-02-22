package pl.aptewicz.ftthchecker.service;

import pl.aptewicz.ftthchecker.dto.EdgeDto;

import java.util.Collection;

public interface PathService {

	Collection<EdgeDto> findPathForIssue(Long issueId);
}
