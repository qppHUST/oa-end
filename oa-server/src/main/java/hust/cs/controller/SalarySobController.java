package hust.cs.controller;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import hust.cs.pojo.Employee;
import hust.cs.pojo.RespBean;
import hust.cs.pojo.RespPageBean;
import hust.cs.pojo.Salary;
import hust.cs.service.IEmployeeService;
import hust.cs.service.ISalaryService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * ClassName: SalarySobController
 * PackageName:hust.cs.server.controller
 * Description:
 * date: 2022/3/29 21:03
 *
 * @author: 邱攀攀
 * @version:
 * @since JDK 1.8
 */
@RestController
@RequestMapping("/salary/sobcfg")
public class SalarySobController {

    @Autowired
    private ISalaryService salaryService;
    @Autowired
    private IEmployeeService employeeService;

    @ApiOperation(value = "获取所有的工资账套")
    @GetMapping("/salaries")
    public List<Salary> getAllSalaries(){
        return salaryService.list();
    }

    @ApiOperation(value = "获取所有员工账套")
    @GetMapping
    public RespPageBean getEmployeeSalary(@RequestParam(value = "currentPage",defaultValue = "1") Integer currentPage
            , @RequestParam(value = "size",defaultValue = "10") Integer size){
        return employeeService.getEmployeeWithSalary(currentPage,size);
    }

    @ApiOperation(value = "更新员工账套")
    @PutMapping
    public RespBean updateEmployeeWithSalary(Integer eid, Integer sid){
        if(employeeService.update(new UpdateWrapper<Employee>().set("salaryId",sid).eq("id",eid))){
            return RespBean.success("更新员工账套成功");
        }
        return RespBean.error("更新员工账套失败");
    }

}
