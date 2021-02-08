package uz.real.loader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import uz.real.entity.LessonSchedule;
import uz.real.entity.Role;
import uz.real.entity.User;
import uz.real.repository.LessonScheduleRepository;
import uz.real.repository.RoleRepositroy;
import uz.real.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepositroy roleRepositroy;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Value("${spring.datasource.initialization-mode}")
    private String initMode;

    @Autowired
    LessonScheduleRepository lessonScheduleRepository;

    @Override
    public void run(String... args) throws Exception {
        if (initMode.equals("always")){

           userRepository.save(new User(
                   "Super admin",
                   "admin",
                   passwordEncoder.encode("12345"),
                   22,
                   roleRepositroy.findAllByRoleName("ROLE_ADMIN")
           ));
            userRepository.save(new User(
                    "Super user",
                    "user",
                    passwordEncoder.encode("111"),
                    22,
                    roleRepositroy.findAllByRoleName("ROLE_USER")
            ));


//            Lesson Section add to database /**

            List<LessonSchedule> lessonSchedules=new ArrayList<>();
            lessonSchedules.add(new LessonSchedule("Художественно-прикладное творчество"));
            lessonSchedules.add(new LessonSchedule("Техническое творчество"));
            lessonSchedules.add(new LessonSchedule("Художественное творчество"));
            lessonScheduleRepository.saveAll(lessonSchedules);
        }
    }
}
