package HYlikelion.gitppo.repository;

import HYlikelion.gitppo.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUserToken(String userToken);
    Boolean existsByUserToken(String userToken);

}
