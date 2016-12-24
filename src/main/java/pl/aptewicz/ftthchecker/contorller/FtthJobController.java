package pl.aptewicz.ftthchecker.contorller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.aptewicz.ftthchecker.domain.FtthCheckerUser;
import pl.aptewicz.ftthchecker.domain.FtthJob;
import pl.aptewicz.ftthchecker.dto.FtthJobDto;
import pl.aptewicz.ftthchecker.repository.FtthCheckerUserRepository;
import pl.aptewicz.ftthchecker.repository.FtthJobRepository;

import java.util.Collection;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/ftthJob")
public class FtthJobController {

	private final FtthJobRepository ftthJobRepository;

	private final FtthCheckerUserRepository ftthCheckerUserRepository;

	@Autowired
	public FtthJobController(FtthJobRepository ftthJobRepository, FtthCheckerUserRepository ftthCheckerUserRepository) {
		this.ftthJobRepository = ftthJobRepository;
		this.ftthCheckerUserRepository = ftthCheckerUserRepository;
	}

	@RequestMapping(method = RequestMethod.PUT)
	public ResponseEntity<FtthJobDto> createFtthJob(@RequestBody FtthJobDto ftthJobDto) {
		FtthJob ftthJob = new FtthJob(ftthJobDto);
		ftthJob.setFtthCheckerUser(ftthCheckerUserRepository.findByUsername(ftthJobDto.getServicemanUsername()));
		return new ResponseEntity<>(new FtthJobDto(ftthJobRepository.save(ftthJob)), HttpStatus.CREATED);
	}

	@RequestMapping(method = RequestMethod.GET, path = "/{servicemanUsername}")
	public ResponseEntity<Collection<FtthJobDto>> getFtthJobsForServiceman(@PathVariable String servicemanUsername) {
		FtthCheckerUser serviceman = ftthCheckerUserRepository.findByUsername(servicemanUsername);
		return new ResponseEntity<>(
				ftthJobRepository.findByFtthCheckerUser(serviceman).stream().map(FtthJobDto::new)
						.collect(Collectors.toList()), HttpStatus.OK);
	}
}
