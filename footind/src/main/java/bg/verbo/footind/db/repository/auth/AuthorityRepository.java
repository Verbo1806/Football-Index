package bg.verbo.footind.db.repository.auth;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import bg.verbo.footind.db.entity.auth.Authority;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, Long> {

}
