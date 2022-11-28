package hust.cs.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ClassName: RespBean
 * PackageName:hust.cs.server.pojo
 * Description:
 * date: 2022/3/26 11:31
 *
 * @author: 邱攀攀
 * @version:
 * @since JDK 1.8
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RespBean {
    private long code;
    private String message;
    private Object obj;

    /***
     * @author Sunny
     * @description 成功返回
     * @createTime  2022/3/26 11:33
     * @param message
     * @return hust.cs.server.pojo.RespBean
     **/
    public static RespBean success(String message){
        return new RespBean(200,message,null);
    }

    public static RespBean success(String message,Object obj){
        return new RespBean(200,message,obj);
    }

    /***
     * @author Sunny
     * @description 错误返回
     * @createTime  2022/3/26 11:36
     * @param message
     * @return hust.cs.server.pojo.RespBean
     **/
    public static RespBean error(String message){
        return new RespBean(500,message,null);
    }
    public static RespBean error(String message,Object obj){
        return new RespBean(500,message,null);
    }
}
