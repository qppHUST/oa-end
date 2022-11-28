package hust.cs;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * ClassName: cloudHelpApplication
 * PackageName:hust.cs.server
 * Description:项目启动类
 * date: 2022/3/25 13:06
 *
 * @author: 邱攀攀
 * @version:
 * @since JDK 1.8
 */
@SpringBootApplication
@MapperScan("hust.cs.mapper")
@Slf4j
//@EnableScheduling
public class OAApplication {
    public static void main(String[] args) {
        SpringApplication.run(OAApplication.class,args);
        log.debug("111");
    }
}
