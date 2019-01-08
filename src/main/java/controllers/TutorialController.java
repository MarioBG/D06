
package controllers;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.HandyWorkerService;
import services.SponsorshipService;
import services.TutorialService;
import domain.HandyWorker;
import domain.Tutorial;

@Controller
@RequestMapping("/tutorial")
public class TutorialController extends AbstractController {

	// Services -------------------------------------------------------------

	// @Autowired
	// private UserService userService;

	@Autowired
	private TutorialService		tutorialService;

	@Autowired
	private SponsorshipService	sponsorshipService;

	@Autowired
	private HandyWorkerService	handyWorkerService;


	// Constructors ---------------------------------------------------------

	public TutorialController() {
		super();
	}

	// Listing --------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam(required = false) Integer handyWorkerId) {
		ModelAndView result;
		Collection<Tutorial> tutorials;
		if (handyWorkerId == null)
			tutorials = this.tutorialService.findAll();
		else {
			HandyWorker tmp = this.handyWorkerService.findOne(handyWorkerId);
			tutorials = tmp.getTutorials();
		}

		Date date = new Date();

		result = new ModelAndView("tutorial/list");

		result.addObject("tutorials", tutorials);
		result.addObject("date", date);

		return result;
	}

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam int tutorialId) {
		ModelAndView result;
		HandyWorker hw = this.handyWorkerService.findByPrincipal();
		Tutorial tutorial = this.tutorialService.findOne(tutorialId);
		boolean isOwner;
		if (hw != null && hw.getTutorials().contains(tutorial))
			isOwner = true;
		else
			isOwner = false;

		result = new ModelAndView("tutorial/display");

		result.addObject("isNull", tutorial == null);
		result.addObject("isOwner", isOwner);
		result.addObject("advertisement", this.sponsorshipService.findRandomForTutorial(tutorial));
		result.addObject("tutorial", tutorial);
		if (tutorial != null)
			result.addObject("sections", tutorial.getSections());

		return result;
	}

}
