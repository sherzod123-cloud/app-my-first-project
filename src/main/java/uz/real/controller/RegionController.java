package uz.real.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import uz.real.entity.Region;
import uz.real.model.Result;
import uz.real.repository.RegionRepository;
import uz.real.repository.TeacherRepository;

import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class RegionController {

    @Autowired
    RegionRepository regionRepository;

    @Autowired
    TeacherRepository teacherRepository;

//    @PreAuthorize("hasAuthority('/admin/region')")
    @GetMapping("/region")
    public String openRegionPage(Model model){
        model.addAttribute("regions", regionRepository.findAll());
        model.addAttribute("teacherList",teacherRepository.findAll());
        return "admin/pages/menu/region";
    }
    @PostMapping("/region/save")
    @ResponseBody
//    @PreAuthorize("hasAuthority('/admin/region/save')")
    public Result saveRegion(@RequestParam(name = "region_name") String region_name){
        Region region=new Region();
        region.setRegion_name(region_name);
        regionRepository.save(region);
        return new Result(true, "Successfully saved region");
    }
    @GetMapping("/region/get/{id}")
    @ResponseBody
    public Region getRegionById(@PathVariable Long id){
        Optional<Region> byId = regionRepository.findById(id);
        if (byId.isPresent()){
           return byId.get();
        }return null;
    }
    @PutMapping("/region/edit/{id}")
    @ResponseBody
    public Result editRegion(@PathVariable Long id, @RequestParam(name = "region_name") String region_name){
        Optional<Region> byId = regionRepository.findById(id);
        if (byId.isPresent()){
            Region region = byId.get();
            region.setId(id);
            region.setRegion_name(region_name);
            regionRepository.save(region);
            return new Result(true, "Successfully edit region");
        }return new Result(false, "Region not found");
    }
    @DeleteMapping("/region/delete/{id}")
    @ResponseBody
    public Result deleteRegion(@PathVariable Long id){
        Optional<Region> byId = regionRepository.findById(id);
        if (byId.isPresent()){
            regionRepository.delete(byId.get());
            return new Result(true, "Successfully delete region");
        }return new Result(false, "Region not found");
    }


}
