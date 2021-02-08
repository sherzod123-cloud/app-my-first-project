package uz.real.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.real.entity.History;
import uz.real.entity.HistoryImg;
import uz.real.model.Result;
import uz.real.repository.HistoryRepository;
import uz.real.repository.TeacherRepository;
import uz.real.service.HisTwoService;

import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/admin")
public class HistoryController {

    @Autowired
    HisTwoService hisTwoService;

    @Autowired
    HistoryRepository historyRepository;

    @Autowired
    TeacherRepository teacherRepository;

    @GetMapping("/history")
    public String openHistoryTwoPage(Model model){
        model.addAttribute("historiyes", historyRepository.findAll());
        model.addAttribute("teacherList", teacherRepository.findAll());
        return "admin/pages/menu/history_two";
    }
    @PostMapping("/histwo/saveFile")
    @ResponseBody
    public HistoryImg saveFileToServer(@RequestParam(name = "file") MultipartFile file){
        return hisTwoService.saveFileToServer(file);
    }
    @PostMapping("/histwo/save")
    @ResponseBody
    public Result saveHistoryTwo( @RequestParam(name = "title") String title,
                                  @RequestParam(name = "description") String description,
                                  @RequestParam(name = "image_id") Long image_id){
        return hisTwoService.saveHistoryTwo(title, description, image_id);
    }
    @GetMapping("/histwo/get/{id}")
    @ResponseBody
    public History getHistoryById(@PathVariable Long id){
        return hisTwoService.getHistoryTwoById(id);
    }
    @PutMapping("/histwo/edit/{id}")
    @ResponseBody
    public Result editHistory( @PathVariable Long id,
                               @RequestParam(name = "title") String title,
                               @RequestParam(name = "description") String description,
                               @RequestParam(name = "image_id") Long image_id){
        return hisTwoService.editHistory(id, title, description, image_id);
    }
    @DeleteMapping("/histwo/delete/{id}")
    @ResponseBody
    public Result deleteHistoryTwo(@PathVariable Long id){
        return hisTwoService.deleteHistoryTwo(id);
    }
    @GetMapping("/getHisTwoImgContent/{image_id}")
    @ResponseBody
    public byte[] getFileFromServer(@PathVariable Long image_id, HttpServletResponse response){
        return hisTwoService.getFileFromServer(response, image_id);
    }




}
