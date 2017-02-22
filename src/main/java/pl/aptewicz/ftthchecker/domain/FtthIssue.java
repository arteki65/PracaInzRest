package pl.aptewicz.ftthchecker.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import pl.aptewicz.ftthchecker.dto.FtthIssueDto;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
public class FtthIssue {

	@Id
	@GeneratedValue
	Long id;

	private String description;

	private double longitude;

	private double latitude;

	@ManyToOne
	@JoinColumn(name = "ftthCustomer_id")
	private FtthCustomer ftthCustomer;

	@OneToOne
	private FtthJob ftthJob;

	public FtthIssue(FtthIssueDto ftthIssueDto) {
		id = ftthIssueDto.getId();
		description = ftthIssueDto.getDescription();
		longitude = ftthIssueDto.getLongitude();
		latitude = ftthIssueDto.getLatitude();
	}

	@Override
	public String toString() {
		return "FtthIssue{" + "id=" + id + ", description='" + description + '\'' + ", longitude=" + longitude
				+ ", latitude=" + latitude + ", ftthCustomer=" + ftthCustomer + '}';
	}
}
