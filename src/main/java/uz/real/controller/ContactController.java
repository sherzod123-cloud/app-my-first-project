package uz.real.controller;

import javafx.scene.control.Alert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import uz.real.entity.Contact;
import uz.real.model.Result;
import uz.real.repository.ContactRepository;
import uz.real.repository.TeacherRepository;

import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class ContactController {

    @Autowired
    ContactRepository contactRepository;

    @Autowired
    TeacherRepository teacherRepository;

    @GetMapping("/contacts")
    public String openAdminContactPage(Model model){
        model.addAttribute("contacts", contactRepository.findAll());
        model.addAttribute("teacherList", teacherRepository.findAll());
        return "admin/pages/menu/contacts";
    }

//    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/contact/save")
    @ResponseBody
    public Result saveContact(
            @RequestParam(name = "name") String name,
            @RequestParam(name = "email") String email,
            @RequestParam(name = "phone") String phone,
            @RequestParam(name = "posts") String posts
    ){
        Contact contact=new Contact();
        contact.setName(name);
        contact.setEmail(email);
        contact.setPhone(phone);
        contact.setPosts(posts);
        contactRepository.save(contact);
        return new Result(true, "Successfully send your posts");
    }
    @GetMapping("/contact/get/{id}")
    @ResponseBody
    public Contact getContactById(@PathVariable Long id){
        Optional<Contact> byId = contactRepository.findById(id);
        if (byId.isPresent()){
            return byId.get();
        }return null;
    }
    @PutMapping("/contact/edit/{id}")
    @ResponseBody
    public Result editContact(@PathVariable Long id,
                              @RequestParam(name = "name") String name,
                              @RequestParam(name = "email") String email,
                              @RequestParam(name = "phone") String phone,
                              @RequestParam(name = "posts") String posts){
        Optional<Contact> byId = contactRepository.findById(id);
        if (byId.isPresent()){
            Contact contact = byId.get();
            contact.setId(id);
            contact.setName(name);
            contact.setEmail(email);
            contact.setPhone(phone);
            contact.setPosts(posts);
            contactRepository.save(contact);
            return new Result(true, "Successfully updated Contact!");
        }return new Result(false, "Contact not found!");
    }
    @DeleteMapping("/contact/delete/{id}")
    @ResponseBody
    public Result deleteContact(@PathVariable Long id){
        Optional<Contact> byId = contactRepository.findById(id);
        if (byId.isPresent()){
            Contact contact = byId.get();
            contactRepository.delete(contact);
            return new Result(true, "Successfully deleted!");
        }return new Result(false, "Contact not found?");
    }

}
