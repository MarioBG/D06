
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.TutorialRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.HandyWorker;
import domain.Section;
import domain.Sponsorship;
import domain.Tutorial;

@Service
@Transactional
public class TutorialService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private TutorialRepository	tutorialRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private HandyWorkerService	handyWorkerService;

	@Autowired
	private SponsorshipService	sponsorshipService;

	@Autowired
	private SectionService		sectionService;

	@PersistenceContext
	EntityManager				entitymanager;


	// Simple CRUD methods ----------------------------------------------------

	public Tutorial create() {

		Tutorial tutorial = new Tutorial();

		tutorial.setUpdateTime(new Date(System.currentTimeMillis() - 1000));
		tutorial.setPictures(new ArrayList<String>());
		tutorial.setSections(new ArrayList<Section>());

		return tutorial;
	}

	public Tutorial save(final Tutorial entity) {
		UserAccount loggedUserAccount;
		Authority authority;
		authority = new Authority();
		authority.setAuthority(Authority.HANDYWORKER);
		loggedUserAccount = LoginService.getPrincipal();
		entity.setUpdateTime(new Date(System.currentTimeMillis() - 1));
		Tutorial saved;
		if (entity.getId() != 0) {
			Assert.isTrue(loggedUserAccount.getAuthorities().contains(authority));
			Assert.isTrue(this.handyWorkerService.findByPrincipal().getTutorials().contains(entity));
			saved = this.tutorialRepository.save(entity);
		} else {
			entity.setSections(new ArrayList<Section>());
			saved = this.tutorialRepository.save(entity);
			HandyWorker hw = this.handyWorkerService.findByPrincipal();
			this.handyWorkerService.addTutorial(hw, saved);

		}
		return saved;
	}

	public List<Tutorial> findAll() {
		return this.tutorialRepository.findAll();
	}

	public Tutorial findOne(final Integer id) {
		return this.tutorialRepository.findOne(id);
	}

	public void delete(Tutorial entity) {
		UserAccount loggedUserAccount;
		Authority authority;
		authority = new Authority();
		authority.setAuthority(Authority.HANDYWORKER);
		loggedUserAccount = LoginService.getPrincipal();
		Assert.isTrue(loggedUserAccount.getAuthorities().contains(authority));
		Assert.isTrue(this.handyWorkerService.findByPrincipal().getTutorials().contains(entity));
		entity = this.findOne(entity.getId());
		final Collection<Section> sections = this.sectionService.getSectionsOrderedFromTutorial(entity.getId());
		entity.setSections(new ArrayList<Section>());
		entity = this.tutorialRepository.save(entity);
		this.sectionService.delete(sections);
		this.handyWorkerService.removeTutorial(this.handyWorkerService.findByPrincipal(), entity);
		for (Sponsorship s : this.sponsorshipService.findAllForTutorialId(entity.getId()))
			this.sponsorshipService.deleteForDeletingTutorials(s);
		this.tutorialRepository.delete(entity);
	}

	public Tutorial addSection(final Tutorial tutorial, final Section section) {
		if (!tutorial.getSections().contains(section)) {
			tutorial.setUpdateTime(new Date(System.currentTimeMillis() - 1));
			final List<Section> sections = this.sectionService.getSectionsOrderedFromTutorial(tutorial.getId());
			final int lastIndex = sections.size();
			section.setNumber(lastIndex);
			this.sectionService.save(section);
			sections.add(section);
			tutorial.setSections(sections);
			return this.tutorialRepository.save(tutorial);
		}
		return tutorial;
	}

	public Tutorial removeSection(final Tutorial tutorial, final Section section) {
		if (tutorial.getSections().contains(section)) {
			tutorial.setUpdateTime(new Date(System.currentTimeMillis() - 1));
			final List<Section> sections = this.sectionService.getSectionsOrderedFromTutorial(tutorial.getId());
			final int lastIndex = sections.get(sections.size() - 1).getNumber();
			section.setNumber(lastIndex);
			this.sectionService.save(section);
			sections.remove(section);
			tutorial.setSections(sections);
			return this.tutorialRepository.save(tutorial);
		}
		return tutorial;
	}

	public boolean exists(final Integer id) {
		return this.tutorialRepository.exists(id);
	}

	// Other business methods

	public Collection<Tutorial> findAllHandyWorkerTutorials(final HandyWorker handyWorker) {
		Assert.notNull(handyWorker);
		Assert.isTrue(this.handyWorkerService.exists(handyWorker.getId()));
		Collection<Tutorial> result;
		result = this.tutorialRepository.findAllHandyWorkerTutorialsFromAccountId(handyWorker.getId());

		return result;
	}

	public Tutorial findForSectionId(int sectionId) {
		return this.tutorialRepository.findForSectionId(sectionId);
	}
}
