package pl.aptewicz.ftthchecker.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@NamedQueries({
		@NamedQuery(name = "Edge.findEdgesInArea", query = "SELECT e FROM Edge e WHERE "
				+ "e.nodeA.x >= :x1 AND e.nodeA.y >= :y1 AND e.nodeA.name in "
				+ "(SELECT d.node FROM Demand d WHERE d.signalCount >= :nodeSize) "
				+ "AND e.nodeB.x <= :x2 AND e.nodeB.y <= :y2 AND e.nodeB.name in "
				+ "(SELECT d.node FROM Demand d WHERE d.signalCount >= :nodeSize)")})
public class Edge {

	@Id
	private Long name;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "nodeAName")
	private Node nodeA;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "nodeBName")
	private Node nodeB;

	private Double length;

	public Long getName() {
		return name;
	}

	public void setName(Long name) {
		this.name = name;
	}

	public Node getNodeA() {
		return nodeA;
	}

	public void setNodeA(Node nodeA) {
		this.nodeA = nodeA;
	}

	public Node getNodeB() {
		return nodeB;
	}

	public void setNodeB(Node nodeB) {
		this.nodeB = nodeB;
	}

	public Double getLength() {
		return length;
	}

	public void setLength(Double length) {
		this.length = length;
	}

}
