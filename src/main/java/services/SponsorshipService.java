
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.SponsorshipRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Sponsorship;
import domain.Tutorial;

@Service
@Transactional
public class SponsorshipService {

	@Autowired
	private SponsorshipRepository	sponsorshipRepository;

	@Autowired
	private SponsorService			sponsorService;


	public Sponsorship save(final Sponsorship sponsorship) {
		Sponsorship result, saved;
		UserAccount loggedUserAccount;
		Authority authority;
		authority = new Authority();
		authority.setAuthority(Authority.SPONSOR);
		loggedUserAccount = LoginService.getPrincipal();
		Assert.notNull(sponsorship.getCreditCard());
		if (this.exists(sponsorship.getId()) && (loggedUserAccount.getAuthorities().contains(authority))) {
			saved = this.sponsorshipRepository.findOne(sponsorship.getId());
			Assert.notNull(saved);
		}

		result = this.sponsorshipRepository.save(sponsorship);
		Assert.notNull(result);
		return result;
	}

	public List<Sponsorship> findAll() {
		return this.sponsorshipRepository.findAll();
	}

	public Sponsorship findOne(final Integer sponsorshipId) {
		Assert.isTrue(this.exists(sponsorshipId));
		return this.sponsorshipRepository.findOne(sponsorshipId);
	}

	public boolean exists(final Integer sponsorshipId) {
		return this.sponsorshipRepository.exists(sponsorshipId);
	}

	public void delete(final Sponsorship sponsorship) {
		UserAccount loggedUserAccount;
		Authority authority;
		authority = new Authority();
		authority.setAuthority(Authority.SPONSOR);
		loggedUserAccount = LoginService.getPrincipal();
		Assert.isTrue(loggedUserAccount.getAuthorities().contains(authority));
		Assert.isTrue(this.sponsorService.findByPrincipal().getSponsorships().contains(sponsorship));
		this.sponsorService.removeSponsorshipWhereBelongs(sponsorship);
		this.sponsorshipRepository.delete(sponsorship);
	}

	public void deleteForDeletingTutorials(final Sponsorship sponsorship) {
		UserAccount loggedUserAccount;
		Authority authority;
		authority = new Authority();
		authority.setAuthority(Authority.HANDYWORKER);
		loggedUserAccount = LoginService.getPrincipal();
		Assert.isTrue(loggedUserAccount.getAuthorities().contains(authority));
		this.sponsorService.removeSponsorshipWhereBelongs(sponsorship);
		this.sponsorshipRepository.delete(sponsorship);
	}

	public Sponsorship findRandomForTutorial(final Tutorial tutorial) {
		Random rnd = new Random();
		Collection<Sponsorship> from = this.sponsorshipRepository.findAll();
		Collection<Sponsorship> ans = new ArrayList<Sponsorship>();
		for (Sponsorship s : from)
			if (s.getTutorial().equals(tutorial))
				ans.add(s);
		if (ans.size() > 0)
			return (Sponsorship) ans.toArray()[rnd.nextInt(ans.size())];
		else
			return null;
	}

	public Collection<Sponsorship> findAllForTutorialId(int id) {
		return this.sponsorshipRepository.findAllForTutorialId(id);
	}

	public Sponsorship create() {
		Sponsorship result;

		result = new Sponsorship();

		return result;
	}
}
