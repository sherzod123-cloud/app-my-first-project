package uz.real.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import uz.real.entity.Kamanda;
import uz.real.entity.KamandaImg;
import uz.real.model.Result;
import uz.real.payload.ReqKamanda;
import uz.real.repository.KamandaImgRepository;
import uz.real.repository.KamandaRepository;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;

@Service
public class KamandaService {

    private static String upload_Folder="D:\\sherzod\\app-spring-security-lesson1\\src\\main\\resources\\static\\assets\\kamandaImg/";


    @Autowired
    KamandaRepository kamandaRepository;

    @Autowired
    KamandaImgRepository kamandaImgRepository;



    public KamandaImg saveFile(@RequestParam(name = "file" )MultipartFile mpf){
        KamandaImg kamandaImg=new KamandaImg();
        kamandaImg.setName(mpf.getName());
        kamandaImg.setOriginalFileName(mpf.getOriginalFilename());
        kamandaImg.setContentType(mpf.getContentType());
        kamandaImg.setSize(mpf.getSize());
        kamandaImgRepository.save(kamandaImg);
        try{
            String path=upload_Folder+kamandaImg.getOriginalFileName();
            File file=new File(path);
            if (!file.isAbsolute()){
                file.createNewFile();
            }
            mpf.transferTo(file);
            return kamandaImg;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    public Result savePeople(@ModelAttribute ReqKamanda reqKamanda){
        Kamanda kamanda=new Kamanda();
        kamanda.setFullname(reqKamanda.getFullname());
        kamanda.setAddress(reqKamanda.getAddress());
        kamanda.setPhone(reqKamanda.getPhone());
        kamanda.setPosition(reqKamanda.getPosition());
        kamanda.setBiography(reqKamanda.getBiography());
        kamanda.setImage_id(reqKamanda.getImage_id());
        kamandaRepository.save(kamanda);
        return new Result(true, "Succesfully savedpeople");
    }
    public Kamanda getKamandaById(@PathVariable Long id){
        Optional<Kamanda> byId = kamandaRepository.findById(id);
        if (byId.isPresent()){
           return byId.get();
        }return null;
    }
    public Result editPeople(@PathVariable Long id, @ModelAttribute ReqKamanda reqKamanda){
        Optional<Kamanda> byId = kamandaRepository.findById(id);
        if (byId.isPresent()){
            Kamanda kamanda = byId.get();
            kamanda.setId(id);
            kamanda.setFullname(reqKamanda.getFullname());
            kamanda.setAddress(reqKamanda.getAddress());
            kamanda.setPhone(reqKamanda.getPhone());
            kamanda.setPosition(reqKamanda.getPosition());
            kamanda.setBiography(reqKamanda.getBiography());
            kamanda.setImage_id(reqKamanda.getImage_id());
            kamandaRepository.save(kamanda);
            return new Result(true, "Successfully edit People");
        }
        return new Result(false, "People not found!");
    }
    public Result deletePeopleById(@PathVariable Long id){
        Optional<Kamanda> byId = kamandaRepository.findById(id);
        if (byId.isPresent()){
            Kamanda kamanda = byId.get();
            Optional<KamandaImg> byId1 = kamandaImgRepository.findById(kamanda.getImage_id());
            if (byId1.isPresent()){
                KamandaImg kamandaImg = byId1.get();
                File file=new File(upload_Folder+kamandaImg.getOriginalFileName());
                kamandaRepository.delete(kamanda);
                if (file.delete()){
                    kamandaImgRepository.delete(kamandaImg);
                    return new Result(true, "Successfully deleted people");
                }
            }
        }return new Result(false, "People not found!");
    }
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public byte[] getFileFromServer(HttpServletResponse response, Long image_id) {
        try {
//            TeacherImages attachment=teacherImagesRepository.findById(image_id).get();
            KamandaImg kamandaImg = kamandaImgRepository.findById(image_id).get();
            BufferedInputStream bufferedInputStream=new BufferedInputStream(new FileInputStream(new File(upload_Folder+ kamandaImg.getOriginalFileName())));
            byte[] bytes= Files.readAllBytes(Paths.get(upload_Folder+kamandaImg.getOriginalFileName()));
            System.out.println(bytes);
            response.setContentType(kamandaImg.getContentType());
            FileCopyUtils.copy(bytes, response.getOutputStream());
            return bytes;
        } catch (IOException e) {
            System.out.println(e);
        }return null;
    }
}
