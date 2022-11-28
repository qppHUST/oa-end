package hust.cs.controller;

import hust.cs.pojo.Admin;
import hust.cs.service.IAdminService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * ClassName: ChatController
 * PackageName:hust.cs.server.controller
 * Description: 在线聊天
 * date: 2022/3/29 22:42
 *
 * @author: 邱攀攀
 * @version:
 * @since JDK 1.8
 */
@RestController
@RequestMapping("/chat")
public class ChatController {
    @Autowired
    private IAdminService adminService;

    @ApiOperation(value = "获取所有操作员")
    @GetMapping("/admin")
    public List<Admin> getAllAdmins(String keywords){
        return adminService.getAllAdmins(keywords);
    }
}
