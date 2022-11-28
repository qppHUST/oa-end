package hust.cs.pojo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * ClassName: ChatMsg
 * PackageName:hust.cs.pojo
 * Description:
 * date: 2022/3/29 22:34
 *
 * @author: 邱攀攀
 * @version:
 * @since JDK 1.8
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ChatMsg {
    private String from;
    private String to;
    private String content;
    private LocalDateTime date;
    private String fromNickName;
}
