package bg.verbo.footind.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import bg.verbo.footind.db.entity.League;

@Repository
public interface LeagueRepository extends JpaRepository<League, Long> {

}
