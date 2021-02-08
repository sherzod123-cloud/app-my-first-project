package uz.real.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import uz.real.entity.HistoryDescription;
import uz.real.entity.LogoHistory;
import uz.real.entity.News;
import uz.real.entity.NewsImg;
import uz.real.model.Result;
import uz.real.repository.HistoryDescriptionRepository;
import uz.real.repository.LogoHistoryRepository;
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
import java.util.Optional;

@Service
public class HistoryCenterService {

    @Autowired
    LogoHistoryRepository logoHistoryRepository;

    @Autowired
    HistoryDescriptionRepository historyDescriptionRepository;

    private String uploadPath="D:\\sherzod\\app-spring-security-lesson1\\src\\main\\resources\\static\\assets\\logoHistory\\";


    public LogoHistory saveFileToServer(@RequestParam(name = "file") MultipartFile file){
        LogoHistory logoHistory=new LogoHistory();
        if (!file.isEmpty()){
         logoHistory.setName(file.getName());
            logoHistory.setOriginalFileName(file.getOriginalFilename());
            logoHistory.setContentType(file.getContentType());
            logoHistory.setSize(file.getSize());
           logoHistoryRepository.save(logoHistory);
            String path=uploadPath+file.getOriginalFilename();
            try{
                File file1=new File(path);
                if (!file1.isAbsolute()){
                    file1.createNewFile();
                }file.transferTo(file1);
                return logoHistory;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }

    public Result saveHistory(
            @RequestParam(name = "description") String description,
            @RequestParam(name = "image_id") Long image_id
    ){
        HistoryDescription historyDescription=new HistoryDescription();
        historyDescription.setDescription(description);
        historyDescription.setImage_id(image_id);
        historyDescriptionRepository.save(historyDescription);
        return new Result(true, "Successfully saved history");
    }
    public HistoryDescription getHistoryById(@PathVariable Long id){
        Optional<HistoryDescription> byId = historyDescriptionRepository.findById(id);
        if (byId.isPresent()){
            return byId.get();
        }return null;
    }
    public Result editHistory(@PathVariable Long id,
                           @RequestParam(name = "description") String description,
                           @RequestParam(name = "image_id") Long image_id){
        Optional<HistoryDescription> byId = historyDescriptionRepository.findById(id);
        if (byId.isPresent()){
            HistoryDescription historyDescription = byId.get();
            historyDescription.setId(id);
            historyDescription.setImage_id(image_id);
            historyDescription.setDescription(description);
           historyDescriptionRepository.save(historyDescription);
            return new Result(true, "Successfully updated history!");
        }
        return new Result(false, "History not found!");
    }
    public Result deleteHistory(@PathVariable Long id){
        Optional<HistoryDescription> byId = historyDescriptionRepository.findById(id);
        if (byId.isPresent()){
            HistoryDescription historyDescription = byId.get();
            Optional<LogoHistory> byId1 = logoHistoryRepository.findById(historyDescription.getImage_id());
            if (byId1.isPresent()){
                LogoHistory logoHistory = byId1.get();
                String path=uploadPath+logoHistory.getOriginalFileName();
                File file=new File(path);
                if (file.delete()){
                    logoHistoryRepository.delete(logoHistory);
                    historyDescriptionRepository.delete(historyDescription);
                    return new Result(true, "Successfully deleted!");
                }
            }
        }
        return new Result(false, "History not found!");
    }
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public byte[] getFileFromServer(HttpServletResponse response, Long image_id) {
        try {
            Optional<LogoHistory> byId = logoHistoryRepository.findById(image_id);
            if (byId.isPresent()) {
                LogoHistory logoHistory = byId.get();
                BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(new File(uploadPath + logoHistory.getOriginalFileName())));
                byte[] bytes = Files.readAllBytes(Paths.get(uploadPath+logoHistory.getOriginalFileName()));
//                System.out.println(bytes);
                response.setContentType(logoHistory.getContentType());
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
