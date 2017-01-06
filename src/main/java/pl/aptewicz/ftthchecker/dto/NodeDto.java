package pl.aptewicz.ftthchecker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.aptewicz.ftthchecker.domain.Node;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NodeDto {

	private Long name;

	private Double x;

	private Double y;

	public NodeDto(Node node) {
		name = node.getName();
		x =  node.getX();
		y = node.getY();
	}
}
