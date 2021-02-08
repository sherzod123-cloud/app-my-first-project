package uz.real.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.real.entity.PhotoGalery;
import uz.real.model.Result;
import uz.real.repository.PhotoGaleryRepository;
import uz.real.repository.TeacherRepository;
import uz.real.service.PhotoGaleryService;

import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/admin")
public class PhotoGaleryController {

    @Autowired
    PhotoGaleryService photoGaleryService;

    @Autowired
    PhotoGaleryRepository photoGaleryRepository;

    @Autowired
    TeacherRepository teacherRepository;

    @GetMapping("/photogalery")
    public String openPhotoGaleryPage(Model model){
        model.addAttribute("photos", photoGaleryRepository.findAll());
        model.addAttribute("teacherList", teacherRepository.findAll());
        return "admin/pages/menu/gallery";
    }

    @PostMapping("/save/photo")
    @ResponseBody
    public Result savePhoto(@RequestParam(name = "file") MultipartFile mpf){
        return photoGaleryService.savePhoto(mpf);
    }
    @GetMapping("/getPhotoById/{id}")
    @ResponseBody
    public PhotoGalery getPhotoById(@PathVariable Long id){
        return photoGaleryService.getPhotoGaleryById(id);
    }
    @PutMapping("/edit/photo/{id}")
    @ResponseBody
    public Result editPhoto(@PathVariable Long id, @RequestParam(name = "file") MultipartFile mpf){
        return photoGaleryService.editPhoto(id, mpf);
    }
    @DeleteMapping("/delete/photo/{id}")
    @ResponseBody
    public Result deletePhoto(@PathVariable Long id){
        return photoGaleryService.deletePhoto(id);
    }

    @GetMapping("/getPhotoContent/{id}")
    @ResponseBody
    public byte[] getPhotoContent(@PathVariable Long id, HttpServletResponse response){
       return photoGaleryService.getFileFromServer(response, id);
    }
}
