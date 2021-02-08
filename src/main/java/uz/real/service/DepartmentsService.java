package uz.real.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.real.repository.DepartmentsRepsoitory;

@Service
public class DepartmentsService {

    @Autowired
    DepartmentsRepsoitory departmentsRepsoitory;

//    public Result saveRayon(@ModelAttribute ReqDepartments reqDepartments){
//        Departments departments=new Departments();
//        departments.setName(reqDepartments.getName());
//        departments.setAddress(reqDepartments.getAddress());
//        departments.setPhoneOne(reqDepartments.getPhoneOne());
//        departments.setPhoneTwo(departments.getPhoneTwo());
//        departments.setPhoneThree(departments.getPhoneThree());
//        departments.setDirectFullName(departments.getDirectFullName());
//        departmentsRepsoitory.save(departments);
//        return new Result(true, "Successfully save rayon!");
//
//    }


}
