package pl.aptewicz.ftthchecker.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.aptewicz.ftthchecker.dto.LatLngDto;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class LatLng {

	@Id
	@GeneratedValue
	private Long id;

	private double latitude;

	private double longitude;

	public LatLng(LatLngDto lastPosition) {
		latitude = lastPosition.getLatitude();
		longitude = lastPosition.getLongitude();
	}
}
