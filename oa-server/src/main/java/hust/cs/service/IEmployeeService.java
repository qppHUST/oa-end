package hust.cs.service;

import hust.cs.pojo.Employee;
import com.baomidou.mybatisplus.extension.service.IService;
import hust.cs.pojo.RespBean;
import hust.cs.pojo.RespPageBean;

import java.time.LocalDate;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author qpphust
 * @since 2022-11-23
 */
public interface IEmployeeService extends IService<Employee> {

    RespPageBean getEmployeeByPage(Integer currntPage, Integer size, Employee employee, LocalDate[] beginDateScope);

    RespBean getMaxWorkID();

    RespBean addEmp(Employee employee);

    List<Employee> getEmployee(Integer id);

    RespPageBean getEmployeeWithSalary(Integer currentPage, Integer size);
}
