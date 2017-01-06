package pl.aptewicz.ftthchecker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.aptewicz.ftthchecker.domain.LatLng;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LatLngDto {

	private double latitude;

	private double longitude;

	public LatLngDto(LatLng latLng) {
		latitude = latLng.getLatitude();
		longitude = latLng.getLongitude();
	}
}
