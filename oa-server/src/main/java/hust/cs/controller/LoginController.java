package hust.cs.controller;

import hust.cs.pojo.Admin;
import hust.cs.pojo.AdminLogin;
import hust.cs.pojo.RespBean;
import hust.cs.service.IAdminService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.time.LocalDateTime;

/**
 * ClassName: LoginController
 * PackageName:hust.cs.server.controller
 * Description:登陆用
 * date: 2022/3/26 11:50
 *
 * @author: 邱攀攀
 * @version:
 * @since JDK 1.8
 */
@RestController
@Api(tags = "LoginController")
@Slf4j
public class LoginController {

    @Autowired
    private IAdminService adminService;

    @ApiOperation(value = "登录之后返回token")
    @PostMapping("/login")
    public RespBean login(@RequestBody AdminLogin login, HttpServletRequest request){
        return adminService.login(login.getUsername(),login.getPassword(),login.getCode(),request);
    }

    @ApiOperation(value = "获取当前用户的信息")
    @GetMapping("/admin/info")
    public Admin getAdminInfo(Principal principal){
        if(null==principal){
            return null;
        }
        String username = principal.getName();
        Admin admin = adminService.getAdminByUserName(username);
        admin.setPassword(null);
        //登录之后设置该用户的权限列表，就是是什么角色
        admin.setRoles(adminService.getRoles(admin.getId()));
        return admin;
    }

    @ApiOperation(value = "退出登录")
    @PostMapping("/logout")
    public RespBean logout(HttpServletRequest request){
        log.info(SecurityContextHolder.getContext().getAuthentication().getName()+"在"+ LocalDateTime.now()+"下线了");
        return RespBean.success("注销成功");
    }
}
