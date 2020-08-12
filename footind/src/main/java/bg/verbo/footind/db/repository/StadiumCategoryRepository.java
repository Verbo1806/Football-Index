package bg.verbo.footind.db.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import bg.verbo.footind.db.entity.StadiumCategory;

@Repository
public interface StadiumCategoryRepository extends JpaRepository<StadiumCategory, String> {
	List<StadiumCategory> findByCodeAndName(String code, String name);
}
