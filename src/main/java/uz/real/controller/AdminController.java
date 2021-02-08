package uz.real.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import uz.real.entity.Role;
import uz.real.entity.User;
import uz.real.model.Result;
import uz.real.repository.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class AdminController {


    @Autowired
    TeacherRepository teacherRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepositroy roleRepositroy;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    LessonScheduleRepository lessonScheduleRepository;

    @Autowired
    NewsRepository newsRepository;


//    private static String upload_Folder="E:\\newSpring/app-spring-security-lesson1/src/main/resources/static/assets/images/";

    @GetMapping("/login")
    public String openAdminLoginPage(@RequestParam(defaultValue = "false") boolean failed, Model model){
            model.addAttribute("failed", failed);
            return "admin/pages/auth/login";
    }


   @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/home")
    public String openIndexAdminPage(Model model){
        model.addAttribute("userLit", userRepository.findAll());
        model.addAttribute("lessonList", lessonScheduleRepository.findAll());
        model.addAttribute("teacherList",teacherRepository.findAll());
        model.addAttribute("newsList",newsRepository.findAll());
        return "admin/index";
    }


//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/users")
    public String openRegisterPage(Model model){
        model.addAttribute("userList", userRepository.findAll());
        model.addAttribute("teacherList", teacherRepository.findAll());
        model.addAttribute("roleList", roleRepositroy.findAll());
        return "admin/pages/content/users";
    }
    @PostMapping("/save/user")
    @ResponseBody
    public Result saveUser(@RequestParam(name = "full_name") String full_name,
                           @RequestParam(name = "username") String username,
                           @RequestParam(name = "password") String password,
                           @RequestParam(name = "age") Integer age,
                           @RequestParam(name = "role") List<Role> role){
        User user=new User();
        user.setFull_name(full_name);
        user.setUsername(username);
        user.setAge(age);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(role);
        userRepository.save(user);
        return new Result(true, "Successfully saved user!");
    }

    @GetMapping("/get/user/{id}")
    @ResponseBody
    public User getUserbyId(@PathVariable Long id){
        Optional<User> byId = userRepository.findById(id);
        if (byId.isPresent()){
            return byId.get();
        }return null;
    }
    @PutMapping("/edit/user/{id}")
    @ResponseBody
    public Result editUser(@PathVariable Long id,
                           @RequestParam(name = "full_name") String full_name,
                           @RequestParam(name = "username") String username,
                           @RequestParam(name = "password") String password,
                           @RequestParam(name = "age") Integer age,
                           @RequestParam(name = "role") List<Role> role){
        Optional<User> byId = userRepository.findById(id);
        if (byId.isPresent()){
            User user = byId.get();
            user.setId(id);
            user.setFull_name(full_name);
            user.setUsername(username);
            user.setPassword(passwordEncoder.encode(password));
            user.setAge(age);
            user.setRole(role);
//            user.setRoles(roleRepositroy.findAllByRoleName("ROLE_USER"));
            userRepository.save(user);
            return new Result(true, "Successfully edited user");
        }return new Result(false, "USer not found");
    }
    @DeleteMapping("/delete/user/{id}")
    @ResponseBody
    public Result deleteUser(@PathVariable Long id){
        Optional<User> byId = userRepository.findById(id);
        if (byId.isPresent()){
            userRepository.delete(byId.get());
            return new Result(true, "Successfully deleted user");
        }return new Result(false, "USer not found");

    }
}
