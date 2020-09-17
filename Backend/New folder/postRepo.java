package com.example.app.repo;

import com.example.app.model.Post;
import org.springframework.data.repository.CrudRepository;
import java.util.List;


public interface postRepo extends CrudRepository<Post,Long> {
    //I have no idea how to actually declare what these do//

    //Find posts by a hashtag
    List<Post> findByHashtag(String hashtag);

    //Sort posts by newest
    List<Post> sortByNew();

    //Sort posts by highest score
    List<Post> sortByHighestScore();
}
