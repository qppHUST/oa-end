package hust.cs.service;

import hust.cs.pojo.Department;
import com.baomidou.mybatisplus.extension.service.IService;
import hust.cs.pojo.RespBean;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author qpphust
 * @since 2022-11-23
 */
public interface IDepartmentService extends IService<Department> {

    List<Department> getAllDepartment();

    RespBean addDep(Department department);

    RespBean deleteDep(Integer id);
}
