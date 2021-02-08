package uz.real.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import uz.real.entity.District;
import uz.real.entity.Region;
import uz.real.model.Result;
import uz.real.repository.DistrictRepository;
import uz.real.repository.RegionRepository;
import uz.real.repository.TeacherRepository;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class DistrictController {

    @Autowired
    DistrictRepository districtRepository;

    @Autowired
    RegionRepository regionRepository;

    @Autowired
    TeacherRepository teacherRepository;

    @GetMapping("/district")
   public String openDistrictPage(Model model){
        model.addAttribute("regionList", regionRepository.findAll());
        model.addAttribute("districtList", districtRepository.findAll());
        model.addAttribute("teacherList", teacherRepository.findAll());
        return "admin/pages/menu/district";
    }
    @PostMapping("/district/save")
    @ResponseBody
    public Result saveDistrict(@RequestParam(name = "district_name") String district_name,
                               @RequestParam(name = "region_name")Region region){
        District district=new District();
        district.setDistrict_name(district_name);
        district.setRegion(region);
        districtRepository.save(district);
        return new Result(true, "Successfully saved district");
    }
    @GetMapping("/district/get/{id}")
    @ResponseBody
    public District getDistrictById(@PathVariable Long id){
        Optional<District> byId = districtRepository.findById(id);
        if (byId.isPresent()){
            return byId.get();
        }return null;
    }
    @PutMapping("/district/edit/{id}")
    @ResponseBody
    public Result editDistrict(@PathVariable Long id,
                               @RequestParam(name = "district_name") String district_name,
                               @RequestParam(name = "region_name")Region region){
        Optional<District> byId = districtRepository.findById(id);
        if (byId.isPresent()){
            District district = byId.get();
            district.setId(id);
            district.setDistrict_name(district_name);
            district.setRegion(region);
            districtRepository.save(district);
            return new Result(true, "Successfully edited district");
        }return new Result(false, "District not found!");
    }
    @DeleteMapping("/district/delete/{id}")
    @ResponseBody
    public Result deleteDistrict(@PathVariable Long id){
        Optional<District> byId = districtRepository.findById(id);
        if (byId.isPresent()){
            districtRepository.delete(byId.get());
            return new Result(true, "Successfully deleted district");
        }return new Result(false, "District not found!");
    }
    @GetMapping("/ditrict/getByregionId/{region_id}")
    @ResponseBody
    public List<District> getDistricts(@PathVariable Long region_id){
       return districtRepository.findAllByRegionId(region_id);
    }
}
