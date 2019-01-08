
package controllers.handyWorker;

import java.util.ArrayList;
import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.HandyWorkerService;
import services.SectionService;
import services.TutorialService;
import controllers.AbstractController;
import domain.HandyWorker;
import domain.Section;
import domain.Tutorial;
import forms.SectionForm;

@Controller
@RequestMapping("/section/handyworker")
public class HandyWorkerSectionController extends AbstractController {

	// Services -------------------------------------------------------------

	// @Autowired
	// private UserService userService;

	@Autowired
	private Validator			validator;

	@Autowired
	private TutorialService		tutorialService;

	@Autowired
	private SectionService		sectionService;

	@Autowired
	private HandyWorkerService	handyWorkerService;


	// Constructors ---------------------------------------------------------

	public HandyWorkerSectionController() {
		super();
	}

	// Listing --------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam int tutorialId) {
		HandyWorker hw = this.handyWorkerService.findByPrincipal();
		Tutorial tut = this.tutorialService.findOne(tutorialId);
		Assert.notNull(tut);
		Assert.isTrue(hw.getTutorials().contains(tut));
		ModelAndView result;
		Collection<Section> sections = tut.getSections();

		result = new ModelAndView("section/list");

		result.addObject("sections", sections);

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam int tutorialId) {

		Section section = this.sectionService.create();

		final ModelAndView result = this.createEditModelAndView(this.construct(section, tutorialId));

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int sectionId) {

		HandyWorker principal = this.handyWorkerService.findByPrincipal();
		Section section = this.sectionService.findOne(sectionId);
		Assert.notNull(section);
		Tutorial current = new Tutorial();
		for (Tutorial t : principal.getTutorials())
			if (t.getSections().contains(section))
				current = t;
		Assert.notNull(current);

		final ModelAndView result = this.createEditModelAndView(this.construct(section, current.getId()));

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final SectionForm sectionForm, final BindingResult binding) {

		ModelAndView result;
		if (binding.hasErrors())
			result = this.createEditModelAndView(sectionForm);
		else
			try {
				Section section = this.reconstruct(sectionForm, binding);
				section = this.sectionService.save(section);
				this.tutorialService.addSection(this.tutorialService.findOne(sectionForm.getTutorialId()), section);
				result = new ModelAndView("redirect:/tutorial/display.do?tutorialId=" + sectionForm.getTutorialId());
			} catch (final Throwable oops) {
				String msg = oops.getMessage();
				if (msg == null)
					msg = "tutorial.commit.error";
				result = this.createEditModelAndView(sectionForm, msg);
			}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final SectionForm sectionForm, final BindingResult binding) {

		ModelAndView result;
		if (binding.hasErrors())
			result = this.createEditModelAndView(sectionForm);
		else
			try {
				Section section = this.reconstruct(sectionForm, binding);
				this.sectionService.delete(section);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(sectionForm, "tutorial.commit.error");
			}
		return result;
	}

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(SectionForm section) {

		ModelAndView result;

		result = this.createEditModelAndView(section, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(SectionForm section, final String messageCode) {

		ModelAndView result;
		result = new ModelAndView("section/edit");
		result.addObject("section", section);
		result.addObject("message", messageCode);

		return result;
	}

	protected SectionForm construct(Section section, int tutorialId) {
		SectionForm ans = new SectionForm();
		ans.setId(section.getId());
		ans.setNumber(section.getNumber());
		String pics = "";
		for (String s : section.getPictures())
			pics += s + "\n";
		ans.setPictures(pics);
		ans.setText(section.getText());
		ans.setTitle(section.getTitle());
		ans.setTutorialId(tutorialId);
		return ans;
	}

	protected Section reconstruct(SectionForm sf, BindingResult binding) {
		Section ans = new Section();
		ans.setId(sf.getId());
		int version = 0;
		Section temp = this.sectionService.findOne(sf.getId());
		if (temp != null)
			version = temp.getVersion();
		ans.setNumber(sf.getNumber());
		ArrayList<String> pics = new ArrayList<String>();
		for (String s : sf.getPictures().split("\n"))
			pics.add(s);
		ans.setPictures(pics);
		ans.setText(sf.getText());
		ans.setTitle(sf.getTitle());
		ans.setVersion(version);
		if (binding != null)
			this.validator.validate(ans, binding);
		return ans;
	}

}
