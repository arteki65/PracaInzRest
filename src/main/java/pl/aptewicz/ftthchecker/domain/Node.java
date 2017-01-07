package pl.aptewicz.ftthchecker.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.aptewicz.ftthchecker.dto.NodeDto;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Node {

	@Id
	private Long name;

	private Double x;

	private Double y;

	public Node(NodeDto node) {
		name = node.getName();
		x = node.getX();
		y = node.getY();
	}
}
