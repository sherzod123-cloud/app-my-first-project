package uz.real.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import uz.real.entity.News;
import uz.real.entity.NewsBottomMenu;
import uz.real.entity.NewsBottomMenuImg;
import uz.real.entity.NewsImg;
import uz.real.model.Result;
import uz.real.repository.NewsBottomMenuImgRepository;
import uz.real.repository.NewsBottomMenuRepository;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.Optional;

@Service
public class NewsBottomMenuService {

    @Autowired
    NewsBottomMenuRepository newsBottomMenuRepository;

    @Autowired
    NewsBottomMenuImgRepository newsBottomMenuImgRepository;

    private String uploadPath="D:\\sherzod\\app-spring-security-lesson1\\src\\main\\resources\\static\\assets\\newsBottomMenuImg\\";

    public NewsBottomMenuImg saveFileToServer(@RequestParam(name = "file") MultipartFile file){
       NewsBottomMenuImg newsImg=new NewsBottomMenuImg();
        if (!file.isEmpty()){
            newsImg.setName(file.getName());
            newsImg.setOriginalFileName(file.getOriginalFilename());
            newsImg.setContentType(file.getContentType());
            newsImg.setSize(file.getSize());
            newsBottomMenuImgRepository.save(newsImg);
            String path=uploadPath+file.getOriginalFilename();
            try{
                File file1=new File(path);
                if (!file1.isAbsolute()){
                    file1.createNewFile();
                }file.transferTo(file1);
                return newsImg;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }
    public Result saveNewsBottomMenu(
            @RequestParam(name = "title") String title,
            @RequestParam(name = "description") String description,
            @RequestParam(name = "image_id") Long image_id
    ){
       NewsBottomMenu news=new NewsBottomMenu();
        news.setTitle(title);
        news.setDescription(description);
        news.setImage_id(image_id);
        news.setIsPublish(false);
        news.setCreated_at(new Date());
        news.setUpdated_at(new Date());
        newsBottomMenuRepository.save(news);
        return new Result(true, "Successfully saved news bottom menu");
    }
    public NewsBottomMenu getNewsMenuById(@PathVariable Long id){
        Optional<NewsBottomMenu> byId = newsBottomMenuRepository.findById(id);
        if (byId.isPresent()){
            return byId.get();
        }return null;
    }
    public Result editNewsMenu(@PathVariable Long id,
                           @RequestParam(name = "title") String title,
                           @RequestParam(name = "description") String description,
                           @RequestParam(name = "image_id") Long image_id){
        Optional<NewsBottomMenu> byId = newsBottomMenuRepository.findById(id);
        if (byId.isPresent()){
            NewsBottomMenu newsBottomMenu = byId.get();
            newsBottomMenu.setId(id);
            newsBottomMenu.setTitle(title);
            newsBottomMenu.setImage_id(image_id);
           newsBottomMenu.setIsPublish(false);
            newsBottomMenu.setCreated_at(new Date());
            newsBottomMenu.setUpdated_at(new Date());
            newsBottomMenuRepository.save(newsBottomMenu);
            return new Result(true, "Successfully updated news menu");
        }return new Result(false, "News menu not found");
    }
    public Result deleteNewsMenu(@PathVariable Long id){
        Optional<NewsBottomMenu> byId = newsBottomMenuRepository.findById(id);
        if (byId.isPresent()){
            NewsBottomMenu newsBottomMenu = byId.get();
            NewsBottomMenuImg newsBottomMenuImg = newsBottomMenuImgRepository.findById(newsBottomMenu.getImage_id()).get();
            File file=new File(uploadPath+newsBottomMenuImg.getOriginalFileName());
            if (file.delete()){
                newsBottomMenuImgRepository.delete(newsBottomMenuImg);
                newsBottomMenuRepository.delete(newsBottomMenu);
            }
        }return new Result(false, "News menu not found");
    }
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public byte[] getFileFromServer(HttpServletResponse response, Long image_id) {
        try {
            Optional<NewsBottomMenuImg> byId = newsBottomMenuImgRepository.findById(image_id);
            if (byId.isPresent()) {
                NewsBottomMenuImg newsBottomMenuImg = byId.get();
                BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(new File(uploadPath + newsBottomMenuImg.getOriginalFileName())));
                byte[] bytes = Files.readAllBytes(Paths.get(uploadPath+newsBottomMenuImg.getOriginalFileName()));
//                System.out.println(bytes);
                response.setContentType(newsBottomMenuImg.getContentType());
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
