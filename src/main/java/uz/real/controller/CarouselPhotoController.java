package uz.real.controller;

import com.sun.org.apache.xpath.internal.operations.Mult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.real.entity.CarouselPhoto;
import uz.real.entity.TeacherImages;
import uz.real.model.Result;
import uz.real.payload.ReqCarouselImg;
import uz.real.repository.CarouselPhotoRepository;
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
public class CarouselPhotoController {

    private String uploadFolderPath="D:\\sherzod\\app-spring-security-lesson1\\src\\main\\resources\\static\\assets\\carouselContentImages/";

    @Autowired
    CarouselPhotoRepository carouselPhotoRepository;

    @Autowired
    TeacherRepository teacherRepository;

    @GetMapping("/carouselPhoto")
    public String openCarouselImagePage(Model model){
        model.addAttribute("images", carouselPhotoRepository.findAll());
        model.addAttribute("teacherList", teacherRepository.findAll());
        return "admin/pages/content/carouserPhoto";
    }

    @PostMapping("carouselPhoto/saveImage")
    @ResponseBody
    public Result saveIamge(@RequestParam(name = "file") MultipartFile file){
        if (file.isEmpty()){
            return new Result(false, "File not selected!");
        }
        CarouselPhoto carouselPhoto=new CarouselPhoto();
        carouselPhoto.setFileName(String.valueOf(UUID.randomUUID()));
        carouselPhoto.setOriginalFileName(file.getOriginalFilename());
        carouselPhoto.setContentType(file.getContentType());
        carouselPhoto.setSize(file.getSize());
        carouselPhotoRepository.save(carouselPhoto);
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
    @DeleteMapping("carouselImg/delete/{id}")
    @ResponseBody
    public Result deleteCarouselPhotoFromServer(@PathVariable Long id){
        try{
            CarouselPhoto attachment=carouselPhotoRepository.findById(id).get();
            File file=new File(uploadFolderPath+attachment.getOriginalFileName());
            if (file.delete()){
                carouselPhotoRepository.deleteById(id);
                return new Result(true, "Successfully deleted!");
            }else {
                return new Result(false, "No deleted!");
            }
        }catch (Exception e){
            System.out.println(e);
            return new Result(false," File not found!");
        }
    }

    @GetMapping("/getImageById/{id}")
    @ResponseBody
    public CarouselPhoto getCarosuelPhotoById(@PathVariable Long id){
        Optional<CarouselPhoto> byId = carouselPhotoRepository.findById(id);
        if(byId.isPresent()){
            return byId.get();
        }return null;
    }
    @PutMapping("/carouselPhoto/edit/{id}")
    @ResponseBody
    public CarouselPhoto editImage(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        Optional<CarouselPhoto> byId = carouselPhotoRepository.findById(id);
        if (byId.isPresent()){
                CarouselPhoto carouselPhoto = byId.get();
                File file1=new File(uploadFolderPath+carouselPhoto.getOriginalFileName());
                if (file1.delete()){
                    carouselPhoto.setId(id);
                    carouselPhoto.setFileName(String.valueOf(UUID.randomUUID()));
                    carouselPhoto.setOriginalFileName(file.getOriginalFilename());
                    carouselPhoto.setContentType(file.getContentType());
                    carouselPhoto.setSize(file.getSize());
                    CarouselPhoto save = carouselPhotoRepository.save(carouselPhoto);
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


    @GetMapping("/getContent/{id}")
    @ResponseBody
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public byte[] getFileFromServer(HttpServletResponse response, @PathVariable Long id) {
        try {
            Optional<CarouselPhoto> optionalCarouselPhoto = carouselPhotoRepository.findById(id);
            if (optionalCarouselPhoto.isPresent()) {
                CarouselPhoto carouselPhoto = optionalCarouselPhoto.get();
                BufferedInputStream bufferedInputStream=new BufferedInputStream(new FileInputStream(uploadFolderPath+ carouselPhoto.getOriginalFileName()));
                byte[] bytes = Files.readAllBytes(Paths.get(uploadFolderPath + carouselPhoto.getOriginalFileName()));
                response.setContentType(carouselPhoto.getContentType());
                FileCopyUtils.copy(bytes, response.getOutputStream());
                return bytes;
            }
        } catch (IOException e) {
            System.out.println(e);
        }return null;
    }

}
