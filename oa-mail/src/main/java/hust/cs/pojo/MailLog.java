package hust.cs.pojo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author qpphust
 * @since 2022-11-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class MailLog implements Serializable {

    private static final long serialVersionUID = 1L;

    private String msgId;

    private Integer eid;

    private Integer status;

    private String routeKey;

    private String exchange;

    private Integer count;

    private LocalDateTime tryTime;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;


}
