package uz.real.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import uz.real.entity.Course;
import uz.real.entity.District;
import uz.real.entity.LessonSchedualSection;
import uz.real.model.Result;
import uz.real.payload.ReqCourse;
import uz.real.repository.*;

import java.util.Date;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class CourseController {

    @Autowired
    RegionRepository regionRepository;

    @Autowired
    LessonScheduleRepository lessonScheduleRepository;

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    TeacherRepository teacherRepository;

    @GetMapping("/courses")
    public String openCoursePage(Model model){
        model.addAttribute("courseList", courseRepository.findAll());
        model.addAttribute("regions",regionRepository.findAll());
        model.addAttribute("lessonSchedualeList", lessonScheduleRepository.findAll());
        model.addAttribute("teacherList", teacherRepository.findAll());
        return "admin/pages/menu/courses";

    }
    @PostMapping("/course/save")
    @ResponseBody
    public Result saveCourse(@ModelAttribute ReqCourse reqCourse,
                             @RequestParam(name = "district") District district,
                             @RequestParam(name = "section") LessonSchedualSection section){
        Course course=new Course();
        course.setFirst_name(reqCourse.getFirst_name());
        course.setLast_name(reqCourse.getLast_name());
        course.setMiddle_name(reqCourse.getMiddle_name());
        course.setDate(new Date());
        course.setPhone(reqCourse.getPhone());
        course.setDistrict(district);
        course.setSection(section);
        courseRepository.save(course);
        return new Result(true, "Successfully saved course");
    }
    @GetMapping("/course/get/{id}")
    @ResponseBody
    public Course getCourseById(@PathVariable Long id){
        Optional<Course> byId = courseRepository.findById(id);
        if (byId.isPresent()){
            return byId.get();
        }return null;
    }
    @PutMapping("/course/edit/{id}")
    @ResponseBody
    public Result editCourse(@PathVariable Long id,
                             @ModelAttribute ReqCourse reqCourse,
                             @RequestParam(name = "district") District district,
                             @RequestParam(name = "section") LessonSchedualSection section){
        Optional<Course> byId = courseRepository.findById(id);
        if (byId.isPresent()){
            Course course = byId.get();
            course.setId(id);
            course.setFirst_name(reqCourse.getFirst_name());
            course.setLast_name(reqCourse.getLast_name());
            course.setMiddle_name(reqCourse.getMiddle_name());
            course.setDate(new Date());
            course.setPhone(reqCourse.getPhone());
            course.setDistrict(district);
            course.setSection(section);
            courseRepository.save(course);
            return new Result(true, "Successfully edited course");
        }return new Result(false, "Course not found");
    }
    @DeleteMapping("/course/delete/{id}")
    @ResponseBody
    public Result deleteCourse(@PathVariable Long id){
        Optional<Course> byId = courseRepository.findById(id);
        if (byId.isPresent()){
            courseRepository.delete(byId.get());
            return new Result(true, "Successfully deleted course");
        }return new Result(false, "Course not found");
    }




}
