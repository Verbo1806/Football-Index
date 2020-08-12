package bg.verbo.footind.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import bg.verbo.footind.db.entity.Ground;

@Repository
public interface GroundRepository extends JpaRepository<Ground, Long> {

}
