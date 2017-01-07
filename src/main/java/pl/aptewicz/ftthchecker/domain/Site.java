package pl.aptewicz.ftthchecker.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Data
@Entity
public class Site {

	@Id
	@GeneratedValue
	private Long id;

	private String name;

	private String usedSite;

	@OneToOne
	private Edge firstEdge;

	private String firstEdgeSpliced;
}
