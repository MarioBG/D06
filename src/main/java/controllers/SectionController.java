
package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.HandyWorkerService;
import services.SectionService;
import services.TutorialService;
import domain.Section;
import domain.Tutorial;

@Controller
@RequestMapping("/section")
public class SectionController extends AbstractController {

	// Services -------------------------------------------------------------

	// @Autowired
	// private UserService userService;

	@Autowired
	private TutorialService		tutorialService;

	@Autowired
	private SectionService		sectionService;

	@Autowired
	private HandyWorkerService	handyWorkerService;


	// Constructors ---------------------------------------------------------

	public SectionController() {
		super();
	}

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam int sectionId) {
		ModelAndView result;
		Section section = this.sectionService.findOne(sectionId);
		Tutorial tutorial = this.tutorialService.findForSectionId(sectionId);

		result = new ModelAndView("section/display");

		result.addObject("section", section);
		result.addObject("tutorial", tutorial);

		return result;
	}

}
