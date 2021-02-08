package uz.real.controller;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.real.entity.NewsBottomMenu;
import uz.real.entity.NewsBottomMenuImg;
import uz.real.model.Result;
import uz.real.repository.NewsBottomMenuRepository;
import uz.real.repository.TeacherRepository;
import uz.real.service.NewsBottomMenuService;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class NewsBottomMenuController {

    @Autowired
    NewsBottomMenuService newsBottomMenuService;

    @Autowired
    NewsBottomMenuRepository newsBottomMenuRepository;

    @Autowired
    TeacherRepository teacherRepository;

    @GetMapping("/newsmenu")
    public String openNewsBottomMenuPage(Model model){
        model.addAttribute("newsmenus", newsBottomMenuRepository.findAll());
        model.addAttribute("teacherList", teacherRepository.findAll());
        return "admin/pages/menu/news_bottom_menu";
    }

    @PostMapping("/newsmenu/saveFile")
    @ResponseBody
    public NewsBottomMenuImg savweFileToServer(@RequestParam(name = "file")MultipartFile file){
        return newsBottomMenuService.saveFileToServer(file);
    }
    @PostMapping("/save/newsmenu")
    @ResponseBody
    public Result saveNewsBottomMenu(    @RequestParam(name = "title") String title,
                                         @RequestParam(name = "description") String description,
                                         @RequestParam(name = "image_id") Long image_id){
        return newsBottomMenuService.saveNewsBottomMenu(title, description, image_id);
    }
    @GetMapping("/get/menunews/{id}")
    @ResponseBody
    public NewsBottomMenu getNewsMenuByid(@PathVariable Long id){
        return newsBottomMenuService.getNewsMenuById(id);
    }
    @PutMapping("/newsmenu/edit/{id}")
    @ResponseBody
    public Result editNewsMenu(@PathVariable Long id,
                               @RequestParam(name = "title") String title,
                               @RequestParam(name = "description") String description,
                               @RequestParam(name = "image_id") Long image_id){
        return newsBottomMenuService.editNewsMenu(id, title, description, image_id);
    }
    @DeleteMapping("/newsmenu/delete/{id}")
    @ResponseBody
    public Result deleteNewsMenu(@PathVariable Long id){
        return newsBottomMenuService.deleteNewsMenu(id);
    }
    @PostMapping("/editIsPublish/{id}")
    @ResponseBody
    public Boolean updateIsPublish(@PathVariable Long id){
        Optional<NewsBottomMenu> byId = newsBottomMenuRepository.findById(id);
        if (byId.isPresent()) {
            NewsBottomMenu newsBottomMenu = byId.get();
            Boolean isPublish=newsBottomMenu.getIsPublish();
            if (isPublish){
                newsBottomMenu.setIsPublish(!isPublish);
                newsBottomMenuRepository.save(newsBottomMenu);
                return newsBottomMenu.getIsPublish();
            }else {
                newsBottomMenu.setIsPublish(!isPublish);
                newsBottomMenuRepository.save(newsBottomMenu);
                return newsBottomMenu.getIsPublish();
            }
        }return null;
    }
    @GetMapping("/getNewsMenuContent/{image_id}")
    @ResponseBody
    public byte[] getFileFromServer(@PathVariable Long image_id, HttpServletResponse response){
        return newsBottomMenuService.getFileFromServer(response, image_id);
    }
    @GetMapping("/getMoreNews/{id}")
    public String openGetMoreNewsPage(@PathVariable Long id, Model model){
        Optional<NewsBottomMenu> byId = newsBottomMenuRepository.findById(id);
        if (byId.isPresent()){
            NewsBottomMenu newsBottomMenu = byId.get();
            model.addAttribute("newsBotMenuGetById", newsBottomMenu);
            return "newsmenu_more";
        }return "";
    }
}
