package uz.real.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.real.entity.HistoryDescription;
import uz.real.entity.LogoHistory;
import uz.real.model.Result;
import uz.real.repository.HistoryDescriptionRepository;
import uz.real.repository.TeacherRepository;
import uz.real.service.HistoryCenterService;

import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/admin")
public class HistoryCenterController {

    @Autowired
    HistoryCenterService historyCenterService;

    @Autowired
    HistoryDescriptionRepository historyDescriptionRepository;

    @Autowired
    TeacherRepository teacherRepository;

    @GetMapping("/centerhistory")
    public String openCenterHistoryPage(Model model){
        model.addAttribute("histories", historyDescriptionRepository.findAll());
        model.addAttribute("teacherList", teacherRepository.findAll());
        return "admin/pages/menu/history_description";
    }

    @PostMapping("/history/saveFile")
    @ResponseBody
    public LogoHistory saveFile(@RequestParam(name = "file")MultipartFile file){
        return historyCenterService.saveFileToServer(file);
    }

    @PostMapping("/save/history")
    @ResponseBody
    public Result saveHistory(@RequestParam(name = "description") String description,
                                @RequestParam(name = "image_id") Long image_id){
        return historyCenterService.saveHistory(description, image_id);
    }
    @PutMapping("/edit/history/{id}")
    @ResponseBody
    public Result editHistory(@PathVariable Long id,   @RequestParam(name = "description") String description,
                              @RequestParam(name = "image_id") Long image_id){
        return historyCenterService.editHistory(id, description, image_id);
    }
    @DeleteMapping("/delete/history/{id}")
    @ResponseBody
    public Result deleteHistory(@PathVariable Long id){
        return historyCenterService.deleteHistory(id);
    }
    @GetMapping("/get/history/{id}")
    @ResponseBody
    public HistoryDescription getHistoryDescriptionById(@PathVariable Long id){
        return historyCenterService.getHistoryById(id);
    }
    @GetMapping("/getHC/{image_id}")
    @ResponseBody
    public byte[] getFileFromServer(@PathVariable Long image_id, HttpServletResponse response){
        return historyCenterService.getFileFromServer(response, image_id);
    }

}
