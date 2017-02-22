package pl.aptewicz.ftthchecker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.aptewicz.ftthchecker.domain.AccessPoint;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccessPointDto {

	private Long id;

	private NodeDto node;

	private String type;

	private String description;

	public AccessPointDto(AccessPoint accessPoint) {
		id = accessPoint.getId();
		node = new NodeDto(accessPoint.getNode());
		type = accessPoint.getType();
		description = accessPoint.getDescription();
	}
}
