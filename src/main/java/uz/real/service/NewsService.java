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
import uz.real.entity.NewsImg;
import uz.real.entity.TalantAttachment;
import uz.real.model.Result;
import uz.real.repository.NewsImgRepository;
import uz.real.repository.NewsRepository;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class NewsService {

    private String uploadPath="D:\\sherzod\\app-spring-security-lesson1\\src\\main\\resources\\static\\assets\\newsImg/";

    @Autowired
    NewsRepository newsRepository;

    @Autowired
    NewsImgRepository newsImgRepository;

    public NewsImg saveFileToServer(@RequestParam(name = "file")MultipartFile file){
        NewsImg newsImg=new NewsImg();
        if (!file.isEmpty()){
            newsImg.setName(file.getName());
            newsImg.setOriginalFileName(file.getOriginalFilename());
            newsImg.setContentType(file.getContentType());
            newsImg.setSize(file.getSize());
            newsImgRepository.save(newsImg);
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

    public Result saveNews(
            @RequestParam(name = "title") String title,
            @RequestParam(name = "description") String description,
            @RequestParam(name = "image_id") Long image_id
    ){
        News news=new News();
        news.setTitle(title);
        news.setDescription(description);
        news.setIsPublished(false);
        news.setIsView(false);
        news.setImage_id(image_id);
        news.setCreated_at(new Date());
        news.setUpdated_at(new Date());
        newsRepository.save(news);
        return new Result(true, "Successfully saved news");
    }
    public News getNewsById(@PathVariable Long id){
        Optional<News> byId = newsRepository.findById(id);
        if (byId.isPresent()){
           return byId.get();
        }return null;
    }
    public Result editNews(@PathVariable Long id,
                           @RequestParam(name = "title") String title,
                           @RequestParam(name = "description") String description,
                           @RequestParam(name = "image_id") Long image_id){
        Optional<News> byId = newsRepository.findById(id);
        if (byId.isPresent()){
            News news = byId.get();
                    news.setId(id);
                    news.setImage_id(image_id);
                    news.setTitle(title);
                    news.setDescription(description);
                    news.setIsPublished(false);
                    news.setIsView(false);
                    news.setCreated_at(new Date());
                    news.setUpdated_at(new Date());
                    newsRepository.save(news);
                    return new Result(true, "Successfully updated news!");
                }
        return new Result(false, "News not found!");
    }
    public Result deleteNews(@PathVariable Long id){
        Optional<News> byId = newsRepository.findById(id);
        if (byId.isPresent()){
            News news = byId.get();
            Optional<NewsImg> byId1 = newsImgRepository.findById(news.getImage_id());
            if (byId1.isPresent()){
                NewsImg newsImg = byId1.get();
                String path=uploadPath+newsImg.getOriginalFileName();
                File file=new File(path);
                if (file.delete()){
                    newsRepository.delete(news);
                    newsImgRepository.delete(newsImg);
                    return new Result(true, "Successfully deleted!");
                }
            }
        }
        return new Result(false, "News not found!");
    }
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public byte[] getFileFromServer(HttpServletResponse response, Long image_id) {
        try {
            Optional<NewsImg> byId = newsImgRepository.findById(image_id);
            if (byId.isPresent()) {
                NewsImg newsImg = byId.get();
                BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(new File(uploadPath + newsImg.getOriginalFileName())));
                byte[] bytes = Files.readAllBytes(Paths.get(uploadPath+newsImg.getOriginalFileName()));
//                System.out.println(bytes);
                response.setContentType(newsImg.getContentType());
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
