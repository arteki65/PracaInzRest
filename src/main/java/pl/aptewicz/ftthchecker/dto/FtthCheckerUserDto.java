package pl.aptewicz.ftthchecker.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.aptewicz.ftthchecker.domain.FtthCheckerUser;
import pl.aptewicz.ftthchecker.domain.FtthCheckerUserRole;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FtthCheckerUserDto {

	private Long id;

	private String username;

	private FtthCheckerUserRole ftthCheckerUserRole;

	private Collection<FtthJobDto> ftthJobs;

	private LatLngDto lastPosition;

	public FtthCheckerUserDto(FtthCheckerUser ftthCheckerUser) {
		id = ftthCheckerUser.getId();
		username = ftthCheckerUser.getUsername();
		ftthCheckerUserRole = ftthCheckerUser.getFtthUserRole();
		Optional.ofNullable(ftthCheckerUser.getLastPosition()).ifPresent(latLng -> lastPosition = new LatLngDto
				(latLng));
		Optional.ofNullable(ftthCheckerUser.getJobs()).ifPresent(
				ftthJobsLambda -> ftthJobs = ftthJobsLambda.stream().map(FtthJobDto::new).collect(Collectors.toList()));
	}
}
