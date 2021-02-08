package uz.real.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import uz.real.entity.LessonSchedualSection;
import uz.real.entity.LessonSchedule;
import uz.real.model.Result;
import uz.real.payload.ReqLessonScheduleSection;
import uz.real.repository.LessonSchedualSectionRepository;
import uz.real.repository.LessonScheduleRepository;
import uz.real.repository.TeacherRepository;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class LessonScheduleSectionController {

    @Autowired
    LessonScheduleRepository lessonScheduleRepository;

    @Autowired
    LessonSchedualSectionRepository lessonSchedualSectionRepository;

    @Autowired
    TeacherRepository teacherRepository;

    @GetMapping("/lesson/section")
    public String openAdminLessonScheduleSectionPage(Model model){
        model.addAttribute("lessonScheduleList",lessonScheduleRepository.findAll());
        model.addAttribute("lessonScheduleSectionList", lessonSchedualSectionRepository.findAll());
        model.addAttribute("teacherList", teacherRepository.findAll());
        return "admin/pages/menu/lesson_schedule_section";
    }
    @PostMapping("/schedulesection/save")
    @ResponseBody
    public Result saveLessonScheduleSection(@ModelAttribute ReqLessonScheduleSection reqLessonScheduleSection,
                                            @RequestParam(name = "lesson_schedule")LessonSchedule lessonSchedule){
        LessonSchedualSection lessonSchedualSection=new LessonSchedualSection();
        lessonSchedualSection.setSectionName(reqLessonScheduleSection.getSectionName());
        lessonSchedualSection.setMondayTime(reqLessonScheduleSection.getMondayTime());
        lessonSchedualSection.setTuesdayTime(reqLessonScheduleSection.getTuesdayTime());
        lessonSchedualSection.setWednesdayTime(reqLessonScheduleSection.getWednesdayTime());
        lessonSchedualSection.setThursdayTime(reqLessonScheduleSection.getThursdayTime());
        lessonSchedualSection.setFridayTime(reqLessonScheduleSection.getFridayTime());
        lessonSchedualSection.setSaturdayTime(reqLessonScheduleSection.getSaturdayTime());
        lessonSchedualSection.setLessonSchedule(lessonSchedule);
        lessonSchedualSectionRepository.save(lessonSchedualSection);
        return new Result(true, "Successfully saved Lesson schedule section");
    }
    @GetMapping("/schedualsection/get/{id}")
    @ResponseBody
    public LessonSchedualSection getLessonSchedualSectionById(@PathVariable Long id){
        Optional<LessonSchedualSection> byId = lessonSchedualSectionRepository.findById(id);
        if (byId.isPresent()){
            return byId.get();
        }return null;
    }
    @PutMapping("/schedulsection/edit/{id}")
    @ResponseBody
    public Result editLessonScheduleSection(@PathVariable Long id,
                                            @ModelAttribute ReqLessonScheduleSection reqLessonScheduleSection,
                                            @RequestParam(name = "lesson_schedule") LessonSchedule lessonSchedule){
        Optional<LessonSchedualSection> byId = lessonSchedualSectionRepository.findById(id);
        if (byId.isPresent()){
            LessonSchedualSection lessonSchedualSection = byId.get();
            lessonSchedualSection.setId(id);
            lessonSchedualSection.setSectionName(reqLessonScheduleSection.getSectionName());
            lessonSchedualSection.setMondayTime(reqLessonScheduleSection.getMondayTime());
            lessonSchedualSection.setTuesdayTime(reqLessonScheduleSection.getTuesdayTime());
            lessonSchedualSection.setWednesdayTime(reqLessonScheduleSection.getWednesdayTime());
            lessonSchedualSection.setThursdayTime(reqLessonScheduleSection.getThursdayTime());
            lessonSchedualSection.setFridayTime(reqLessonScheduleSection.getFridayTime());
            lessonSchedualSection.setSaturdayTime(reqLessonScheduleSection.getSaturdayTime());
            lessonSchedualSection.setLessonSchedule(lessonSchedule);
            lessonSchedualSectionRepository.save(lessonSchedualSection);
            return new Result(true, "Successfully edited LessonScheduleSection");
        }return new Result(false, "LessonScheduleSection not found!");
    }
    @DeleteMapping("/schedualsection/delete/{id}")
    @ResponseBody
    public Result deleteLessonScheduleSection(@PathVariable Long id){
        Optional<LessonSchedualSection> byId = lessonSchedualSectionRepository.findById(id);
        if (byId.isPresent()){
            lessonSchedualSectionRepository.delete(byId.get());
            return new Result(true, "Successfully delete lesson schedule section");
        }return new Result(false, "LessonScheduleSection id not found!");
    }
    @GetMapping("/lesSchedualSection/get/{lessonId}")
    @ResponseBody
    public List<LessonSchedualSection> getLessonSchedualSectionByLessonSchedualId(@PathVariable Long lessonId, Model model){
        List<LessonSchedualSection> allByLessonSchedule_id = lessonSchedualSectionRepository.getAllByLessonSchedule_Id(lessonId);
        model.addAttribute("lesson", allByLessonSchedule_id);
        return allByLessonSchedule_id;
    }
    @GetMapping("/lessonSection/{id}")
    @ResponseBody
    public LessonSchedualSection getLessonScheduleList(@PathVariable Long id){
        return lessonSchedualSectionRepository.getOne(id);
    }
}
