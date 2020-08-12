package bg.verbo.footind.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import bg.verbo.footind.db.entity.CompetitionType;

@Repository
public interface CompetitionTypeRepository extends JpaRepository<CompetitionType, String> {

}
