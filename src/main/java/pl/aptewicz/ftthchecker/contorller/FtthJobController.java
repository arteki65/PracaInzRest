package pl.aptewicz.ftthchecker.contorller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.aptewicz.ftthchecker.domain.FtthCheckerUser;
import pl.aptewicz.ftthchecker.domain.FtthJob;
import pl.aptewicz.ftthchecker.domain.LatLng;
import pl.aptewicz.ftthchecker.dto.FtthJobDto;
import pl.aptewicz.ftthchecker.exception.FtthRestApiException;
import pl.aptewicz.ftthchecker.exception.FtthRestApiExceptionConstants;
import pl.aptewicz.ftthchecker.repository.FtthCheckerUserRepository;
import pl.aptewicz.ftthchecker.repository.FtthJobRepository;
import pl.aptewicz.ftthchecker.service.DistanceService;

import java.util.Collection;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/ftthJob")
public class FtthJobController {

	private final FtthJobRepository ftthJobRepository;

	private final FtthCheckerUserRepository ftthCheckerUserRepository;

	private final DistanceService distanceService;

	@Autowired
	public FtthJobController(FtthJobRepository ftthJobRepository, FtthCheckerUserRepository ftthCheckerUserRepository,
			DistanceService distanceService) {
		this.ftthJobRepository = ftthJobRepository;
		this.ftthCheckerUserRepository = ftthCheckerUserRepository;
		this.distanceService = distanceService;
	}

	@RequestMapping(method = RequestMethod.PUT)
	public ResponseEntity<FtthJobDto> createFtthJob(@RequestBody FtthJobDto ftthJobDto) {
		FtthJob ftthJob = new FtthJob(ftthJobDto);
		ftthJob.setFtthCheckerUser(ftthCheckerUserRepository.findByUsername(ftthJobDto.getServicemanUsername()));
		return new ResponseEntity<>(new FtthJobDto(ftthJobRepository.save(ftthJob)), HttpStatus.CREATED);
	}

	@RequestMapping(method = RequestMethod.PUT, path = "/updateStatus")
	public ResponseEntity<FtthJobDto> updateFtthJobD(@RequestBody FtthJobDto ftthJobDto) {
		FtthJob ftthJob = ftthJobRepository.findOne(ftthJobDto.getId());
		LatLng lastPosition = ftthJob.getFtthCheckerUser().getLastPosition();
		if(distanceService.getDistance(lastPosition.getLatitude(), lastPosition.getLongitude(), ftthJob.getFtthIssue
				().getLatitude(), ftthJob.getFtthIssue().getLongitude()) > 0.1) {
			throw new FtthRestApiException(FtthRestApiExceptionConstants.SERVICEMAN_TOO_FAR_FROM_ISSUE_LOCATION);
		}
		ftthJob.setJobStatus(ftthJobDto.getJobStatus());
		return new ResponseEntity<>(new FtthJobDto(ftthJobRepository.save(ftthJob)), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET, path = "/{servicemanUsername}")
	public ResponseEntity<Collection<FtthJobDto>> getFtthJobsForServiceman(@PathVariable String servicemanUsername) {
		FtthCheckerUser serviceman = ftthCheckerUserRepository.findByUsername(servicemanUsername);
		return new ResponseEntity<>(ftthJobRepository.findByFtthCheckerUser(serviceman).stream().map(FtthJobDto::new)
				.collect(Collectors.toList()), HttpStatus.OK);
	}
}
