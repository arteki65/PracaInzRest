package pl.aptewicz.ftthchecker.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import pl.aptewicz.ftthchecker.dto.FtthJobDto;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@Entity
@Data
@NoArgsConstructor
public class FtthJob {

	@Id
	@GeneratedValue
	private Long id;

	private String description;

	private FtthJobStatus jobStatus;

	@ManyToOne
	@JoinColumn(name = "ftthCheckerUser_id")
	private FtthCheckerUser ftthCheckerUser;

	@ManyToMany(fetch = FetchType.EAGER)
	private Collection<AccessPoint> affectedAccessPoints;

	@OneToOne
	private FtthIssue ftthIssue;

	public FtthJob(FtthJobDto ftthJobDto) {
		id = ftthJobDto.getId();
		description = ftthJobDto.getDescription();
		jobStatus = ftthJobDto.getJobStatus();
		affectedAccessPoints = new ArrayList<>();
		Optional.ofNullable(ftthJobDto.getAffectedAccessPoints()).ifPresent(accessPointDtos -> accessPointDtos
				.forEach(accessPointDto -> affectedAccessPoints.add(new AccessPoint(accessPointDto))));
	}

	@Override
	public String toString() {
		return "FtthJob{" + "id=" + id + ", description='" + description + '\'' + ", jobStatus=" + jobStatus
				+ ", affectedAccessPoints=" + affectedAccessPoints
				+ ", ftthIssue=" + ftthIssue + '}';
	}
}
