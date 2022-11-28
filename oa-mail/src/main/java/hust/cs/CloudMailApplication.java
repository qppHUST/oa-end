package hust.cs;

import org.springframework.amqp.core.Queue;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;

import static hust.cs.pojo.MailConstants.MAIL_QUEUE_NAME;

/**
 * ClassName: cloudMailApplication
 * PackageName:hust.cs.mail
 * Description:项目启动类
 * date: 2022/3/25 13:06
 *
 * @author: 邱攀攀
 * @version:
 * @since JDK 1.8
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class CloudMailApplication {

    public static void main(String[] args) {
        SpringApplication.run(CloudMailApplication.class,args);
    }

    @Bean
    public Queue queue(){
        return new Queue(MAIL_QUEUE_NAME);
    }

}
