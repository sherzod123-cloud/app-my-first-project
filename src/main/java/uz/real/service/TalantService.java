package uz.real.service;

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
import uz.real.entity.Talant;
import uz.real.entity.TalantAttachment;
import uz.real.entity.Teacher;
import uz.real.entity.TeacherImages;
import uz.real.model.Result;
import uz.real.payload.ReqTalant;
import uz.real.payload.ReqTeacher;
import uz.real.repository.TalantAttachmentRepository;
import uz.real.repository.TalantRepository;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TalantService {

    @Autowired
    TalantRepository talantRepository;

    @Autowired
    TalantAttachmentRepository talantAttachmentRepository;

    private static String upload_Folder="D:\\sherzod\\app-spring-security-lesson1\\src\\main\\resources\\static\\assets\\talantImages/";

    public TalantAttachment saveFileToServer(@RequestParam("file") MultipartFile file){
        if(file.isEmpty()) {
            return null;
        }
        TalantAttachment attachment=new TalantAttachment();
        attachment.setContentType(file.getContentType());
        attachment.setSize(file.getSize());
        attachment.setFileName(UUID.randomUUID().toString());
        attachment.setOriginalFileName(file.getOriginalFilename());
        talantAttachmentRepository.save(attachment);
        try {
            String path=upload_Folder+file.getOriginalFilename();
            File file1=new File(path);
            if (!file1.isAbsolute()){
                file1.createNewFile();
            }
            file.transferTo(file1);
            return attachment;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    public Result saveTalant(@ModelAttribute ReqTalant reqTalant, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return new Result(false, "Talant save incorrect!");
        }
        Talant talant=new Talant();
        List<TalantAttachment> talantAttachmentList = talantAttachmentRepository.findAllById(reqTalant.getAttachmentId());
        talant.setFullName(reqTalant.getFullName());
        talant.setAddress(reqTalant.getAddress());
        talant.setPosition(reqTalant.getPosition());
        talant.setAttachmentId(reqTalant.getAttachmentId());
        talant.setTalantAttachments(talantAttachmentList);
        talantRepository.save(talant);
        return new Result(true, "Successfully saved talant");
    }
    public Talant getTalantById(@PathVariable Integer id){
        Optional<Talant> talant = talantRepository.findById(id);
        if (talant.isPresent()){
            return talant.get();
        }
        return null;
    }
    public Result editTalant(@ModelAttribute ReqTalant reqTalant, @PathVariable Integer id){
        Optional<Talant> byId = talantRepository.findById(id);
        if (byId.isPresent()){
            Talant talant = byId.get();
            if (delTalantFromServer(talant.getAttachmentId())){
                List<TalantAttachment> talantAttachmentList = talantAttachmentRepository.findAllById(reqTalant.getAttachmentId());
                Talant talant1=new Talant();
                talant1.setId(id);
                talant1.setFullName(reqTalant.getFullName());
                talant1.setAddress(reqTalant.getAddress());
                talant1.setPosition(reqTalant.getPosition());
                talant1.setAttachmentId(reqTalant.getAttachmentId());
                talant1.setTalantAttachments(talantAttachmentList);
                talantRepository.save(talant1);
                return new Result(true, "Successfully edit talant!");
            }

        }return new Result(false, "Edit not successfully");
    }
    public Boolean delTalantFromServer(@PathVariable Integer id){
        try{
            TalantAttachment attachment=talantAttachmentRepository.findById(id).get();
            File file=new File(upload_Folder+attachment.getOriginalFileName());
            if (file.delete()){
                talantAttachmentRepository.deleteById(id);
                return true;

            }else {
                return false;
            }
        }catch (Exception e){
            System.out.println(e);
            return false;
        }
    }
    public Result deleteTalant(@PathVariable Integer id){
        Optional<Talant> talantOptional = talantRepository.findById(id);
        if (talantOptional.isPresent()){
            Talant talant = talantOptional.get();
            if(delTalantFromServer(talant.getAttachmentId())){
                talantRepository.delete(talant);
                return new Result(true, "Successfully deleted talant!");
            }

        }
        return new Result(false, "Not deleted!");
    }


    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public byte[] getFileFromServer(HttpServletResponse response, Integer attachmentId) {
        try {
            Optional<TalantAttachment> talantAttachmentOptional = talantAttachmentRepository.findById(attachmentId);
            if (talantAttachmentOptional.isPresent()) {
                TalantAttachment attachment = talantAttachmentOptional.get();
                BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(new File(upload_Folder + attachment.getOriginalFileName())));
                byte[] bytes = Files.readAllBytes(Paths.get(upload_Folder + attachment.getOriginalFileName()));
//                System.out.println(bytes);
                response.setContentType(attachment.getContentType());
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
