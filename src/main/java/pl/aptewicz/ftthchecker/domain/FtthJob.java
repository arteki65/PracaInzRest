package pl.aptewicz.ftthchecker.domain;

import javax.persistence.*;

@Entity
public class FtthJob {

	@Id
	@GeneratedValue
	private Long id;

	private String description;

	private JobStatus jobStatus;

	@ManyToOne
	@JoinColumn(name = "ftthCheckerUser_id")
	private FtthCheckerUser ftthCheckerUser;

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

	public JobStatus getJobStatus() {
		return jobStatus;
	}

	public void setJobStatus(JobStatus jobStatus) {
		this.jobStatus = jobStatus;
	}

	public FtthCheckerUser getFtthCheckerUser() {
		return ftthCheckerUser;
	}

	public void setFtthCheckerUser(FtthCheckerUser ftthCheckerUser) {
		this.ftthCheckerUser = ftthCheckerUser;
	}
}
