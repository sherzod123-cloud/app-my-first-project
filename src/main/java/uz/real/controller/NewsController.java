package uz.real.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.real.entity.News;
import uz.real.entity.NewsImg;
import uz.real.model.Result;
import uz.real.repository.NewsRepository;
import uz.real.repository.TeacherRepository;
import uz.real.service.NewsService;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class NewsController {

    @Autowired
    NewsService newsService;

    @Autowired
    NewsRepository newsRepository;

    @Autowired
    TeacherRepository teacherRepository;

    @GetMapping("/news")
    public String openNewsPage(Model model){
        model.addAttribute("newsList", newsRepository.findAll());
        model.addAttribute("teacherList",teacherRepository.findAll());
        return "admin/pages/menu/news";
    }
    @PostMapping("/news/saveFile")
    @ResponseBody
    public NewsImg saveFileToServer(@RequestParam(name = "file")MultipartFile file){
        return newsService.saveFileToServer(file);
    }
    @PostMapping("/save/news")
    @ResponseBody
    public Result saveNews(
            @RequestParam(name = "title") String title,
            @RequestParam(name = "description") String description,
            @RequestParam(name = "image_id") Long image_id
    ){
        return newsService.saveNews(title, description, image_id);
    }
    @GetMapping("/news/get/{id}")
    @ResponseBody
    public News getNewsById(@PathVariable Long id){
        return newsService.getNewsById(id);
    }
    @PutMapping("/news/edit/{id}")
    @ResponseBody
    public Result editNews(@PathVariable Long id,
                           @RequestParam(name = "title") String title,
                           @RequestParam(name = "description") String description,
                           @RequestParam(name = "image_id") Long image_id
                           ){
        return newsService.editNews(id,title, description, image_id);
    }
    @DeleteMapping("/news/delete/{id}")
    @ResponseBody
    public Result deleteNews(@PathVariable Long id){
        return newsService.deleteNews(id);
    }

    @PostMapping("/news/isPublish/{id}")
    @ResponseBody
    public Boolean updateIsPublish(@PathVariable Long id){
        Optional<News> byId = newsRepository.findById(id);
        if (byId.isPresent()) {
            News news = byId.get();
            Boolean isPublish=news.getIsPublished();
            if (isPublish){
                news.setIsPublished(!isPublish);
                newsRepository.save(news);
                return news.getIsPublished();
            }else {
                news.setIsPublished(!isPublish);
                newsRepository.save(news);
                return news.getIsPublished();
            }
        }return null;
    }
    @PostMapping("/news/isView/{id}")
    @ResponseBody
    public Boolean updateIsView(@PathVariable Long id){
        Optional<News> byId = newsRepository.findById(id);
        if (byId.isPresent()) {
            News news = byId.get();
            Boolean isView=news.getIsView();
            if (isView){
                news.setIsView(!isView);
                newsRepository.save(news);
                return news.getIsView();
            }else {
                news.setIsView(!isView);
                newsRepository.save(news);
                return news.getIsView();
            }
        }return null;
    }
    @GetMapping("/news/getImgContent/{image_id}")
    @ResponseBody
    public byte[] getFileFromServer(@PathVariable Long image_id, HttpServletResponse response){
        return newsService.getFileFromServer(response, image_id);
    }

}
