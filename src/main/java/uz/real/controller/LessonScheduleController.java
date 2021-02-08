package uz.real.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import uz.real.entity.LessonSchedule;
import uz.real.model.Result;
import uz.real.repository.LessonScheduleRepository;
import uz.real.repository.TeacherRepository;

import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class LessonScheduleController {

    @Autowired
    LessonScheduleRepository classScheduleRepository;

    @Autowired
    TeacherRepository teacherRepository;

    @GetMapping("/classschedule")
    public String openClassSchedulePage(Model model){
        model.addAttribute("classScheduleList", classScheduleRepository.findAll());
        model.addAttribute("teacherList",teacherRepository.findAll());
        return "admin/pages/menu/class_schedule";
    }
    @PostMapping("/schedual/save")
    @ResponseBody
    public Result saveClassSchedual(@RequestParam(name = "className") String className){
        LessonSchedule classSchedule=new LessonSchedule();
        classSchedule.setClassName(className);
        classScheduleRepository.save(classSchedule);
        return new Result(true, "Successfully saved class schedule");
    }
    @GetMapping("/schedual/get/{id}")
    @ResponseBody
    public LessonSchedule getClassSchedualById(@PathVariable Long id){
        Optional<LessonSchedule> byId = classScheduleRepository.findById(id);
       if (byId.isPresent()){
           return byId.get();
       }return null;
    }
    @PutMapping("/schedul/edit/{id}")
    @ResponseBody
    public Result editClassSchedule(@PathVariable Long id, @RequestParam(name = "className") String className){
        Optional<LessonSchedule> byId = classScheduleRepository.findById(id);
        if (byId.isPresent()){
            LessonSchedule classSchedule = byId.get();
            classSchedule.setId(id);
            classSchedule.setClassName(className);
            classScheduleRepository.save(classSchedule);
            return new Result(true, " Successfully edited class schedule");
        }return new Result(false, "Class schedule not found");
    }
    @DeleteMapping("/schedual/delete/{id}")
    @ResponseBody
    public Result deleteClassSchedual(@PathVariable Long id){
        Optional<LessonSchedule> byId = classScheduleRepository.findById(id);
        if (byId.isPresent()){
            LessonSchedule classSchedule = byId.get();
            classScheduleRepository.delete(classSchedule);
            return new Result(true, "Successfully deleted");
        }
       return new Result(false, "Class schedule not found");
    }


}
