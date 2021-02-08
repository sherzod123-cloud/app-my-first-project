package uz.real.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import uz.real.entity.Videos;
import uz.real.model.Result;
import uz.real.repository.TeacherRepository;
import uz.real.repository.VideosRepository;

import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class VideosController {

    @Autowired
    VideosRepository videosRepository;

    @Autowired
    TeacherRepository teacherRepository;

    @GetMapping("/videos")
    public String openVideosPage(Model model){
        model.addAttribute("videos", videosRepository.findAll());
        model.addAttribute("teacherList", teacherRepository.findAll());
        return "admin/pages/content/videos";
    }
    @PostMapping("/save/videos")
    @ResponseBody
    public Result saveVideos(@RequestParam(name = "src") String src, @RequestParam(name = "description") String description){
        Videos videos=new Videos();
        videos.setAddress(src);
        videos.setDescription(description);
        videosRepository.save(videos);
        return new Result(true, "Successfully saved videos:");
    }
    @GetMapping("/getVideobyId/{id}")
    @ResponseBody
    public Videos getVideoById(@PathVariable Long id){
        Optional<Videos> byId = videosRepository.findById(id);
        if (byId.isPresent()){
           return byId.get();
        }return null;
    }
    @PutMapping("/editVideo/{id}")
    @ResponseBody
    public Result editVideo(@PathVariable Long id, @RequestParam(name = "src") String src, @RequestParam(name = "description") String description){
        Optional<Videos> byId = videosRepository.findById(id);
        if (byId.isPresent()){
            Videos videos = byId.get();
            videos.setId(id);
            videos.setAddress(src);
            videos.setDescription(description);
            videosRepository.save(videos);
            return new Result(true, "Successfully edit video");
        }return new Result(false, "Don't edit");
    }
    @DeleteMapping("/deleteVideo/{id}")
    @ResponseBody
    public Result deleteVideo(@PathVariable Long id){
        videosRepository.deleteById(id);
        return new Result(true, "Successfully deleted!");
    }
    @GetMapping("videos/getSrc/{id}")
    @ResponseBody
    public String getSrc(@PathVariable Long id){
        Optional<Videos> byId = videosRepository.findById(id);
        if (byId.isPresent()){
            Videos videos = byId.get();
            System.out.println(videos.getAddress());
            return videos.getAddress();
        }return null;
    }
}
