package pl.krzysztofstuglik.myImage.models.forms;

import lombok.Data;

@Data
public class PostForm {
    private int id;
    private String title;
    private String content;
}
