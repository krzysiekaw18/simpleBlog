package pl.krzysztofstuglik.myImage.models.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.krzysztofstuglik.myImage.models.UserEntity;
import pl.krzysztofstuglik.myImage.models.repositories.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final SessionService sessionService;

    @Autowired
    public UserService(UserRepository userRepository, SessionService sessionService) {
        this.userRepository = userRepository;
        this.sessionService = sessionService;
    }

    public void growingNumberOfUserPosts(){
        UserEntity userEntity = getAllInfoAboutUser();
        userEntity.setNumberUserPosts(userEntity.getNumberUserPosts() + 1);
        userRepository.save(userEntity);
    }

    public void decliningNumberOfUserPosts(){
        UserEntity userEntity = getAllInfoAboutUser();
        userEntity.setNumberUserPosts(userEntity.getNumberUserPosts() - 1);
        userRepository. save(userEntity);
    }

    public void growingNumberOfUserComments(){
        UserEntity userEntity = getAllInfoAboutUser();
        userEntity.setNumberUserComments(userEntity.getNumberUserComments() + 1);
        userRepository.save(userEntity);
    }

    public void decliningNumberOfUserComments(){
        UserEntity userEntity = getAllInfoAboutUser();
        userEntity.setNumberUserComments(userEntity.getNumberUserComments() - 1);
        userRepository.save(userEntity);
    }

    private UserEntity getAllInfoAboutUser() {
        return userRepository.findById(sessionService.getUserEntity().getId()).get();
    }
}
