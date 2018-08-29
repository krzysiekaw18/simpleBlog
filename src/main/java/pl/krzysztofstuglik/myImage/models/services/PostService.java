package pl.krzysztofstuglik.myImage.models.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.krzysztofstuglik.myImage.models.PostEntity;
import pl.krzysztofstuglik.myImage.models.forms.PostForm;
import pl.krzysztofstuglik.myImage.models.repositories.PostRepository;

import java.util.List;
import java.util.Optional;

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
        postEntity.setTitle(postForm.getTitle());
        postEntity.setContent(postForm.getContent());
        postEntity.setUser(sessionService.getUserEntity());
        return postEntity;
    }

    public List<PostEntity> getAll() {
        return postRepository.findAllByOrderByIdDesc();
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
}
