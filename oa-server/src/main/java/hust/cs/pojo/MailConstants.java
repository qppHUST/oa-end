package hust.cs.pojo;

/**
 * ClassName: MailConstants
 * PackageName:hust.cs.server.pojo
 * Description: 一个关于邮件的常量
 * date: 2022/3/29 16:38
 *
 * @author: 邱攀攀
 * @version:
 * @since JDK 1.8
 */
public class MailConstants {
    //消息投递中
    public static final Integer DELIVERING = 0;
    //消息投递成功
    public static final Integer SUCCESS = 1;
    //消息投递失败
    public static final Integer FAILURE = 2;
    //最大尝试次数
    public static final Integer MAX_TRY_COUNT = 3;
    //消息超时时间
    public static final Integer MSG_TIMEOUT = 1;
    //队列名称
    public static final String MAIL_QUEUE_NAME = "oamail.queue";
    //交换机
    public static final String MAIL_EXCHANGE = "oamail.exchange";
    //路由键
    public static final String MAIL_ROUTING_KEY_NAME = "oamail.routing.key";
}
