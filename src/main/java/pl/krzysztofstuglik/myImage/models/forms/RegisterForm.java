package pl.krzysztofstuglik.myImage.models.forms;

import lombok.Data;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;

@Data
public class RegisterForm {
    @Length(min = 5, message = "Twój login musi mieć minimum 5 znaków")
    @Length(max = 25, message = "Twój login nie może mieć więcej niż 25 znaków")
    private String username;
    @Email(message = "Proszę podać poprawną formę e-maila")
    private String email;
    @Length(min = 5, message = "Twoje hasło powinno składać się z minium 5 znaków")
    private String password;
    private String passwordRepeat;
}
