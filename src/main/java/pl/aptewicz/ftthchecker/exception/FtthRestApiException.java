package pl.aptewicz.ftthchecker.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class FtthRestApiException extends RuntimeException {

	private String code;

	public FtthRestApiException(String code) {
		super();
		this.code = code;
	}
}
