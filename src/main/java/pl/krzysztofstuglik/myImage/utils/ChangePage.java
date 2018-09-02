package pl.krzysztofstuglik.myImage.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import pl.krzysztofstuglik.myImage.models.PostEntity;

public class ChangePage {

    private final Page<PostEntity> postEntities;

    @Autowired
    public ChangePage(Page<PostEntity> postEntities) {
        this.postEntities = postEntities;
    }

    public Page<PostEntity> getPostEntities(){
        return postEntities;
    }

    public int getPageIndex(){
        return postEntities.getNumber() + 1;
    }

    public int getPageSize(){
        return postEntities.getSize();
    }

    public boolean hasNext(){
        return postEntities.hasNext();
    }

    public boolean hasPrevious(){
        return postEntities.hasPrevious();
    }

    public int getTotalNumberOfPages(){
        return postEntities.getTotalPages();
    }

    public int getTotalElements(){
        return (int) postEntities.getTotalElements();
    }

    public boolean indexOutOfBounds(){
        return getPageIndex() < 0 || getPageIndex() > (getTotalElements()/5)+1;
    }
}
