package pl.aptewicz.ftthchecker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import pl.aptewicz.ftthchecker.exception.FtthRestApiException;

@Data
@AllArgsConstructor
public class FtthRestApiErrorDto {

	private String code;

	public FtthRestApiErrorDto(FtthRestApiException ftthRestApiException) {
		code = ftthRestApiException.getCode();
	}
}
