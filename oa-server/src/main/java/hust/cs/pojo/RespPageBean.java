package hust.cs.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * ClassName: RespPageBean
 * PackageName:hust.cs.server.pojo
 * Description:
 * date: 2022/3/28 11:25
 *
 * @author: 邱攀攀
 * @version:
 * @since JDK 1.8
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RespPageBean {
    /***
     * 总条数
     **/
    private Long total;
    /***
     * 数据list
     **/
    private List<?> data;
}
