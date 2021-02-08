package uz.real.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import uz.real.entity.Departments;
import uz.real.model.Result;
import uz.real.payload.ReqDepartments;
import uz.real.repository.DepartmentsRepsoitory;
import uz.real.repository.TeacherRepository;
import uz.real.service.DepartmentsService;

import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class DepartmentsController {

    @Autowired
    DepartmentsService departmentsService;

    @Autowired
    DepartmentsRepsoitory departmentsRepsoitory;

    @Autowired
    TeacherRepository teacherRepository;

    @GetMapping("/departments")
    public String openDepartmentsPage(Model model){
        model.addAttribute("departments", departmentsRepsoitory.findAll());
        model.addAttribute("teacherList", teacherRepository.findAll());
        return "admin/pages/menu/departments";
    }
    @PostMapping("/rayon/save")
    @ResponseBody
    public Result saveRayon(
            @RequestParam(name = "name") String name,
            @RequestParam(name = "address") String address,
            @RequestParam(name = "phone1") String phoneOne,
            @RequestParam(name = "phone2") String phoneTwo,
            @RequestParam(name = "phone3") String phoneThree,
            @RequestParam(name = "df") String directFullName
    ){
        Departments departments=new Departments();
        departments.setName(name);
        departments.setAddress(address);
        departments.setPhone1(phoneOne);
        departments.setPhone2(phoneTwo);
        departments.setPhone3(phoneThree);
        departments.setDf(directFullName);
        departmentsRepsoitory.save(departments);
     return new Result(true, "Successfully saved Rayon");
    }
//    @PostMapping("/save/rayon")
//    @ResponseBody
//    public Result saveRayon(@ModelAttribute ReqDepartments reqDepartments){
//        return departmentsService.saveRayon(reqDepartments);
//    }
     @GetMapping("/rayon/get/{id}")
     @ResponseBody
    public Departments getDepartmentsById(@PathVariable Long id){
         Optional<Departments> byId = departmentsRepsoitory.findById(id);
         if (byId.isPresent()){
             return byId.get();
         }return null;
     }
    @PutMapping("/rayon/edit/{id}")
    @ResponseBody
    public Result editRayon(@PathVariable Long id,
                            @RequestParam(name = "name") String name,
                            @RequestParam(name = "address") String address,
                            @RequestParam(name = "phone1") String phoneOne,
                            @RequestParam(name = "phone2") String phoneTwo,
                            @RequestParam(name = "phone3") String phoneThree,
                            @RequestParam(name = "df") String directFullName){
        Optional<Departments> byId = departmentsRepsoitory.findById(id);
        if (byId.isPresent()){
            Departments departments = byId.get();
            departments.setId(id);
            departments.setName(name);
            departments.setAddress(address);
            departments.setPhone1(phoneOne);
            departments.setPhone2(phoneTwo);
            departments.setPhone3(phoneThree);
            departments.setDf(directFullName);
            departmentsRepsoitory.save(departments);
            return new Result(true, "Successfully updated rayon!");
        }return new Result(false, "Rayon not found! Not edited");
    }
    @DeleteMapping("/rayon/delete/{id}")
    @ResponseBody
    public Result deleteRayon(@PathVariable Long id){
        departmentsRepsoitory.deleteById(id);
        return new Result(true, "Successfully deleted!");
    }

}
