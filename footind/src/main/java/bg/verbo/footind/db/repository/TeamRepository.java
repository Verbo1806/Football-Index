package bg.verbo.footind.db.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import bg.verbo.footind.db.entity.Team;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {

	@Query(value = "SELECT t FROM Team t WHERE (t.name LIKE CONCAT('%', ?1, '%') and ?1 is not null) and (t.league.name = ?1 and ?1 is not null)")
	List<Team> findByNameAndLeagueName(String name, String leagueName);
}
