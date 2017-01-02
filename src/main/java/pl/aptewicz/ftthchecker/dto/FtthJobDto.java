package pl.aptewicz.ftthchecker.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.aptewicz.ftthchecker.domain.FtthJob;
import pl.aptewicz.ftthchecker.domain.FtthJobStatus;

import java.util.Collection;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FtthJobDto {

	private Long id;

	private String description;

	private FtthJobStatus jobStatus;

	private String servicemanUsername;

	private Collection<EdgeDto> affectedDtoEdges;

	public FtthJobDto(FtthJob ftthJob) {
		id = ftthJob.getId();
		description = ftthJob.getDescription();
		jobStatus = ftthJob.getJobStatus();
	}
}
