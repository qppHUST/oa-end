package hust.cs;

import com.rabbitmq.client.Channel;
import hust.cs.pojo.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Date;

import static hust.cs.pojo.MailConstants.MAIL_QUEUE_NAME;

/**
 * ClassName: MailReceiver
 * PackageName:hust.cs.mail
 * Description:
 * date: 2022/3/29 13:13
 *
 * @author: 邱攀攀
 * @version:
 * @since JDK 1.8
 */
@Component
public class MailReceiver {
    private static final Logger LOGGER= LoggerFactory.getLogger(MailReceiver.class);
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private MailProperties mailProperties;
    @Autowired
    private TemplateEngine templateEngine;
    @Autowired
    private RedisTemplate redisTemplate;

    //配置rabbitMq并且在监听相关队列，对队列进行枚举
    @RabbitListener(queues = MAIL_QUEUE_NAME)
    public void handler(Message message, Channel channel){
        //获得员工
        Employee employee = (Employee) message.getPayload();
        //获得消息序号tag
        MessageHeaders headers = message.getHeaders();
        long tag = (long) headers.get(AmqpHeaders.DELIVERY_TAG);
        //得到msg的uuid,这个id和其他几个表是没关系的，是自己生成的给msg的一个id
        String msgId = (String) headers.get("spring_returned_message_correlation");
        //通过redis的API得到对象
        HashOperations hashOperations = redisTemplate.opsForHash();

        try {
            if (hashOperations.entries("mail_log").containsKey(msgId)) {
                LOGGER.error("消息已经被消费===========>{}",msgId);
                /***
                 * 手动确认消息
                 * tag:消息序号
                 * multiple:是否确认多条
                 **/
                channel.basicAck(tag,false);
                return;
            }
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
            //发件人
            helper.setFrom(mailProperties.getUsername());
            //收件人
            helper.setTo(employee.getEmail());
            //设置主题
            helper.setSubject("入职欢迎邮件");
            //发送日期
            helper.setSentDate(new Date());
            //邮件内容
            Context context = new Context();
            context.setVariable("name",employee.getName());
            context.setVariable("posName",employee.getPosition().getName());
            context.setVariable("joblevelName",employee.getJoblevel().getName());
            context.setVariable("departmentName",employee.getDepartment().getName());
            String mail = templateEngine.process("mail", context);
            helper.setText(mail,true);
            //发送
            javaMailSender.send(mimeMessage);
            LOGGER.info("邮件发送成功");
            //将消息id存进去，这个id是从rabbitmq中得到的
            hashOperations.put("mail_log",msgId,"Ok");
            //手动确认
            channel.basicAck(tag,false);
        } catch (Exception e) {
            /***
             * tag:消息序号
             * multiple:是否确认多条
             * requeue:是否退回队列
             **/
            try {
                //手动确认消息，但是是有异常，即失败情况下
                channel.basicNack(tag,false,true);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            LOGGER.error("邮件发送失败============>{}",e.getMessage());
        }
    }
}
