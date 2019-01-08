
package controllers.sponsor;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.HandyWorkerService;
import services.SponsorService;
import services.SponsorshipService;
import services.TutorialService;
import controllers.AbstractController;
import domain.Sponsor;
import domain.Sponsorship;
import domain.Tutorial;

@Controller
@RequestMapping("/sponsorship/sponsor")
public class SponsorSponsorshipController extends AbstractController {

	// Services -------------------------------------------------------------

	// @Autowired
	// private UserService userService;

	@Autowired
	private TutorialService		tutorialService;

	@Autowired
	private SponsorshipService	sponsorshipService;

	@Autowired
	private SponsorService		sponsorService;

	@Autowired
	private HandyWorkerService	handyWorkerService;


	// Constructors ---------------------------------------------------------

	public SponsorSponsorshipController() {
		super();
	}

	// Listing --------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Sponsor s = this.sponsorService.findByPrincipal();
		Collection<Sponsorship> sponsorships = s.getSponsorships();

		result = new ModelAndView("sponsorship/list");

		result.addObject("sponsorship", sponsorships);
		result.addObject("requestURI", "sponsorship/sponsor/list.do");

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam(required = false) Integer tutorialId) {

		Sponsorship sponsorship = this.sponsorshipService.create();
		Tutorial chosen = null;
		if (tutorialId != null)
			chosen = this.tutorialService.findOne(tutorialId);

		final ModelAndView result = this.createEditModelAndView(sponsorship, chosen, null);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Sponsorship sponsorship, final BindingResult binding) {

		ModelAndView result;
		if (binding.hasErrors())
			result = this.createEditModelAndView(sponsorship);
		else
			try {
				this.sponsorshipService.save(sponsorship);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				String msg = oops.getMessage();
				if (msg == null)
					msg = "tutorial.commit.error";
				result = this.createEditModelAndView(sponsorship, msg);
			}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final Sponsorship sponsorship, final BindingResult binding) {

		ModelAndView result;
		if (binding.hasErrors())
			result = this.createEditModelAndView(sponsorship);
		else
			try {
				this.sponsorshipService.delete(sponsorship);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(sponsorship, "tutorial.commit.error");
			}
		return result;
	}

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(Sponsorship sponsorship) {

		ModelAndView result;

		result = this.createEditModelAndView(sponsorship, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(Sponsorship sponsorship, final String messageCode) {

		ModelAndView result;
		result = new ModelAndView("sponsorship/edit");
		result.addObject("sponsorship", sponsorship);
		result.addObject("tutorials", this.tutorialService.findAll());
		result.addObject("message", messageCode);

		return result;
	}

	protected ModelAndView createEditModelAndView(Sponsorship sponsorship, Tutorial chosen, final String messageCode) {

		ModelAndView result;
		result = new ModelAndView("sponsorship/edit");
		result.addObject("sponsorship", sponsorship);
		result.addObject("chosenTutorial", chosen);
		result.addObject("message", messageCode);

		return result;
	}

}
