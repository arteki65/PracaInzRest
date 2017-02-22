package pl.aptewicz.ftthchecker.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Collection;

@Entity
@NamedQueries({@NamedQuery(name = "FtthCheckerUser.findUsersInArea", query = "SELECT u FROM FtthCheckerUser u WHERE "
		+ "u.lastPosition.longitude >= :x1 AND u.lastPosition.latitude >= :y1 AND u.lastPosition.longitude <= :x2 "
		+ "AND u.lastPosition.latitude <= :y2")})
@Data
public class FtthCheckerUser {

	@Id
	@GeneratedValue
	private Long id;

	private String username;

	private String password;

	private FtthCheckerUserRole ftthUserRole;

	@OneToMany(mappedBy = "ftthCheckerUser", fetch = FetchType.EAGER)
	private Collection<FtthJob> jobs;

	@OneToOne
	private LatLng lastPosition;
}
