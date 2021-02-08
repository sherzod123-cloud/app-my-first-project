package uz.real.service;

import org.apache.lucene.search.Query;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import uz.real.entity.Teacher;
import uz.real.entity.TeacherImages;
import uz.real.model.Result;
import uz.real.payload.ReqTeacher;
import uz.real.repository.TeacherImagesRepository;
import uz.real.repository.TeacherRepository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

@Service
public class TeacherService {

    @Autowired
    TeacherImagesRepository teacherImagesRepository;

    @Autowired
    TeacherRepository teacherRepository;

    private static String upload_Folder="D:\\sherzod\\app-spring-security-lesson1\\src\\main\\resources\\static\\assets\\teacherImages/";




    public TeacherImages saveFileToServer(@RequestParam("file") MultipartFile file){
            if(file.isEmpty()) {
                return null;
            }
            TeacherImages teacherImages=new TeacherImages();
            teacherImages.setContentType(file.getContentType());
            teacherImages.setSize(file.getSize());
            teacherImages.setFileName(UUID.randomUUID().toString());
            teacherImages.setOriginalFileName(file.getOriginalFilename());
            teacherImagesRepository.save(teacherImages);
            try {
                String path=upload_Folder+file.getOriginalFilename();
                File file1=new File(path);
                if (!file1.isAbsolute()){
                    file1.createNewFile();
                }
                file.transferTo(file1);
                return teacherImages;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
    public Result saveTeacher(@ModelAttribute ReqTeacher reqTeacher, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return new Result(false, "Teacher save incorrect!");
        }
        Teacher teacher=new Teacher();
        teacher.setFullName(reqTeacher.getFullName());
        teacher.setPosition(reqTeacher.getPosition());
        teacher.setPhoneNumber(reqTeacher.getPhoneNumber());
        teacher.setBiography(reqTeacher.getBiography());
        teacher.setImage_id(reqTeacher.getImage_id());
        teacherRepository.save(teacher);
        return new Result(true, "Successfully saved teacher");
    }
    public Teacher getTeacherById(@PathVariable Integer id){
        Optional<Teacher> byId = teacherRepository.findById(id);
        if (byId.isPresent()){
            Teacher teacher = byId.get();
            return teacher;
        }
        return new Teacher();
    }
    public Result editTeacher(@ModelAttribute ReqTeacher reqTeacher, @PathVariable Integer id){
        Optional<Teacher> byId = teacherRepository.findById(id);
        if (byId.isPresent()){
            Teacher teacher = byId.get();
            teacher.setId(id);
            teacher.setFullName(reqTeacher.getFullName());
            teacher.setPosition(reqTeacher.getPosition());
            teacher.setPhoneNumber(reqTeacher.getPhoneNumber());
            teacher.setBiography(reqTeacher.getBiography());
            teacher.setImage_id(reqTeacher.getImage_id());
            teacherRepository.save(teacher);
            return new Result(true, "Successfully edit teacher!");
        }return new Result(false, "Edit not successfully");
    }
    public Result deleteTeacher(@PathVariable Integer id){
        Optional<Teacher> byId = teacherRepository.findById(id);
        if (byId.isPresent()){
            Teacher teacher = byId.get();
            if (deleteTalantFromServer(teacher.getImage_id())){
                teacherRepository.delete(teacher);
                return new Result(true, "Successfully deleted teacher!");
            }
        }
        return new Result(false, "Not deleted!");
    }

    public Boolean deleteTalantFromServer(Integer id){
        try{
            TeacherImages attachment=teacherImagesRepository.findById(id).get();
            File file=new File(upload_Folder+attachment.getOriginalFileName());
            if (file.delete()){
                teacherImagesRepository.deleteById(id);
                return true;
            }
        }catch (Exception e){
            System.out.println(e);
//            return new Result(false,"No deleting files");
            return false;
        }
        return false;
    }



    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public byte[] getFileFromServer(HttpServletResponse response, Integer image_id) {
        try {
            TeacherImages attachment=teacherImagesRepository.findById(image_id).get();
            BufferedInputStream bufferedInputStream=new BufferedInputStream(new FileInputStream(new File(upload_Folder+ attachment.getOriginalFileName())));
            byte[] bytes= Files.readAllBytes(Paths.get(upload_Folder+attachment.getOriginalFileName()));
            System.out.println(bytes);
            response.setContentType(attachment.getContentType());
            FileCopyUtils.copy(bytes, response.getOutputStream());
            return bytes;
        } catch (IOException e) {
            System.out.println(e);
        }return null;
        }
}
