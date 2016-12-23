package pl.aptewicz.ftthchecker.domain;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
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
}
