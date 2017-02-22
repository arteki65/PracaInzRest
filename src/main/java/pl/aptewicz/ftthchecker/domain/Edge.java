package pl.aptewicz.ftthchecker.domain;

import lombok.Data;

import javax.persistence.*;

@Entity
@NamedQueries({@NamedQuery(name = "Edge.findEdgesInArea", query = "SELECT e FROM Edge e WHERE "
		+ "e.nodeA.x >= :x1 AND e.nodeA.y >= :y1 AND e.nodeB.x <= :x2 AND e.nodeB.y <= :y2")})
@Data
public class Edge {

	@Id
	private Long name;

	@ManyToOne
	@JoinColumn(name = "nodeAName")
	private Node nodeA;

	@ManyToOne
	@JoinColumn(name = "nodeBName")
	private Node nodeB;

	private Double length;
}
