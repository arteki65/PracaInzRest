package pl.aptewicz.ftthchecker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import pl.aptewicz.ftthchecker.domain.FtthCheckerUser;
import pl.aptewicz.ftthchecker.domain.FtthCheckerUserRole;
import pl.aptewicz.ftthchecker.dto.FtthCheckerUserDto;
import pl.aptewicz.ftthchecker.dto.LatLngDto;
import pl.aptewicz.ftthchecker.repository.FtthCheckerUserRepository;
import pl.aptewicz.ftthchecker.repository.LatLngRepository;

import java.util.ArrayList;
import java.util.Collection;

@RestController
@RequestMapping("/user")
public class FtthCheckerUserController {

	private final FtthCheckerUserRepository ftthCheckerUserRepository;

	private final LatLngRepository latLngRepository;

	@Autowired
	public FtthCheckerUserController(FtthCheckerUserRepository ftthCheckerUserRepository,
			LatLngRepository latLngRepository) {
		this.ftthCheckerUserRepository = ftthCheckerUserRepository;
		this.latLngRepository = latLngRepository;
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

	@RequestMapping(method = RequestMethod.GET, path = "findUser/{username}")
	public ResponseEntity<FtthCheckerUserDto> finUser(@PathVariable("username") String username) {
		FtthCheckerUser ftthCheckerUser = ftthCheckerUserRepository.findByUsername(username);
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

	@RequestMapping(method = RequestMethod.PUT, path = "/updateLastLocation")
	public ResponseEntity<FtthCheckerUserDto> updateLastLocation(@RequestBody FtthCheckerUserDto ftthCheckerUserDto) {
		FtthCheckerUser ftthCheckerUser = ftthCheckerUserRepository.findOne(ftthCheckerUserDto.getId());
		ftthCheckerUser.getLastPosition().setLatitude(ftthCheckerUserDto.getLastPosition().getLatitude());
		ftthCheckerUser.getLastPosition().setLongitude(ftthCheckerUserDto.getLastPosition().getLongitude());
		latLngRepository.save(ftthCheckerUser.getLastPosition());
		return new ResponseEntity<>(new FtthCheckerUserDto(ftthCheckerUserRepository.save(ftthCheckerUser)),
				HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET, path = "/getLastLocation/{username}")
	public ResponseEntity<FtthCheckerUserDto> getLastLocation(@PathVariable("username") String username) {
		FtthCheckerUser ftthCheckerUser = ftthCheckerUserRepository.findByUsername(username);
		FtthCheckerUserDto ftthCheckerUserDto = FtthCheckerUserDto.builder().id(ftthCheckerUser.getId())
				.ftthCheckerUserRole(ftthCheckerUser.getFtthUserRole())
				.lastPosition(new LatLngDto(ftthCheckerUser.getLastPosition())).username(ftthCheckerUser.getUsername())
				.build();
		return new ResponseEntity<>(ftthCheckerUserDto, HttpStatus.OK);
	}
}
