package uz.real.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import uz.real.entity.Document;
import uz.real.model.Result;
import uz.real.repository.DocumentRepository;
import uz.real.repository.TeacherRepository;

import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class DocumentController {

    @Autowired
    DocumentRepository documentRepository;

    @Autowired
    TeacherRepository teacherRepository;

//    @PreAuthorize("hasAuthority('document')")
    @GetMapping("/document")
    public String openAdminDocumentPage(Model model){
        model.addAttribute("documents", documentRepository.findAll());
        model.addAttribute("teacherList", teacherRepository.findAll());
        return "admin/pages/menu/document";
    }

//    @PreAuthorize("hasAuthority('saveDocument')")
    @PostMapping("/save/document")
    @ResponseBody
    public Result saveDocument(@RequestParam(name = "title") String title){
        Document document1=new Document();
        document1.setTitle(title);
        documentRepository.save(document1);
        return new Result(true, "Successfully saved document");
    }
    @GetMapping("/get/document/{id}")
    @ResponseBody
    public Document getDocumentById(@PathVariable Long id){
        Optional<Document> byId = documentRepository.findById(id);
        if (byId.isPresent()){
            return byId.get();
        }return null;
    }
//    @PreAuthorize("hasAuthority('editDocument')")
    @PutMapping("/edit/document/{id}")
    @ResponseBody
    public Result editDocument(@PathVariable Long id, @RequestParam(name = "title") String title){
        Optional<Document> byId = documentRepository.findById(id);
        if (byId.isPresent()){
            Document document1 = byId.get();
            document1.setId(id);
            document1.setTitle(title);
            documentRepository.save(document1);
            return new Result(true, "Successfully updated document");
        }return new Result(false, "Document not found");
    }
    @DeleteMapping("/delete/document/{id}")
    @ResponseBody
    public Result deleteDocument(@PathVariable Long id){
        Optional<Document> byId = documentRepository.findById(id);
        if (byId.isPresent()){
            documentRepository.delete(byId.get());
            return new Result(true, "Successfully deleted document");
        }return new Result(false, "Document not found");
    }

}
