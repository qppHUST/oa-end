package hust.cs.service.impl;

import hust.cs.pojo.Department;
import hust.cs.mapper.DepartmentMapper;
import hust.cs.pojo.RespBean;
import hust.cs.service.IDepartmentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author qpphust
 * @since 2022-11-23
 */
@Service
public class DepartmentServiceImpl extends ServiceImpl<DepartmentMapper, Department> implements IDepartmentService {

    @Autowired
    private DepartmentMapper departmentMapper;

    /***
     * @author Sunny
     * @description 获得所有部门列表
     * @createTime  2022/3/27 20:57
     * @param
     * @return java.util.List<hust.cs.server.pojo.Department>
     **/
    @Override
    public List<Department> getAllDepartment() {
        return departmentMapper.getAllDepartment(-1);
    }

    /***
     * @author Sunny
     * @description 添加部门
     * @createTime  2022/3/27 21:35
     * @param department
     * @return hust.cs.server.pojo.RespBean
     **/
    @Override
    public RespBean addDep(Department department) {
        department.setEnabled(true);
        departmentMapper.addDep(department);
        if(1==department.getResult()){
            return RespBean.success("添加成功",department);
        }
        return RespBean.error("添加失败");
    }

    /***
     * @author Sunny
     * @description 删除部门
     * @createTime  2022/3/27 21:58
     * @param id
     * @return hust.cs.server.pojo.RespBean
     **/
    @Override
    public RespBean deleteDep(Integer id) {
        Department department = new Department();
        department.setId(id);
        departmentMapper.deleteDep(department);
        if(department.getResult()==-2){
            return RespBean.error("该部门下有子部门,删除失败");
        }else if(department.getResult() == -1){
            return RespBean.error("该部门下有员工");
        }else {
            return RespBean.success("删除部门成功");
        }
    }
}
