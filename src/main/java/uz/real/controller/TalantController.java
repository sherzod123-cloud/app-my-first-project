package uz.real.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
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
import uz.real.repository.TeacherRepository;
import uz.real.service.TalantService;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class TalantController {

    @Autowired
    TalantRepository talantRepository;

    @Autowired
    TalantAttachmentRepository talantAttachmentRepository;

    @Autowired
    TalantService talantService;

    @Autowired
    TeacherRepository teacherRepository;

    @GetMapping("/talant")
    public String openTeacherPage(Model model){
        model.addAttribute("talants", talantRepository.findAll());
        model.addAttribute("teacherList", teacherRepository.findAll());
        return "admin/pages/content/talant";
    }
    @GetMapping("/talant/get/{id}")
    @ResponseBody
    public Talant getTalantById(@PathVariable Integer id){
        return talantService.getTalantById(id);
    }

    @PostMapping("/talant")
    @ResponseBody
    public Result saveTalant(@ModelAttribute ReqTalant reqTalant, BindingResult bindingResult){
     return talantService.saveTalant(reqTalant, bindingResult);
    }

    @PostMapping("/talant/saveFile")
    @ResponseBody
    public TalantAttachment uploadImage(@RequestParam(name = "file") MultipartFile file){
        return talantService.saveFileToServer(file);
    }

    @DeleteMapping("/talant/delete/{id}")
    @ResponseBody
    public Result deleteTalantById(@PathVariable Integer id){
        return talantService.deleteTalant(id);
    }
    @PutMapping("/talant/edit/{id}")
    @ResponseBody
    public Result editTalant(@PathVariable Integer id, @ModelAttribute ReqTalant reqTalant){
        return talantService.editTalant(reqTalant, id);
    }
    @GetMapping("/talant/fileContent/{attachmentId}")
    @ResponseBody
    public byte[] getImageContent(@PathVariable Integer attachmentId, HttpServletResponse response){
        return talantService.getFileFromServer(response, attachmentId);
    }




}
