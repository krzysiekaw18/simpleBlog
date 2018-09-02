package pl.krzysztofstuglik.myImage.models.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import pl.krzysztofstuglik.myImage.models.PostEntity;
import pl.krzysztofstuglik.myImage.models.UserEntity;
import pl.krzysztofstuglik.myImage.models.forms.PostForm;
import pl.krzysztofstuglik.myImage.models.repositories.PostRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final SessionService sessionService;

    @Autowired
    public PostService(PostRepository postRepository, SessionService sessionService) {
        this.postRepository = postRepository;
        this.sessionService = sessionService;
    }

    public void addPost(PostForm postForm) {
        PostEntity postEntity = createEntityFromForm(postForm);
        postRepository.save(postEntity);
    }

    private PostEntity createEntityFromForm(PostForm postForm) {
        PostEntity postEntity = new PostEntity();
        postEntity.setId(postForm.getId());
        postEntity.setTitle(postForm.getTitle());
        postEntity.setContent(postForm.getContent());
        postEntity.setUser(sessionService.getUserEntity());
        return postEntity;
    }

    public List<PostEntity> getAllPostByLoggedUser(){
        return postRepository.findAllByUser_Id(sessionService.getUserEntity().getId());
    }

    public PostEntity getAllPostData(int id) {
        return postRepository.findById(id).get();
    }

    public Optional<PostEntity> getPost(int id) {
        return postRepository.findById(id);
    }

    public PostForm updatePost(Optional<PostEntity> entityOptional) {
        PostForm postForm = new PostForm();
        postForm.setTitle(entityOptional.get().getTitle());
        postForm.setContent(entityOptional.get().getContent());
        return postForm;
    }

    public void deletePost(int postId) {
        postRepository.deleteById(postId);
    }


    public Page<PostEntity> findAllOrderedByDataPageable(int page) {
        return postRepository.findAllByOrderByIdDesc(PageRequest.of(subtractPageByOne(page), 5));
    }

    private int subtractPageByOne(int page) {
        return (page < 1) ? 0 : page - 1;
    }

    public Page<PostEntity> findByUserOrderedByDatePageable(UserEntity userEntity, int page) {
        return postRepository.findByUserOrderByIdDesc(userEntity, (PageRequest.of(subtractPageByOne(page), 5)));
    }

    public void growingNumberOfComments(int postId){
        PostEntity postEntity = getAllPostData(postId);
        postEntity.setNumberOfComments(postEntity.getNumberOfComments() + 1);
        postRepository.save(postEntity);
    }

    public void decliningNumberOfComments(int postId) {
        PostEntity postEntity = getAllPostData(postId);
        postEntity.setNumberOfComments(postEntity.getNumberOfComments() - 1);
        postRepository.save(postEntity);
    }

}
