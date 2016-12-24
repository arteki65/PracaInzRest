package pl.aptewicz.ftthchecker.contorller.mvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.aptewicz.ftthchecker.domain.FtthCheckerUserRole;
import pl.aptewicz.ftthchecker.repository.FtthCheckerUserRepository;

import java.util.stream.Collectors;

@Controller
@RequestMapping("/")
public class HomeController {

	private final FtthCheckerUserRepository ftthCheckerUserRepository;

	@Autowired
	public HomeController(FtthCheckerUserRepository ftthCheckerUserRepository) {
		this.ftthCheckerUserRepository = ftthCheckerUserRepository;
	}

	@RequestMapping(method = RequestMethod.GET)
	public String home(Model model) {
		model.addAttribute("ftthCheckerUsers", ftthCheckerUserRepository.findAll().stream()
				.filter(ftthCheckerUser -> FtthCheckerUserRole.SERVICEMAN.equals(ftthCheckerUser.getFtthUserRole()))
				.collect(Collectors.toList()));
		return "index";
	}

	@RequestMapping(path = "/maps")
	public String map(Model model)	{
		model.addAttribute("ftthCheckerUsers", ftthCheckerUserRepository.findAll().stream()
				.filter(ftthCheckerUser -> FtthCheckerUserRole.SERVICEMAN.equals(ftthCheckerUser.getFtthUserRole()))
				.collect(Collectors.toList()));
		return "map";
	}
}
