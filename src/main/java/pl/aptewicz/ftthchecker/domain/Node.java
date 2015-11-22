package pl.aptewicz.ftthchecker.domain;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Node {

	@Id
	private Long name;

	private Double x;

	private Double y;

	public Node() {
		// do nothing, using by jpa
	}

	public Long getName() {
		return name;
	}

	public void setName(Long name) {
		this.name = name;
	}

	public Double getX() {
		return x;
	}

	public void setX(Double x) {
		this.x = x;
	}

	public Double getY() {
		return y;
	}

	public void setY(Double y) {
		this.y = y;
	}

}
