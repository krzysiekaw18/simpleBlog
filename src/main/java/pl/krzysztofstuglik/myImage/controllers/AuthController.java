package pl.krzysztofstuglik.myImage.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.krzysztofstuglik.myImage.models.forms.RegisterForm;
import pl.krzysztofstuglik.myImage.models.services.AuthService;
import pl.krzysztofstuglik.myImage.models.services.SessionService;

import javax.validation.Valid;

@Controller
public class AuthController {

    private final AuthService authService;
    private final SessionService sessionService;

    @Autowired
    public AuthController(AuthService authService, SessionService sessionService) {
        this.authService = authService;
        this.sessionService = sessionService;
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("userObject", sessionService);
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        Model model) {
        if (!authService.tryToLogin(username, password)) {
            model.addAttribute("loginError", "Niepoprawny login lub hasło");
            model.addAttribute("userObject", sessionService);
            return "login";
        }
        return "redirect:/home";
    }

    @GetMapping("/logout")
    public String logout(RedirectAttributes redirectAttributes) {
        sessionService.setLogin(false);
        sessionService.setUserEntity(null);
        redirectAttributes.addFlashAttribute("infoLogout", "Zostałeś wylogowany");
        return "redirect:/BestBlog";
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("registerForm", new RegisterForm());
        model.addAttribute("userObject", sessionService);
        return "register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute("registerForm") @Valid RegisterForm registerForm,
                           BindingResult bindingResult,
                           RedirectAttributes redirectAttributes,
                           Model model) {
        model.addAttribute("userObject", sessionService);
        if (authService.findByUsername(registerForm.getUsername()).isPresent()) {
            bindingResult.rejectValue("username", "error.registerForm", "Podany login jest już zajęty");
            return "register";
        }
        if (authService.findByEmail(registerForm.getEmail()).isPresent()) {
            bindingResult.rejectValue("email", "error.registerForm", "Podany e-mail jest już zajęty");
            return "register";
        }
        if (!registerForm.getPassword().equals(registerForm.getPasswordRepeat())) {
            bindingResult.rejectValue("passwordRepeat", "error.registerForm", "Wprowadzone hasła nie są takie same");
            return "register";
        }
        if (!bindingResult.hasErrors()) {
            authService.save(registerForm);
            redirectAttributes.addFlashAttribute("registerConfirm", "Zostałeś zarejestrowany, teraz możesz się zalogować");
        }
        return "redirect:/login";
    }
}

