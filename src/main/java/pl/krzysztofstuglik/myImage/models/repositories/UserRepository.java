package pl.krzysztofstuglik.myImage.models.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.krzysztofstuglik.myImage.models.UserEntity;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Integer> {
    Optional<UserEntity> findByUsernameAndPassword(String username, String password);
    Optional<UserEntity> findByUsername(String username);
    Optional<UserEntity> findByEmail(String email);
}
