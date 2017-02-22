package pl.aptewicz.ftthchecker.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.aptewicz.ftthchecker.dto.AccessPointDto;

import javax.persistence.*;

@Entity
@Data
@NamedQueries({@NamedQuery(name = "AccesPoint.findAPInArea", query = "SELECT ap FROM AccessPoint ap WHERE "
		+ "ap.node.x >= :x1 AND ap.node.y >= :y1 AND ap.node.x <= :x2 AND ap.node.y <= :y2")})
@AllArgsConstructor
@NoArgsConstructor
public class AccessPoint {

	@Id
	@GeneratedValue
	private Long id;

	@OneToOne
	private Node node;

	private String type;

	private String description;

	public AccessPoint(AccessPointDto accessPointDto) {
		id = accessPointDto.getId();
		node = new Node(accessPointDto.getNode());
		type = accessPointDto.getType();
		description = accessPointDto.getDescription();
	}
}
