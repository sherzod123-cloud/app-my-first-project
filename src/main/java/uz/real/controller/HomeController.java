package uz.real.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import uz.real.entity.*;
import uz.real.repository.*;

import java.util.List;
import java.util.Optional;

@Controller
public class HomeController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepositroy roleRepositroy;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    TeacherRepository teacherRepository;

    @Autowired
    TalantRepository talantRepository;

    @Autowired
    CarouselPhotoRepository carouselPhotoRepository;

    @Autowired
    VideosRepository videosRepository;

    @Autowired
    PhotoGaleryRepository photoGaleryRepository;

    @Autowired
    DepartmentsRepsoitory departmentsRepsoitory;

    @Autowired
    NewsRepository newsRepository;

    @Autowired
    KamandaRepository kamandaRepository;

    @Autowired
    OurGoalRepository ourGoalRepository;

    @Autowired
    AboutTextRepository aboutTextRepository;

    @Autowired
    AboutCarouselRepository aboutCarouselRepository;

    @Autowired
    HistoryDescriptionRepository historyDescriptionRepository;

    @Autowired
    HistoryRepository historyRepository;

    @Autowired
    LessonScheduleRepository lessonScheduleRepository;

    @Autowired
    LessonSchedualSectionRepository lessonSchedualSectionRepository;

    @Autowired
    DocumentRepository documentRepository;

    @Autowired
    NewsBottomMenuRepository newsBottomMenuRepository;

    @Autowired
    RegionRepository regionRepository;

    @GetMapping("/")
    public String openHomePage(Model model){
        model.addAttribute("teachers", teacherRepository.findAllById(3));
        model.addAttribute("talants",talantRepository.findAllById(3));
        model.addAttribute("carouselImages", carouselPhotoRepository.findAllById((long) 3));
        model.addAttribute("videos", videosRepository.findAllById((long) 1));
        model.addAttribute("newsBotMenuIsPublish", newsBottomMenuRepository.findAllById());
        model.addAttribute("newsBotMenuNotPublish", newsBottomMenuRepository.getById());
        return "index";
    }
    @GetMapping("/teachers")
    public String openAllTeacherPage(Model model){
        model.addAttribute("teachers", teacherRepository.findAll());
        return "allTeacher";
    }
    @GetMapping("/talants")
    public String openAllTalantsPage(Model model){
        model.addAttribute("talants", talantRepository.findAll());
        return "allTalant";
    }
    @GetMapping("/allVideos")
    public String openAllVideosPage(Model model){
        model.addAttribute("videos", videosRepository.findAll());
        return "allVideos";

    }
//    @PreAuthorize("hasAuthority('/about')")
//    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/about")
    public String openAboutPage(Model model){
        model.addAttribute("kamandaLists", kamandaRepository.findAll());
        model.addAttribute("ourGoalList", ourGoalRepository.findAll());
        model.addAttribute("aboutTexts", aboutTextRepository.findAll());
        model.addAttribute("aboutCarousels", aboutCarouselRepository.findAll());
        return "about_bam";
    }
    @GetMapping("/morePeople/{id}")
    public String openMorePeoplePage(@PathVariable Long id, Model model){
        Optional<Kamanda> byId = kamandaRepository.findById(id);
        if (byId.isPresent()){
            Kamanda kamanda = byId.get();
            model.addAttribute("kamandaOne", kamanda);
            return "morePeople";
        }return null;
    }
    @GetMapping("/history")
    public String openHistoryPage(Model model){
        model.addAttribute("historyList", historyDescriptionRepository.findAll());
        model.addAttribute("historyTwoList", historyRepository.findAll());
        return "history";
    }
    @GetMapping("/contacts")
    public String openContactsPage(){
        return "contacts";
    }
    @GetMapping("/departments")
    public String openDepartmentsPage(Model model){
        model.addAttribute("departments", departmentsRepsoitory.findAll());
        return "departments";
    }
    @GetMapping("/documents")
    public String openDocumentsPage(Model model){
        model.addAttribute("documentList", documentRepository.findAll());
        return "documents";
    }
    @GetMapping("/course")
    public String openCoursePage(Model model){
        model.addAttribute("regions", regionRepository.findAll());
        model.addAttribute("lessonSchedualeList", lessonScheduleRepository.findAll());
        return "course";
    }
    @GetMapping("/raspisaniya")
    public String openRaspisaniyaPage(Model model){
        model.addAttribute("lessonScheduales", lessonScheduleRepository.findAll());
        model.addAttribute("lessonSchedualSections", lessonSchedualSectionRepository.findAll());
        return "raspisaniya1";
    }

    @GetMapping("/photogalery")
    public String openPhotoGalery(Model model){
        model.addAttribute("photos", photoGaleryRepository.findAll());
        return "photogalery";
    }
    @GetMapping("/videogalery")
    public String openVideoGalery(){
        return "videogalery";
    }
    @GetMapping("/map")
    public String openMapPage(){
        return "map";
    }

    @GetMapping("/news")
    public String openNewsPage(Model model){
        model.addAttribute("newsList", newsRepository.getAllById((long) 3));
        model.addAttribute("newsPublish", newsRepository.getById((long) 1));
        model.addAttribute("isView", newsRepository.findAllById((long) 4));
        return "news";
    }
    @GetMapping("/moreIsPublishNews")
    public String openMoreNewsPage(Model model){
        model.addAttribute("newsPublish", newsRepository.getById((long) 1));
        return "moreIsPublishNews";
    }
    @GetMapping("/moreIsViewNews/{id}")
    public String openISViewNewsPage(@PathVariable Long id, Model model){
        Optional<News> byId = newsRepository.findById(id);
        if (byId.isPresent()){
            News news = byId.get();
            model.addAttribute("isNewsById", news);
            return "moreIsViewNews";
        }return null;
    }
    @GetMapping("/moreNews/{id}")
    public String openMoreNewsPage(@PathVariable Long id, Model model){
        Optional<News> byId = newsRepository.findById(id);
        if (byId.isPresent()){
            News news = byId.get();
            model.addAttribute("moreNews", news);
            return "moreNews";
        }return null;
    }
    @GetMapping("/teacher/search")
    public String search(@RequestParam(name = "keyword") String keyword, Model model) {
        System.out.println("Search qilinayotgan so'z =  "+keyword);
        List<Teacher> allByFullName = teacherRepository.findUsersByKeyword(keyword);
        if (allByFullName.size()<=0){
            model.addAttribute("notFound", "Bunday o'qituvchi mavjud emas");
            model.addAttribute("teachers", teacherRepository.findAllById(3));
            model.addAttribute("talants",talantRepository.findAllById(3));
            model.addAttribute("carouselImages", carouselPhotoRepository.findAllById((long) 3));
            model.addAttribute("videos", videosRepository.findAllById((long) 1));
            model.addAttribute("newsBotMenuIsPublish", newsBottomMenuRepository.findAllById());
            model.addAttribute("newsBotMenuNotPublish", newsBottomMenuRepository.getById());
            return "index";
        }
        model.addAttribute("teacherList", allByFullName);
        model.addAttribute("teachers", teacherRepository.findAllById(3));
        model.addAttribute("talants",talantRepository.findAllById(3));
        model.addAttribute("carouselImages", carouselPhotoRepository.findAllById((long) 3));
        model.addAttribute("videos", videosRepository.findAllById((long) 1));
        model.addAttribute("newsBotMenuIsPublish", newsBottomMenuRepository.findAllById());
        model.addAttribute("newsBotMenuNotPublish", newsBottomMenuRepository.getById());
        return "index";
    }

}
