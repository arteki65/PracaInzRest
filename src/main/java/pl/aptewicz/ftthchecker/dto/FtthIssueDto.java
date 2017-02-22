package pl.aptewicz.ftthchecker.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.aptewicz.ftthchecker.domain.FtthIssue;

import java.util.Optional;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FtthIssueDto {

	private Long id;

	private String description;

	private double longitude;

	private double latitude;

	private FtthJobDto ftthJob;

	public FtthIssueDto(FtthIssue ftthIssue) {
		id = ftthIssue.getId();
		description = ftthIssue.getDescription();
		latitude = ftthIssue.getLatitude();
		longitude = ftthIssue.getLongitude();
		Optional.ofNullable(ftthIssue.getFtthJob()).ifPresent(ftthJobLambda -> ftthJob = new FtthJobDto(ftthJobLambda));
	}
}
