package pl.krzysztofstuglik.myImage.models.repositories;

import org.hibernate.validator.constraints.EAN;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.krzysztofstuglik.myImage.models.PostEntity;

import java.util.List;

@Repository
public interface PostRepository extends CrudRepository<PostEntity, Integer> {
    List<PostEntity> findAllByOrderByIdDesc();
}
