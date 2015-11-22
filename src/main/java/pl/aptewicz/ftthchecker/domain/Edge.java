package pl.aptewicz.ftthchecker.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Edge {

	@Id
	private Long name;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "nodeAName")
	private Node nodeA;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "nodeBName")
	private Node nodeB;

	private Double length;

	public Edge() {
		// do nothing, usung by jpa
	}

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
