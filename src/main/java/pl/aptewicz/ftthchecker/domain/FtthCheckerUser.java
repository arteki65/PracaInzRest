package pl.aptewicz.ftthchecker.domain;

import javax.persistence.*;
import java.util.Collection;

@Entity
public class FtthCheckerUser {

	@Id
	@GeneratedValue
	private Long id;

	private String username;

	private String password;

	private FtthCheckerUserRole ftthUserRole;

	@OneToMany(mappedBy = "ftthCheckerUser", fetch = FetchType.EAGER)
	private Collection<FtthJob> jobs;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Collection<FtthJob> getJobs() {
		return jobs;
	}

	public void setJobs(Collection<FtthJob> jobs) {
		this.jobs = jobs;
	}

	public FtthCheckerUserRole getFtthUserRole() {
		return ftthUserRole;
	}

	public void setFtthUserRole(FtthCheckerUserRole ftthUserRole) {
		this.ftthUserRole = ftthUserRole;
	}
}
