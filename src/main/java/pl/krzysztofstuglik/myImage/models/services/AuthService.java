package pl.krzysztofstuglik.myImage.models.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.krzysztofstuglik.myImage.models.UserEntity;
import pl.krzysztofstuglik.myImage.models.forms.RegisterForm;
import pl.krzysztofstuglik.myImage.models.repositories.UserRepository;

import javax.jws.soap.SOAPBinding;
import java.util.Optional;

@Service
public class
AuthService {

    private final UserRepository userRepository;
    private final SessionService sessionService;

    @Autowired
    public AuthService(UserRepository userRepository, SessionService sessionService) {
        this.userRepository = userRepository;
        this.sessionService = sessionService;
    }

    public boolean tryToLogin(String username, String password) {
        Optional<UserEntity> userEntity = userRepository.findByUsernameAndPassword(username, password);
        if (userEntity.isPresent()){
            sessionService.setLogin(true);
            sessionService.setUserEntity(userEntity.get());
        }
        return userEntity.isPresent();
    }

    public Optional<UserEntity> findByUsername(String username){
        return userRepository.findByUsername(username);
    }

    public Optional<UserEntity> findByEmail(String email){
        return userRepository.findByEmail(email);
    }

    public UserEntity save(RegisterForm registerForm){
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(registerForm.getUsername());
        userEntity.setEmail(registerForm.getEmail());
        userEntity.setPassword(registerForm.getPassword());
        return userRepository.save(userEntity);
    }

    public Iterable<UserEntity> getAllUsers(){
        return userRepository.findAll();
    }

    public boolean tryToRegister(RegisterForm registerForm){
        if (userRepository.existsByEmail(registerForm.getEmail()) || userRepository.existsByUsername(registerForm.getUsername())){
            return false;
        }
        UserEntity userEntity = save(registerForm);
        userRepository.save(userEntity);
        return true;
    }

}
