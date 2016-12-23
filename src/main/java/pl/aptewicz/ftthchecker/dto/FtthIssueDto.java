package pl.aptewicz.ftthchecker.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FtthIssueDto {

	Long id;

	private String description;

	private double longitude;

	private double latitude;

	private FtthJobDto ftthJobDto;
}
