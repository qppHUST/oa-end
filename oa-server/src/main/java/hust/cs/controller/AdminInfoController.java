package hust.cs.controller;

import hust.cs.pojo.Admin;
import hust.cs.pojo.RespBean;
import hust.cs.service.IAdminService;
import hust.cs.utils.FastDFSUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * ClassName: AdminInfoController
 * PackageName:hust.cs.server.controller
 * Description: 个人中心
 * date: 2022/3/29 23:14
 *
 * @author: 邱攀攀
 * @version:
 * @since JDK 1.8
 */
@RestController
public class AdminInfoController {
    @Autowired
    private IAdminService adminService;

    @ApiOperation(value = "更新当前用户信息")
    @PutMapping("/admin/info")
    public RespBean updateAdmin(@RequestBody Admin admin, Authentication authentication){
        if(adminService.updateById(admin)){
            //在security中设置对象
            SecurityContextHolder.getContext().setAuthentication
                    (new UsernamePasswordAuthenticationToken(admin,null,authentication.getAuthorities()));
            return RespBean.success("更新当前用户信息成功");
        }
        return RespBean.error("更新当前用户信息失败");
    }

    @ApiOperation(value = "更新用户密码")
    @PutMapping("/admin/pass")
    public RespBean updateAdminPassword(@RequestBody Map<String,Object> info){
        //通过这个map得到要改谁的密码，新密码旧密码是啥
        String oldPass = (String) info.get("oldPass");
        String pass = (String) info.get("pass");
        Integer adminId = (Integer) info.get("adminId");
        return adminService.updateAdminPassword(oldPass,pass,adminId);
    }

    @ApiOperation(value = "更新用户头像")
    @PostMapping("/admin/userface")
    public RespBean updateAdminUserFace(MultipartFile file,Integer id,Authentication authentication){
        String[] file_path = FastDFSUtils.upload(file);
        String url = FastDFSUtils.getTrackerUrl() + file_path[0] + "/" + file_path[1];
        return adminService.updateAdminUserFace(url,id,authentication);
    }
}
