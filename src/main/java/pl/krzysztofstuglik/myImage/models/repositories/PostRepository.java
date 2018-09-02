package pl.krzysztofstuglik.myImage.models.repositories;

import org.hibernate.validator.constraints.EAN;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.krzysztofstuglik.myImage.models.PostEntity;
import pl.krzysztofstuglik.myImage.models.UserEntity;

import java.util.List;

@Repository
public interface PostRepository extends CrudRepository<PostEntity, Integer> {
    List<PostEntity> findAllByOrderByIdDesc();
    List<PostEntity> findAllByUser_Id(int userId);
    List<PostEntity> findAllByUser_Username(String username);
    Page<PostEntity> findAllByOrderByIdDesc(Pageable pageable);
    Page<PostEntity> findByUserOrderByIdDesc(UserEntity userEntity, Pageable pageable);
}
