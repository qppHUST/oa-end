package hust.cs.task;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import hust.cs.pojo.Employee;
import hust.cs.pojo.MailLog;
import hust.cs.service.IEmployeeService;
import hust.cs.service.IMailLogService;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

import static hust.cs.pojo.MailConstants.*;

/**
 * ClassName: MailTask
 * PackageName:hust.cs.server.task
 * Description:邮件发送定时服务
 * date: 2022/3/29 17:37
 *
 * @author: 邱攀攀
 * @version:
 * @since JDK 1.8
 */
@Component
public class MailTask {

    @Autowired
    private IMailLogService mailLogService;
    @Autowired
    private IEmployeeService employeeService;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    //十秒发一次，这个发是向管道发
    @Scheduled(cron = "0/10 * * * * ?")
    public void mailTask(){
        //获得当前未发送的邮件，成功和失败的都不管
        List<MailLog> list = mailLogService.list(new QueryWrapper<MailLog>()
                .eq("status", 0).lt("tryTime", LocalDateTime.now()));
        list.forEach(mailLog -> {
            //如果重试次数大于等于三次，就直接设置失败
            if(3<=mailLog.getCount()){
                mailLogService.update(new UpdateWrapper<MailLog>()
                        .set("status",2).eq("msgId",mailLog.getMsgId()));
            }else {
                //还可以重试，我们先更新必要的属性，如重试次数，修改日期，重试时间
                mailLogService.update(new UpdateWrapper<MailLog>()
                        .set("count", mailLog.getCount() + 1)
                        .set("updateTime", LocalDateTime.now())
                        .set("tryTime", LocalDateTime.now().plusMinutes(MSG_TIMEOUT))
                        .eq("msgId", mailLog.getMsgId()));
                //接下来就发送东西
                Employee employee = employeeService.getEmployee(mailLog.getEid()).get(0);
                //将信息送到管道
                rabbitTemplate.convertAndSend(MAIL_EXCHANGE, MAIL_ROUTING_KEY_NAME
                        , employee, new CorrelationData(mailLog.getMsgId()));
            }
        });
    }
}
