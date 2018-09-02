package pl.krzysztofstuglik.myImage.controllers.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.krzysztofstuglik.myImage.models.PostEntity;
import pl.krzysztofstuglik.myImage.models.services.PostService;

@RestController
@RequestMapping("rest")
public class PostRestController {

    private final PostService postService;

    @Autowired
    public PostRestController(PostService postService) {
        this.postService = postService;
    }

    @Value("${api.key}")
    String apiKey;

    @GetMapping(value = "/post", produces = "application/json")
    public ResponseEntity allPosts(@RequestHeader("Api-key") String key) {
        if (!key.equals(apiKey)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Key do not exist");
        }
        return ResponseEntity.ok(postService.getAllPosts());
    }

    @GetMapping(value = "/post/{id}", produces = "application/json")
    public ResponseEntity singlePost(@PathVariable("id") int id,
                                     @RequestHeader("Api-key") String key) {
        if (!key.equals(apiKey)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Key do not exist");
        }
        return postService.getPostById(id)
                .map(s -> ResponseEntity.ok(s))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping(value = "/post", consumes = "application/json")
    public ResponseEntity savePost(@RequestBody PostEntity postEntity,
                                   @RequestHeader("Api-key") String key) {
        if (!key.equals(apiKey)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Key do not exist");
        }
        System.out.println(postEntity);
        postService.savePost(postEntity);
        return ResponseEntity.ok(postEntity);
    }

    @DeleteMapping(value = "/post/{id}", produces = "application/json")
    public ResponseEntity deletePost(@PathVariable("id") int id,
                                     @RequestHeader("Api-key") String key) {
        if (!key.equals(apiKey)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Key do not exist");
        }
        postService.deletePostById(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping(value = "/post", consumes = "application/json")
    public ResponseEntity updatePost(@RequestBody PostEntity postEntity,
                                     @RequestHeader("Api-key") String key) {
        if (!key.equals(apiKey)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Key do not exist");
        }
        postService.savePost(postEntity);
        return ResponseEntity.ok(postEntity);
    }


}