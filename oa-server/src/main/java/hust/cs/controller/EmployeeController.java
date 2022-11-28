package hust.cs.controller;


import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import hust.cs.pojo.*;
import hust.cs.service.*;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author qpphust
 * @since 2022-11-23
 */
@RestController
@RequestMapping("/employee/basic")
public class EmployeeController {

    @Autowired
    private IEmployeeService employeeService;
    @Autowired
    private IPoliticsStatusService politicsStatusService;
    @Autowired
    private IJoblevelService joblevelService;
    @Autowired
    private INationService nationService;
    @Autowired
    private IPositionService positionService;
    @Autowired
    private IDepartmentService departmentService;

    @ApiOperation(value = "查询所有员工并分页")
    @GetMapping
    public RespPageBean getEmployee(@RequestParam(defaultValue = "1")Integer currentPage
            , @RequestParam(defaultValue = "10")Integer size, Employee employee
            , LocalDate[] beginDateScope){
        return employeeService.getEmployeeByPage(currentPage,size,employee,beginDateScope);
    }

    @ApiOperation(value = "获取所有的政治面貌")
    @GetMapping("/politics")
    public List<PoliticsStatus> getAllPoliticsStatus(){
        return politicsStatusService.list();
    }

    @ApiOperation(value = "获取所有的职称")
    @GetMapping("/joblevels")
    public List<Joblevel> getAllJoblevels(){
        return joblevelService.list();
    }

    @ApiOperation(value = "获得所有的民族")
    @GetMapping("/nations")
    public List<Nation>getAllNations(){
        return nationService.list();
    }

    @ApiOperation(value = "获取所有职位")
    @GetMapping("/positions")
    public List<Position> getAllpositions(){
        return positionService.list();
    }

    @ApiOperation(value = "获取所有的部门")
    @GetMapping("/deps")
    public List<Department> getAlldeps(){
        return departmentService.getAllDepartment();
    }

    @ApiOperation(value = "获取工号")
    @GetMapping("/maxWorkID")
    public RespBean getMaxWorkID(){
        return employeeService.getMaxWorkID();
    }

    @ApiOperation(value = "添加员工")
    @PostMapping
    public RespBean addEmp(@RequestBody Employee employee){
        return employeeService.addEmp(employee);
    }

    @ApiOperation(value = "更新员工")
    @PutMapping
    public RespBean updateEmp(@RequestBody Employee employee){
        if(employeeService.updateById(employee)){
            return RespBean.success("更新成功");
        }
        return RespBean.error("更新失败");
    }

    @ApiOperation(value = "删除员工")
    @DeleteMapping("/{id}")
    public RespBean deleteEmp(@PathVariable Integer id){
        if(employeeService.removeById(id)){
            return RespBean.success("删除员工成功");
        }
        return RespBean.error("删除员工失败");
    }

    @ApiOperation(value = "导出员工数据")
    @GetMapping(value = "/export",produces = "application/octet-stream")
    public void export(HttpServletResponse response){
        List<Employee> employee = employeeService.getEmployee(null);
        ExportParams params = new ExportParams("员工表","员工表", ExcelType.HSSF);
        Workbook sheets = ExcelExportUtil.exportExcel(params, Employee.class, employee);
        ServletOutputStream outputStream = null;
        try {
            //以流的形式传出
            response.setHeader("content-type","application/octet-stream");
            //防止中文乱码
            response.setHeader("content-disposition","attachment;filename="
                    + URLEncoder.encode("员工表.xls","UTF-8"));
            outputStream = response.getOutputStream();
            sheets.write(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(null != outputStream){
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @ApiOperation(value = "导入员工数据")
    @PostMapping("/import")
    public RespBean importEmployee(MultipartFile file){
        ImportParams params = new ImportParams();
        //去掉标题行
        params.setTitleRows(1);
        List<Nation> list = nationService.list();
        List<PoliticsStatus> politicsStatusList = politicsStatusService.list();
        List<Department> departments = departmentService.list();
        List<Joblevel> joblevels = joblevelService.list();
        List<Position> positions = positionService.list();
        try {
            List<Employee> employees = ExcelImportUtil.importExcel(file.getInputStream(), Employee.class, params);
            for (Employee employee : employees) {
                //设置国家id
                Integer id = list.get(list.indexOf(
                        new Nation(employee.getNation().getName()))).getId();
                employee.setNationId(id);
                //设置政治面貌id
                Integer id1 = politicsStatusList.get(politicsStatusList.indexOf(
                        new PoliticsStatus(employee.getPoliticsStatus().getName()))).getId();
                employee.setPoliticId(id1);
                //设置部门id
                Integer id2 = departments.get(departments.indexOf(
                        new Department(employee.getDepartment().getName()))).getId();
                employee.setDepartmentId(id2);
                //设置职称id
                Integer id3 = joblevels.get(joblevels.indexOf(
                        new Joblevel(employee.getJoblevel().getName()))).getId();
                employee.setJobLevelId(id3);
                //设置职位id
                Integer id4 = positions.get(positions.indexOf(
                        new Position(employee.getPosition().getName()))).getId();
                employee.setPoliticId(id4);
            }
            if(employeeService.saveBatch(employees)){
                return RespBean.success("导入员工成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return RespBean.error("导入员工失败");
    }
}
