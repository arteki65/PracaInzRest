package pl.aptewicz.ftthchecker.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.Collection;

@Entity
public class FtthCheckerUser {

	@Id
	@GeneratedValue
	private Long id;

	private String username;

	private String password;

	@OneToMany(mappedBy =)
	private Collection<Job> jobs;
}
