package uz.real.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import uz.real.entity.OurGoal;
import uz.real.model.Result;
import uz.real.repository.OurGoalRepository;
import uz.real.repository.TeacherRepository;

import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class OurGoalController {

    @Autowired
    OurGoalRepository ourGoalRepository;

    @Autowired
    TeacherRepository teacherRepository;

    @GetMapping("/ourgoal")
    public String openOurGoalPage(Model model){
        model.addAttribute("ourGoals", ourGoalRepository.findAll());
        model.addAttribute("teacherList", teacherRepository.findAll());
        return "admin/pages/menu/ourgoal";
    }

    @PostMapping("/goal/save")
    @ResponseBody
    public Result saveGoal(@RequestParam(name = "description") String description){
        OurGoal ourGoal=new OurGoal();
        ourGoal.setDescription(description);
        ourGoalRepository.save(ourGoal);
        return new Result(true, "Successfully saved Goal");
    }
    @GetMapping("/goal/get/{id}")
    @ResponseBody
    public OurGoal getGoalById(@PathVariable Long id){
        Optional<OurGoal> byId = ourGoalRepository.findById(id);
        if (byId.isPresent()){
            return byId.get();
        }return null;
    }
    @PutMapping("/goal/edit/{id}")
    @ResponseBody
    public Result editGoal(@PathVariable Long id, @RequestParam(name = "description") String description){
        Optional<OurGoal> byId = ourGoalRepository.findById(id);
        if (byId.isPresent()){
            OurGoal ourGoal = byId.get();
            ourGoal.setId(id);
            ourGoal.setDescription(description);
            ourGoalRepository.save(ourGoal);
            return new Result(true, "Successfully edit goal!");
        }return new Result(false, "Goal not found");
    }
    @DeleteMapping("/goal/delete/{id}")
    @ResponseBody
    public Result deleteGoal(@PathVariable Long id){
        Optional<OurGoal> byId = ourGoalRepository.findById(id);
        if (byId.isPresent()){
            ourGoalRepository.delete(byId.get());
            return new Result(true, "Successfully deleted goal");
        }return new Result(false, "Not found! Goal");
    }
}
