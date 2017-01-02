package pl.aptewicz.ftthchecker.contorller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import pl.aptewicz.ftthchecker.dto.FtthRestApiErrorDto;
import pl.aptewicz.ftthchecker.exception.FtthRestApiException;

@ControllerAdvice
public class FtthRestApiExceptionHandler {

	@ExceptionHandler(FtthRestApiException.class)
	public ResponseEntity<FtthRestApiErrorDto> handleNoEdgesFoundNearIssueLocation(
			FtthRestApiException ftthRestApiException) {
		return new ResponseEntity<>(new FtthRestApiErrorDto(ftthRestApiException), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
