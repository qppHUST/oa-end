package hust.cs.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
/**
 * ClassName: HelloController
 * PackageName:hust.cs.server.controller
 * Description:测试用接口
 * date: 2022/3/26 17:05
 *
 * @author: 邱攀攀
 * @version:
 * @since JDK 1.8
 */
@RestController
public class HelloController {
    @GetMapping("/hello")
    public String hello(){
        return "Hello";
    }

    @GetMapping("/employee/basic/hello")
    public String hello2(){
        return "/employee/basic/hello";
    }

    @GetMapping("/employee/advanced/hello")
    public String hello3(){
        return "/employee/advanced/hello";
    }
}
