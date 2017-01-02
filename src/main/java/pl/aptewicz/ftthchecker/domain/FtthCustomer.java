package pl.aptewicz.ftthchecker.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Data
public class FtthCustomer {

	@Id
	@GeneratedValue
	private Long id;

	private String username;

	private String password;

	@OneToMany(mappedBy = "ftthCustomer", fetch = FetchType.EAGER)
	private Collection<FtthIssue> ftthIssues;
}
