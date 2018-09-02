package pl.krzysztofstuglik.myImage.models.repositories;

import org.springframework.data.repository.CrudRepository;
import pl.krzysztofstuglik.myImage.models.CommentEntity;

import java.util.List;

public interface CommentRepository extends CrudRepository<CommentEntity, Integer> {
}
