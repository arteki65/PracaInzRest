package pl.aptewicz.ftthchecker.contorller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pl.aptewicz.ftthchecker.domain.FtthCheckerUser;
import pl.aptewicz.ftthchecker.domain.FtthCheckerUserRole;
import pl.aptewicz.ftthchecker.dto.FtthCheckerUserDto;
import pl.aptewicz.ftthchecker.repository.FtthCheckerUserRepository;

import java.util.ArrayList;
import java.util.Collection;

@RestController
@RequestMapping("/user")
public class FtthCheckerUserController {

	private final FtthCheckerUserRepository ftthCheckerUserRepository;

	@Autowired
	public FtthCheckerUserController(FtthCheckerUserRepository ftthCheckerUserRepository) {
		this.ftthCheckerUserRepository = ftthCheckerUserRepository;
	}

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<FtthCheckerUserDto> getUserDetails(
			@AuthenticationPrincipal org.springframework.security.core.userdetails.User user) {
		FtthCheckerUser ftthCheckerUser = ftthCheckerUserRepository.findByUsername(user.getUsername());
		if (ftthCheckerUser != null) {
			return new ResponseEntity<>(new FtthCheckerUserDto(ftthCheckerUser), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(method = RequestMethod.GET, path = "/servicemen")
	public ResponseEntity<Collection<String>> getServicemenUsernames() {
		Collection<FtthCheckerUser> ftthCheckerUsers = ftthCheckerUserRepository
				.findByFtthUserRole(FtthCheckerUserRole.SERVICEMAN);
		if (ftthCheckerUsers != null) {
			Collection<String> servicemenUsernames = new ArrayList<>();
			ftthCheckerUsers.forEach(ftthCheckerUser -> servicemenUsernames.add(ftthCheckerUser.getUsername()));
			return new ResponseEntity<>(servicemenUsernames, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}
