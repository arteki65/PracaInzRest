package pl.aptewicz.ftthchecker.contorller.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/ftthJob")
public class FtthJobController {

	@RequestMapping(method = RequestMethod.GET)
	public String getCreateJobForUserTemplate(Model model,
			@RequestParam(name = "ftthCheckerUsername") String ftthCheckerUsername) {
		model.addAttribute("ftthCheckerUsername", ftthCheckerUsername);
		return "ftthJob";
	}
}
