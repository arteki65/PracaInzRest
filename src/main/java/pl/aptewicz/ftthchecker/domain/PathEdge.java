package pl.aptewicz.ftthchecker.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Data
@Entity
public class PathEdge {

	@Id
	@GeneratedValue
	private Long id;

	private String sinkSite;

	@OneToOne
	private Edge edge;

	@OneToOne
	private Edge previousEdge;

	private String previousEdgeSpliced;
}
