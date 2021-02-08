package uz.real.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.real.entity.Teacher;
import uz.real.entity.TeacherImages;
import uz.real.model.Result;
import uz.real.payload.ReqTeacher;
import uz.real.repository.TeacherImagesRepository;
import uz.real.repository.TeacherRepository;
import uz.real.service.TeacherService;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class TeacherController {

    @Autowired
    TeacherImagesRepository teacherImagesRepository;

    @Autowired
    TeacherRepository teacherRepository;

    @Autowired
    TeacherService teacherService;

    @GetMapping("/teacher")
    public String openTeacherPage(Model model){
        model.addAttribute("teachers", teacherRepository.findAll());
        model.addAttribute("teacherList", teacherRepository.findAll());
        return "admin/pages/content/teacher";
    }
    @GetMapping("/teacher/get/{id}")
    @ResponseBody
    public Teacher getTeacherById(@PathVariable Integer id){
        return teacherService.getTeacherById(id);
    }


//    @PreAuthorize("hasAuthority('/admin/teacher')")
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/teacher")
    @ResponseBody
    public Result saveTeacher(@ModelAttribute ReqTeacher reqTeacher, BindingResult bindingResult){
        return teacherService.saveTeacher(reqTeacher, bindingResult);
    }
    @PostMapping("/teacher/saveFile")
    @ResponseBody
    public TeacherImages uploadImage(@RequestParam(name = "file") MultipartFile file){
        return teacherService.saveFileToServer(file);
    }


    @DeleteMapping("/teacher/delete/{id}")
    @ResponseBody
    public Result deleteTeacherById(@PathVariable Integer id){
        return teacherService.deleteTeacher(id);
    }
    @PutMapping("/teacher/edit/{id}")
    @ResponseBody
    public Result editTeacher(@PathVariable Integer id, @ModelAttribute ReqTeacher reqTeacher){
        return teacherService.editTeacher(reqTeacher, id);
    }
    @GetMapping("/teacher/fileContent/{image_id}")
    @ResponseBody
    public byte[] getImageContent(@PathVariable Integer image_id, HttpServletResponse response){
        return teacherService.getFileFromServer(response, image_id);
    }




}
