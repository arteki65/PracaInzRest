package pl.aptewicz.ftthchecker.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
public class LatLng {

	@Id
	@GeneratedValue
	private Long id;

	private double latitude;

	private double longitude;
}
