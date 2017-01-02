package pl.aptewicz.ftthchecker.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.aptewicz.ftthchecker.domain.FtthJob;
import pl.aptewicz.ftthchecker.domain.FtthJobStatus;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FtthJobDto {

	private Long id;

	private String description;

	private FtthJobStatus jobStatus;

	private String servicemanUsername;

	private Collection<EdgeDto> affectedEdges;

	public FtthJobDto(FtthJob ftthJob) {
		id = ftthJob.getId();
		description = ftthJob.getDescription();
		jobStatus = ftthJob.getJobStatus();
		Optional.ofNullable(ftthJob.getFtthCheckerUser())
				.ifPresent(ftthCheckerUser -> servicemanUsername = ftthCheckerUser.getUsername());
		affectedEdges = new ArrayList<>();
		Optional.ofNullable(ftthJob.getAffectedEdges())
				.ifPresent(edges -> edges.forEach(edge -> affectedEdges.add(new EdgeDto(edge))));
	}
}
