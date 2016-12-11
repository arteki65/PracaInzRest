package pl.aptewicz.ftthchecker.domain;

import pl.aptewicz.ftthchecker.dto.FtthJobDto;

import javax.persistence.*;

@Entity
public class FtthJob {

	@Id
	@GeneratedValue
	private Long id;

	private String description;

	private double latitude;

	private double longitude;

	private FtthJobStatus jobStatus;

	@ManyToOne
	@JoinColumn(name = "ftthCheckerUser_id")
	private FtthCheckerUser ftthCheckerUser;

	public FtthJob()	{
		//for jpa
	}

	public FtthJob(FtthJobDto ftthJobDto) {
		id = ftthJobDto.getId();
		description = ftthJobDto.getDescription();
		jobStatus = ftthJobDto.getJobStatus();
		latitude = ftthJobDto.getLatitude();
		longitude = ftthJobDto.getLongitude();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public FtthJobStatus getJobStatus() {
		return jobStatus;
	}

	public void setJobStatus(FtthJobStatus jobStatus) {
		this.jobStatus = jobStatus;
	}

	public FtthCheckerUser getFtthCheckerUser() {
		return ftthCheckerUser;
	}

	public void setFtthCheckerUser(FtthCheckerUser ftthCheckerUser) {
		this.ftthCheckerUser = ftthCheckerUser;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
}
