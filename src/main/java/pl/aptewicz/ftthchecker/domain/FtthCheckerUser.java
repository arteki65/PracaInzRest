package pl.aptewicz.ftthchecker.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Data
public class FtthCheckerUser {

	@Id
	@GeneratedValue
	private Long id;

	private String username;

	private String password;

	private FtthCheckerUserRole ftthUserRole;

	@OneToMany(mappedBy = "ftthCheckerUser", fetch = FetchType.EAGER)
	private Collection<FtthJob> jobs;
}
