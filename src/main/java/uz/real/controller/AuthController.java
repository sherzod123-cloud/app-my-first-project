package uz.real.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import uz.real.entity.enums.RoleName;
import uz.real.entity.User;
import uz.real.payload.ReqUser;
import uz.real.repository.RoleRepositroy;
import uz.real.repository.UserRepository;

import java.util.Optional;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepositroy roleRepositroy;

    @GetMapping("/signup")
    public String openSignUpPage(){
        return "signup";
    }
    @GetMapping("/signin")
    public String openSigninPage(){
        return "signin";
    }

    @PostMapping("/signin")
    public String Signin(Model model, @RequestParam(name = "username") String username,
                         @RequestParam(name = "password") String password){
        Optional<User> byUsername = userRepository.findByUsername(username);
        if (byUsername.isPresent()){
            User user = byUsername.get();
            if (passwordEncoder.matches(password, user.getPassword())){
                return "redirect:/";
            }model.addAttribute("passwordError", "Password correct!");
            return "signin";
        }
        model.addAttribute("usernameError", "Username is not found! Please register");
        return "admin/pages/content/signin";
    }
    @PostMapping("/signup")
    public String addUserMethod(@ModelAttribute ReqUser reqUser){
        userRepository.save(new User(
                reqUser.getFullName(),
                reqUser.getUsername(),
                passwordEncoder.encode(reqUser.getPassword()),
                reqUser.getAge(),
                roleRepositroy.findAllByRoleName("ROLE_USER")));
        return "redirect:/auth/signin";
    }

}
