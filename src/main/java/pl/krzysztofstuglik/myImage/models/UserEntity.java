package pl.krzysztofstuglik.myImage.models;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user")
@Data
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String username;
    private String email;
    private String password;

    @Column(name = "user_comments")
    private int numberUserComments;

    @Column(name = "user_posts")
    private int numberUserPosts;

    @Column(name = "register_date")
    private LocalDateTime registerDate;


    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<PostEntity> posts;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<CommentEntity> comments;
}
