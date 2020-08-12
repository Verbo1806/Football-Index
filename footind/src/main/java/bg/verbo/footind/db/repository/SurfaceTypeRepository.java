package bg.verbo.footind.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import bg.verbo.footind.db.entity.SurfaceType;

@Repository
public interface SurfaceTypeRepository extends JpaRepository<SurfaceType, String> {

}
