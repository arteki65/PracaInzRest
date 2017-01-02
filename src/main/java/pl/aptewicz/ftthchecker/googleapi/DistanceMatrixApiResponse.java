package pl.aptewicz.ftthchecker.googleapi;

import lombok.Data;

import java.util.List;

@Data
public class DistanceMatrixApiResponse {

	List<DistanceMatrixRow> rows;
}
