package pl.aptewicz.ftthchecker.domain;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@NamedQueries({@NamedQuery(name = "AccesPoint.findAPInArea", query = "SELECT ap FROM AccessPoint ap WHERE "
		+ "ap.node.x >= :x1 AND ap.node.y >= :y1 AND ap.node.x <= :x2 AND ap.node.y <= :y2")})
public class AccessPoint {

	@Id
	@GeneratedValue
	private Long id;

	@OneToOne
	private Node node;

	private String type;

	private String description;
}
