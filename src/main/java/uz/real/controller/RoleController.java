package uz.real.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import uz.real.entity.Role;
import uz.real.model.Result;
import uz.real.repository.RoleRepositroy;
import uz.real.repository.TeacherRepository;

import java.util.*;

@Controller
@RequestMapping("/admin")
public class RoleController {

    @Autowired
    RoleRepositroy roleRepositroy;

    @Autowired
    TeacherRepository teacherRepository;


    @GetMapping("/roles")
    public String openRolePage(Model model){
        model.addAttribute("roles", roleRepositroy.findAll());
        model.addAttribute("teacherList", teacherRepository.findAll());
        return "admin/pages/auth/roles";
    }
    @PostMapping("/save/role")
    @ResponseBody
    public Result saveRole(@RequestParam(name = "role_name") String role_name,
                           @RequestParam(name = "code_name") String code_name){

                 Role role=new Role();
                 role.setRoleName(role_name);
                 roleRepositroy.save(role);
            return new Result(true, "Succcessfully saved role");
        }
    @GetMapping("/get/permissions/{id}")
    public String openOrGetPermissionListPage(@PathVariable Long id, Model model){
        Optional<Role> byId = roleRepositroy.findById(id);
        if (byId.isPresent()){
            Role role = byId.get();
            model.addAttribute("Role", role);
            model.addAttribute("teacherList", teacherRepository.findAll());
            return "admin/pages/auth/getPermissionByRoleId";
        }return "";
    }

    @GetMapping("/get/role/{id}")
    @ResponseBody
    public Role getRoleById(@PathVariable Long id){
        Optional<Role> byId = roleRepositroy.findById(id);
        if (byId.isPresent()){
            return byId.get();
        }return null;
    }
    @PutMapping("/edit/role/{id}")
    @ResponseBody
    public Result editRoleById(@PathVariable Long id, @RequestParam(name = "role_name") String role_name) {
        System.out.println("ROLE_NAMEEdit = " + role_name);
        Optional<Role> byId = roleRepositroy.findById(id);
        if (byId.isPresent()) {
            Role role = byId.get();
            role.setId(id);
            role.setRoleName(role_name);
            roleRepositroy.save(role);
            return new Result(true, "Successfully edited role");
        }return new Result(false, "Role not found");
    }

    @DeleteMapping("/delete/role/{id}")
    @ResponseBody
    public Result deleteRole(@PathVariable Long id){
        Optional<Role> byId = roleRepositroy.findById(id);
        if (byId.isPresent()){
            roleRepositroy.delete(byId.get());
            return new Result(true, "Successfully deleted role");
        }return new Result(false, "Role not found");
    }
}
