package uz.real.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import uz.real.entity.History;
import uz.real.entity.HistoryImg;
import uz.real.entity.News;
import uz.real.entity.NewsImg;
import uz.real.model.Result;
import uz.real.repository.HistoryImgRepository;
import uz.real.repository.HistoryRepository;

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
public class HisTwoService {

    private String uploadPath="D:\\sherzod\\app-spring-security-lesson1\\src\\main\\resources\\static\\assets\\histwoImg/";


    @Autowired
    HistoryRepository historyRepository;

    @Autowired
    HistoryImgRepository historyImgRepository;


    public HistoryImg saveFileToServer(@RequestParam(name = "file") MultipartFile file){
        HistoryImg historyImg=new HistoryImg();
        if (!file.isEmpty()){
            historyImg.setName(file.getName());
            historyImg.setOriginalFileName(file.getOriginalFilename());
            historyImg.setContentType(file.getContentType());
            historyImg.setSize(file.getSize());
            historyImgRepository.save(historyImg);
            String path=uploadPath+file.getOriginalFilename();
            try{
                File file1=new File(path);
                if (!file1.isAbsolute()){
                    file1.createNewFile();
                }file.transferTo(file1);
                return historyImg;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }

    public Result saveHistoryTwo(
            @RequestParam(name = "title") String title,
            @RequestParam(name = "description") String description,
            @RequestParam(name = "image_id") Long image_id
    ){
        History history=new History();
        history.setTitle(title);
        history.setDescription(description);
        history.setImage_id(image_id);
        historyRepository.save(history);
        return new Result(true, "Successfully saved history");
    }
    public History getHistoryTwoById(@PathVariable Long id){
        Optional<History> byId = historyRepository.findById(id);
        if (byId.isPresent()){
            return byId.get();
        }return null;
    }
    public Result editHistory(@PathVariable Long id,
                           @RequestParam(name = "title") String title,
                           @RequestParam(name = "description") String description,
                           @RequestParam(name = "image_id") Long image_id){
        Optional<History> byId = historyRepository.findById(id);
        if (byId.isPresent()){
            History history = byId.get();
            history.setId(id);
            history.setImage_id(image_id);
            history.setTitle(title);
            history.setDescription(description);
          historyRepository.save(history);
            return new Result(true, "Successfully updated history!");
        }
        return new Result(false, "History not found!");
    }
    public Result deleteHistoryTwo(@PathVariable Long id){
        Optional<History> byId = historyRepository.findById(id);
        if (byId.isPresent()){
            History history = byId.get();
            Optional<HistoryImg> byId1 = historyImgRepository.findById(history.getImage_id());
            if (byId1.isPresent()){
                HistoryImg historyImg = byId1.get();
                String path=uploadPath+historyImg.getOriginalFileName();
                File file=new File(path);
                if (file.delete()){
                    historyImgRepository.delete(historyImg);
                    historyRepository.delete(history);
                    return new Result(true, "Successfully deleted!");
                }
            }
        }
        return new Result(false, "History not found!");
    }
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public byte[] getFileFromServer(HttpServletResponse response, Long image_id) {
        try {
            Optional<HistoryImg> byId = historyImgRepository.findById(image_id);
            if (byId.isPresent()) {
                HistoryImg historyImg = byId.get();
                BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(new File(uploadPath + historyImg.getOriginalFileName())));
                byte[] bytes = Files.readAllBytes(Paths.get(uploadPath+historyImg.getOriginalFileName()));
//                System.out.println(bytes);
                response.setContentType(historyImg.getContentType());
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
