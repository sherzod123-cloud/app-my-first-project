package uz.real.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.real.entity.AboutCarousel;
import uz.real.entity.CarouselPhoto;
import uz.real.model.Result;
import uz.real.repository.AboutCarouselRepository;
import uz.real.repository.TeacherRepository;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/admin")
public class AboutCarouselController {

    @Autowired
    AboutCarouselRepository aboutCarouselRepository;

    @Autowired
    TeacherRepository teacherRepository;

    private String uploadFolderPath="D:\\sherzod\\app-spring-security-lesson1\\src\\main\\resources\\static\\assets\\aboutCarousel/";


    @GetMapping("/aboutCarousel")
    public String openAboutCarouselPage(Model model){
        model.addAttribute("aboutCarouselList", aboutCarouselRepository.findAll());
        model.addAttribute("teacherList",teacherRepository.findAll());
        return "admin/pages/menu/aboutCarousel";
    }
    @PostMapping("/aboutCarousel/save")
    @ResponseBody
    public Result saveImage(@RequestParam(name = "file") MultipartFile file){
        if (file.isEmpty()){
            return new Result(false, "File not selected!");
        }
        AboutCarousel carouselPhoto=new AboutCarousel();
        carouselPhoto.setFileName(String.valueOf(UUID.randomUUID()));
        carouselPhoto.setOriginalFileName(file.getOriginalFilename());
        carouselPhoto.setContentType(file.getContentType());
        carouselPhoto.setSize(file.getSize());
        aboutCarouselRepository.save(carouselPhoto);
        String  path=uploadFolderPath+file.getOriginalFilename();
        File file1=new File(path);
        try {
            if (!file1.isAbsolute()){
                file1.createNewFile();
            }
            file.transferTo(file1);
            return new Result(true, "Successfully saved Iamge");
        } catch (IOException e) {
            e.printStackTrace();
            return new Result(false, "file no saved!");
        }
    }
    @DeleteMapping("/aboutCarousel/delete/{id}")
    @ResponseBody
    public Result deleteCarouselPhotoFromServer(@PathVariable Long id){
        try{
            AboutCarousel aboutCarousel = aboutCarouselRepository.findById(id).get();
            File file=new File(uploadFolderPath+aboutCarousel.getOriginalFileName());
            if (file.delete()){
                aboutCarouselRepository.deleteById(id);
                return new Result(true, "Successfully deleted!");
            }else {
                return new Result(false, "No deleted!");
            }
        }catch (Exception e){
            System.out.println(e);
            return new Result(false," File not found!");
        }
    }

    @GetMapping("/getAboutCarousel/{id}")
    @ResponseBody
    public AboutCarousel getCarosuelPhotoById(@PathVariable Long id){
        Optional<AboutCarousel> byId = aboutCarouselRepository.findById(id);
        if(byId.isPresent()){
            return byId.get();
        }return null;
    }
    @PutMapping("/aboutCarousel/edit/{id}")
    @ResponseBody
    public AboutCarousel editImage(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        Optional<AboutCarousel> byId = aboutCarouselRepository.findById(id);
        if (byId.isPresent()){
            AboutCarousel aboutCarousel = byId.get();
            File file1=new File(uploadFolderPath+aboutCarousel.getOriginalFileName());
            if (file1.delete()){
                aboutCarousel.setId(id);
                aboutCarousel.setFileName(String.valueOf(UUID.randomUUID()));
                aboutCarousel.setOriginalFileName(file.getOriginalFilename());
                aboutCarousel.setContentType(file.getContentType());
                aboutCarousel.setSize(file.getSize());
                AboutCarousel save = aboutCarouselRepository.save(aboutCarousel);
                File file12=new File(uploadFolderPath+file.getOriginalFilename());
                try{
                    if (!file12.isAbsolute()){
                        file12.createNewFile();
                    }
                    file.transferTo(file12);
                    return save;
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }return null;
    }


    @GetMapping("/getAboutCarouselImgContent/{id}")
    @ResponseBody
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public byte[] getFileFromServer(HttpServletResponse response, @PathVariable Long id) {
        try {
            Optional<AboutCarousel> optionalAboutCarousel = aboutCarouselRepository.findById(id);
            if (optionalAboutCarousel.isPresent()) {
                AboutCarousel aboutCarousel = optionalAboutCarousel.get();
                BufferedInputStream bufferedInputStream=new BufferedInputStream(new FileInputStream(uploadFolderPath+ aboutCarousel.getOriginalFileName()));
                byte[] bytes = Files.readAllBytes(Paths.get(uploadFolderPath + aboutCarousel.getOriginalFileName()));
                response.setContentType(aboutCarousel.getContentType());
                FileCopyUtils.copy(bytes, response.getOutputStream());
                return bytes;
            }
        } catch (IOException e) {
            System.out.println(e);
        }return null;
    }




}
