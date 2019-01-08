
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import domain.Sponsorship;

public interface SponsorshipRepository extends JpaRepository<Sponsorship, Integer> {

	@Query("select s from Sponsorship s where s.tutorial.id = ?1")
	Collection<Sponsorship> findAllForTutorialId(int id);

}
