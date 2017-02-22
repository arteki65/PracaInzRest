package pl.aptewicz.ftthchecker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pl.aptewicz.ftthchecker.domain.FtthCustomer;
import pl.aptewicz.ftthchecker.dto.FtthCustomerDto;
import pl.aptewicz.ftthchecker.repository.FtthCustomerRepository;

import java.util.Optional;

@RestController
@RequestMapping("/ftthCustomer")
public class FtthCustomerController {

	private final FtthCustomerRepository ftthCustomerRepository;

	@Autowired
	public FtthCustomerController(FtthCustomerRepository ftthCustomerRepository) {
		this.ftthCustomerRepository = ftthCustomerRepository;
	}

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<FtthCustomerDto> getFtthCustomer(@AuthenticationPrincipal User user) {
		Optional<FtthCustomer> ftthCustomerOptional = Optional
				.ofNullable(ftthCustomerRepository.findByUsername(user.getUsername()));
		if(ftthCustomerOptional.isPresent()) {
			FtthCustomerDto ftthCustomerDto = new FtthCustomerDto(ftthCustomerOptional.get());
			return new ResponseEntity<>(ftthCustomerDto, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
}
