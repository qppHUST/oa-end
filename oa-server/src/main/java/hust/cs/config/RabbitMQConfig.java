package hust.cs.config;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import hust.cs.pojo.MailLog;
import hust.cs.service.IMailLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static hust.cs.pojo.MailConstants.*;

/**
 * ClassName: RabbitMQConfig
 * PackageName:hust.cs.server.config
 * Description:
 * date: 2022/3/30 19:08
 *
 * @author: 邱攀攀
 * @version:
 * @since JDK 1.8
 */
@Configuration
public class RabbitMQConfig {
    private static final Logger logger = LoggerFactory.getLogger(RabbitMQConfig.class);
    @Autowired
    private CachingConnectionFactory cachingConnectionFactory;
    @Autowired
    private IMailLogService mailLogService;
    @Bean
    public RabbitTemplate rabbitTemplate(){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(cachingConnectionFactory);
        rabbitTemplate.setConfirmCallback((data,ack,cause)->{
            //消息确认回调,确认是否到达broker
            /***
             * data:消息的唯一标识
             * ack:确认结果
             * cause:失败原因
             **/
            if(ack){
                logger.info("{}=========》消息发送成功",data.getId());//uuid
                mailLogService.update(new UpdateWrapper<MailLog>().set("status",1).eq("magId",data.getId()));//发送成功了，将库中的内容更新
            }else {
                logger.error("{}===============>消息发送失败 \n 失败原因:{}",data.getId(),cause);
            }
        });
        //消息失败回调，如果router不到queue
        rabbitTemplate.setReturnCallback((msg,repCode,repText,exchange,routingkey)->{
            /***
             * msg:消息主题
             * 响应码
             * 相应描述
             * 交换机
             * 路由键
             **/
            logger.error("{}===========>消息发送到queue时失败",msg.getBody());
        });
        return rabbitTemplate;
    }
    @Bean
    public Queue queue(){
        return new Queue(MAIL_QUEUE_NAME);
    }
    @Bean
    public DirectExchange directExchange(){
        return new DirectExchange(MAIL_EXCHANGE);
    }
    @Bean
    public Binding binding(){
        return BindingBuilder.bind(queue()).to(directExchange()).with(MAIL_ROUTING_KEY_NAME);
    }
}
