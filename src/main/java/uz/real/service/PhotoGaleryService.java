package uz.real.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import uz.real.entity.PhotoGalery;
import uz.real.entity.TeacherImages;
import uz.real.model.Result;
import uz.real.repository.PhotoGaleryRepository;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;

@Service
public class PhotoGaleryService {

    private String uploadPath="D:\\sherzod\\app-spring-security-lesson1\\src\\main\\resources\\static\\assets\\photogaleryImages/";
    private String shortPath="/assets/photogaleryImages/";
    @Autowired
    PhotoGaleryRepository photoGaleryRepository;

    public Result savePhoto(@RequestParam("file") MultipartFile mpf){
        PhotoGalery photoGalery=new PhotoGalery();
        photoGalery.setFileName(mpf.getName());
        photoGalery.setOriginalFileName(mpf.getOriginalFilename());
        photoGalery.setContentType(mpf.getContentType());
        photoGalery.setUploadPath(shortPath+mpf.getOriginalFilename());
        photoGalery.setSize(mpf.getSize());
        photoGaleryRepository.save(photoGalery);
        String path=uploadPath+mpf.getOriginalFilename();
        File file=new File(path);
        try {
            if (!file.isAbsolute()){
                file.createNewFile();
            }mpf.transferTo(file);
            return new Result(true, "Successfully saved photo");
        } catch (IOException e) {
            e.printStackTrace();
            return new Result(false, "File not create");
        }
    }
    public PhotoGalery getPhotoGaleryById(@PathVariable Long id){
        Optional<PhotoGalery> byId = photoGaleryRepository.findById(id);
        if (!byId.isPresent()){
            return null;
        }return byId.get();
    }
    public Result editPhoto(@PathVariable Long id, @RequestParam(name = "file") MultipartFile mpf){
        Optional<PhotoGalery> byId = photoGaleryRepository.findById(id);
        if (byId.isPresent()){
            PhotoGalery photoGalery = byId.get();
            try {
                File file1=new File(uploadPath+photoGalery.getOriginalFileName());
                if (file1.delete()){
                    photoGalery.setId(id);
                    photoGalery.setFileName(mpf.getName());
                    photoGalery.setOriginalFileName(mpf.getOriginalFilename());
                    photoGalery.setSize(mpf.getSize());
                    photoGalery.setUploadPath(shortPath+mpf.getOriginalFilename());
                    photoGaleryRepository.save(photoGalery);
                    File file11=new File(uploadPath+photoGalery.getOriginalFileName());
                    if (!file11.isAbsolute()){
                       file11.createNewFile();
                    }
                    mpf.transferTo(file11);
                    return new Result(true, "Successfully edit photo");
                }
            } catch (IOException e) {
                e.printStackTrace();
                return new Result(false, "Not successfully edit ");
            }
        }return new Result(false, "Photo not found");
    }

    public Result deletePhoto(@PathVariable Long id){
        Optional<PhotoGalery> byId = photoGaleryRepository.findById(id);
        if (byId.isPresent()){
            PhotoGalery photoGalery = byId.get();
            File file1=new File(uploadPath+photoGalery.getOriginalFileName());
            if (file1.delete()){
                photoGaleryRepository.delete(photoGalery);
            }return new Result(true, "Successfully deleted!");
        }return new Result(false, "Photo not found");
    }
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public byte[] getFileFromServer(HttpServletResponse response, Long id) {
        try {
            PhotoGalery photoGalery = photoGaleryRepository.findById(id).get();
            BufferedInputStream bufferedInputStream=new BufferedInputStream(new FileInputStream(new File(uploadPath+ photoGalery.getOriginalFileName())));
            byte[] bytes= Files.readAllBytes(Paths.get(uploadPath+photoGalery.getOriginalFileName()));
            System.out.println(bytes);
            response.setContentType(photoGalery.getContentType());
            FileCopyUtils.copy(bytes, response.getOutputStream());
            return bytes;
        } catch (IOException e) {
            System.out.println(e);
        }return null;
    }






}
