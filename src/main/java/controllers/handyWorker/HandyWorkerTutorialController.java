
package controllers.handyWorker;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.HandyWorkerService;
import services.TutorialService;
import controllers.AbstractController;
import domain.HandyWorker;
import domain.Tutorial;

@Controller
@RequestMapping("/tutorial/handyworker")
public class HandyWorkerTutorialController extends AbstractController {

	// Services -------------------------------------------------------------

	// @Autowired
	// private UserService userService;

	@Autowired
	private TutorialService		tutorialService;

	@Autowired
	private HandyWorkerService	handyWorkerService;


	// Constructors ---------------------------------------------------------

	public HandyWorkerTutorialController() {
		super();
	}

	// Listing --------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		HandyWorker hw = this.handyWorkerService.findByPrincipal();
		Collection<Tutorial> tutorials = hw.getTutorials();

		result = new ModelAndView("tutorial/list");

		result.addObject("tutorials", tutorials);
		result.addObject("requestURI", "tutorial/handyworker/list.do");

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {

		Tutorial tutorial = this.tutorialService.create();

		final ModelAndView result = this.createEditModelAndView(tutorial);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int tutorialId) {

		HandyWorker principal = this.handyWorkerService.findByPrincipal();
		Tutorial tutorial = this.tutorialService.findOne(tutorialId);
		Assert.notNull(tutorial);
		Assert.isTrue(principal.getTutorials().contains(tutorial));

		final ModelAndView result = this.createEditModelAndView(tutorial);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Tutorial tutorial, final BindingResult binding) {

		ModelAndView result;
		if (binding.hasErrors())
			result = this.createEditModelAndView(tutorial);
		else
			try {
				this.tutorialService.save(tutorial);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				String msg = oops.getMessage();
				if (msg == null)
					msg = "tutorial.commit.error";
				result = this.createEditModelAndView(tutorial, msg);
			}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final Tutorial tutorial, final BindingResult binding) {

		ModelAndView result;
		if (binding.hasErrors())
			result = this.createEditModelAndView(tutorial);
		else
			try {
				this.tutorialService.delete(tutorial);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(tutorial, "tutorial.commit.error");
			}
		return result;
	}

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(Tutorial tutorial) {

		ModelAndView result;

		result = this.createEditModelAndView(tutorial, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(Tutorial tutorial, final String messageCode) {

		ModelAndView result;
		result = new ModelAndView("tutorial/edit");
		result.addObject("tutorial", tutorial);
		result.addObject("message", messageCode);

		return result;
	}

}
