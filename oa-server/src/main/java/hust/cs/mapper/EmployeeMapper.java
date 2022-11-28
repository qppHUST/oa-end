package hust.cs.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import hust.cs.pojo.Employee;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author qpphust
 * @since 2022-11-23
 */
@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {
    IPage<Employee> getEmployeeByPage(Page<Employee> page
            , @Param("employee") Employee employee
            , @Param("beginDateScope") LocalDate[] beginDateScope);

    List<Employee> getEmployee(Integer id);

    IPage<Employee> getEmployeeWithSalary(Page<Employee> page);
}
