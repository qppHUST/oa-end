package hust.cs.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import hust.cs.pojo.Menu;
import hust.cs.pojo.MenuRole;
import hust.cs.pojo.RespBean;
import hust.cs.pojo.Role;
import hust.cs.service.IMenuRoleService;
import hust.cs.service.IMenuService;
import hust.cs.service.IRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * ClassName: PermissController
 * PackageName:hust.cs.server.controller
 * Description:权限组
 * date: 2022/3/27 17:00
 *
 * @author: 邱攀攀
 * @version:
 * @since JDK 1.8
 */
@RestController
@Api(tags = "PermissController")
@RequestMapping("/system/basic/permiss")
public class PermissController {
    @Autowired
    private IRoleService roleService;
    @Autowired
    private IMenuService menuService;
    @Autowired
    private IMenuRoleService menuRoleService;

    @ApiOperation(value = "获取所有角色")
    @GetMapping
    public List<Role> getAllRoles(){
        return roleService.list();
    }

    @ApiOperation(value = "添加角色")
    @PostMapping("/role")
    public RespBean addRole(@RequestBody Role role){
        if(!role.getName().startsWith("ROLE_")){
            role.setName("ROLE_"+role.getName());
        }
        if(roleService.save(role)){
            return RespBean.success("添加角色成功");
        }
        return RespBean.error("添加角色失败");
    }

    @ApiOperation(value = "删除角色")
    @DeleteMapping("/{rid}")
    public RespBean deleteRole(@PathVariable Integer rid){
        if (roleService.removeById(rid)) {
            return RespBean.success("删除角色成功");
        }
        return RespBean.error("删除角色失败");
    }

    @ApiOperation(value = "查询所有菜单")
    @GetMapping("/menus")
    public List<Menu> getAllMenus(){
        return menuService.getAllMenus();
    }

    @ApiOperation(value = "根据角色ID查询到该角色对应的菜单Id")
    @GetMapping("/mid/{rid}")
    public List<Integer> getByRid(@PathVariable Integer rid){
        return menuRoleService.list(new QueryWrapper<MenuRole>().eq("rid",rid))
                .stream().map(MenuRole::getMid).collect(Collectors.toList());
    }

    @ApiOperation(value = "改变权限更新角色菜单")
    @PutMapping
    public RespBean updateMenuRole(Integer rid,Integer[] mids){
        return menuRoleService.updateMenuRole(rid,mids);
    }

}
