package hust.cs.utils;

import hust.cs.pojo.Admin;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * ClassName: AdminUtils
 * PackageName:hust.cs.server.utils
 * Description:
 * date: 2022/3/28 10:00
 *
 * @author: 邱攀攀
 * @version:
 * @since JDK 1.8
 */
public class AdminUtils {
    public static Admin getCurrentAdmin(){
        return ((Admin) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    }
}
