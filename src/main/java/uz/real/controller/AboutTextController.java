package uz.real.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.real.entity.AboutImg;
import uz.real.entity.AboutText;
import uz.real.model.Result;
import uz.real.repository.AboutTextRepository;
import uz.real.repository.TeacherRepository;
import uz.real.service.AboutTextService;

import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/admin")
public class AboutTextController {

    @Autowired
    AboutTextService aboutTextService;

    @Autowired
    AboutTextRepository aboutTextRepository;

    @Autowired
    TeacherRepository teacherRepository;

    @GetMapping("/aboutText")
    public String openAboutTextAndImagePage(Model model){
        model.addAttribute("aboutTextList", aboutTextRepository.findAll());
        model.addAttribute("teacherList",teacherRepository.findAll());
        return "admin/pages/menu/about_text_img";
    }
    @PostMapping("/myabout/saveFile")
    @ResponseBody
    public AboutImg saveFileToServer(@RequestParam(name = "file") MultipartFile mpf){
        return aboutTextService.saveFile(mpf);
    }
    @PostMapping("/save/aboutText")
    @ResponseBody
    public Result saveAboutText(@RequestParam(name = "description") String description,
                                @RequestParam(name = "image_id") Long image_id){
        aboutTextService.saveAboutText(description, image_id);
        return new Result(true, "Successfully saved about");

    }
    @GetMapping("/get/aboutText/{id}")
    @ResponseBody
    public AboutText getAboutTextById(@PathVariable Long id){
        return aboutTextService.getAboutTextById(id);
    }
    @PutMapping("edit/aboutText/{id}")
    @ResponseBody
    public Result editAboutText(@PathVariable Long id,
                                @RequestParam(name = "description") String description,
                                @RequestParam(name = "image_id") Long image_id){
        return aboutTextService.editAboutText(id, description, image_id);
    }
    @DeleteMapping("/delete/aboutText/{id}")
    @ResponseBody
    public Result deleteAboutText(@PathVariable Long id){
        return aboutTextService.deleteAboutText(id);
    }
    @GetMapping("/getImgContent/{image_id}")
    @ResponseBody
    public byte[] getFileFromServer(@PathVariable Long image_id, HttpServletResponse response){
        return aboutTextService.getFileFromServer(response, image_id);
    }


}
