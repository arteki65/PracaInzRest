package pl.aptewicz.ftthchecker.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.aptewicz.ftthchecker.domain.Edge;
import pl.aptewicz.ftthchecker.domain.Node;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EdgeDto {

	private Long name;

	private Node nodeA;

	private Node nodeB;

	private Double length;

	public EdgeDto(Edge edge) {
		name = edge.getName();
		nodeA = edge.getNodeA();
		nodeB = edge.getNodeB();
		length = edge.getLength();
	}
}
