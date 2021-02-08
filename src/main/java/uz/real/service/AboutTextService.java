package uz.real.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import uz.real.entity.AboutImg;
import uz.real.entity.AboutText;
import uz.real.entity.TalantAttachment;
import uz.real.model.Result;
import uz.real.repository.AboutImageRepository;
import uz.real.repository.AboutTextRepository;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;

@Service
public class AboutTextService {

    private String uploadPath ="D:\\sherzod\\app-spring-security-lesson1\\src\\main\\resources\\static\\assets\\aboutImages/";

    @Autowired
    AboutTextRepository aboutTextRepository;

    @Autowired
    AboutImageRepository aboutImageRepository;

    public AboutImg saveFile(@RequestParam(name = "file") MultipartFile mpf) {
        AboutImg aboutImg = new AboutImg();
        aboutImg.setFileName(mpf.getName());
        aboutImg.setOriginalFileName(mpf.getOriginalFilename());
        aboutImg.setContentType(mpf.getContentType());
        aboutImg.setSize(mpf.getSize());
        aboutImageRepository.save(aboutImg);
        String path = uploadPath + mpf.getOriginalFilename();
        try {
            File file = new File(path);
            if (!file.isAbsolute()) {
                file.createNewFile();
            }
            mpf.transferTo(file);
            return aboutImg;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Result saveAboutText(@RequestParam(name = "description") String description,
                                @RequestParam(name = "image_id") Long image_id) {
        AboutText aboutText = new AboutText();
        aboutText.setDescription(description);
        aboutText.setImage_id(image_id);
        aboutTextRepository.save(aboutText);
        return new Result(true, "Successfully saved about text and image");
    }

    public AboutText getAboutTextById(@PathVariable Long id) {
        Optional<AboutText> byId = aboutTextRepository.findById(id);
        if (byId.isPresent()) {
            return byId.get();
        }
        return null;
    }

    public Result editAboutText(@PathVariable Long id,
                                @RequestParam(name = "description") String description,
                                @RequestParam(name = "image_id") Long image_id) {
        Optional<AboutText> byId = aboutTextRepository.findById(id);
        if (byId.isPresent()){
            AboutText aboutText = byId.get();
            aboutText.setId(id);
            aboutText.setDescription(description);
            aboutText.setImage_id(image_id);
            aboutTextRepository.save(aboutText);
            return new Result(true, "Successfully edited aboutText");
        }return new Result(false, "Not edited");
    }
    public Result deleteAboutText(@PathVariable Long id){
        Optional<AboutText> byId = aboutTextRepository.findById(id);
        if (byId.isPresent()){
            aboutTextRepository.deleteById(id);
            return new Result(true, "Successfully deleted aboutText");
        }return new Result(false, "About Text not found");
    }
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public byte[] getFileFromServer(HttpServletResponse response, Long image_id) {
        try {
            Optional<AboutImg> byId = aboutImageRepository.findById(image_id);
            if (byId.isPresent()) {
                AboutImg aboutImg = byId.get();
                BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(new File(uploadPath + aboutImg.getOriginalFileName())));
                byte[] bytes = Files.readAllBytes(Paths.get(uploadPath + aboutImg.getOriginalFileName()));
//                System.out.println(bytes);
                response.setContentType(aboutImg.getContentType());
                FileCopyUtils.copy(bytes, response.getOutputStream());
                return bytes;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }
}
