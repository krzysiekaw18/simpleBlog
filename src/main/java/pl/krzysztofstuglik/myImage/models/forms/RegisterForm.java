package pl.krzysztofstuglik.myImage.models.forms;

import lombok.Data;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;


@Data
public class RegisterForm {
    @Length(min = 5, message = "Nazwa użytkownika musi mieć minimum 5 znaków")
    @Length(max = 20, message = "Nazwa użytkownika nie może mieć więcej niż 20 znaków")
    @NotEmpty(message = "Wprowadź nazwę użytkonika")
    private String username;
    @Email(message = "Proszę podać poprawną formę e-maila")
    @NotEmpty(message = "Proszę wprowadzić e-mail")
    private String email;
    @Length(min = 5, message = "Twoje hasło powinno składać się z minimum 5 znaków")
    @NotEmpty(message = "Proszę wprowadzić hasło")
    private String password;
    @NotEmpty(message = "Proszę powtórzyć hasło")
    private String passwordRepeat;
}
