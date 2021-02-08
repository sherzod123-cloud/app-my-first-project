package uz.real.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.real.entity.Kamanda;
import uz.real.entity.KamandaImg;
import uz.real.model.Result;
import uz.real.payload.ReqKamanda;
import uz.real.repository.KamandaRepository;
import uz.real.repository.TeacherRepository;
import uz.real.service.KamandaService;

import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/admin")
public class KamandaController {

    @Autowired
    KamandaService kamandaService;

    @Autowired
    KamandaRepository kamandaRepository;

    @Autowired
    TeacherRepository teacherRepository;

    @GetMapping("/kamanda")
    public String openAdminKamandaPage(Model model){
        model.addAttribute("kamandaList", kamandaRepository.findAll());
        model.addAttribute("teacherList",teacherRepository.findAll());
        return "admin/pages/menu/kamanda";
    }
    @PostMapping("/people/saveFile")
    @ResponseBody
    public KamandaImg saveImgPeopleToServer(@RequestParam(name = "file")MultipartFile mpf){
        return kamandaService.saveFile(mpf);
    }
    @PostMapping("/people/save")
    @ResponseBody
    public Result savePeople(@ModelAttribute ReqKamanda reqKamanda){
        return kamandaService.savePeople(reqKamanda);
    }
    @GetMapping("/people/get/{id}")
    @ResponseBody
    public Kamanda getKamandaById(@PathVariable Long id){
        return kamandaService.getKamandaById(id);
    }
    @PutMapping("/people/edit/{id}")
    @ResponseBody
    public Result editPeople(@PathVariable Long id, @ModelAttribute ReqKamanda reqKamanda){
        return kamandaService.editPeople(id, reqKamanda);
    }
    @DeleteMapping("/people/delete/{id}")
    @ResponseBody
    public Result deletePeople(@PathVariable Long id){
        return kamandaService.deletePeopleById(id);
    }
    @GetMapping("/peopleImg/getContent/{image_id}")
    @ResponseBody
    public byte[] getFileFromServer(@PathVariable Long image_id, HttpServletResponse response){
        return kamandaService.getFileFromServer(response, image_id);
    }
}
