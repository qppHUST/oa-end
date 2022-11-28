package hust.cs.exception;

import hust.cs.pojo.RespBean;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

/**
 * ClassName: GlobalException
 * PackageName:hust.cs.server.exception
 * Description:
 * date: 2022/3/27 15:02
 *
 * @author: 邱攀攀
 * @version:
 * @since JDK 1.8
 */
@RestControllerAdvice()
public class GlobalException {

    @ExceptionHandler(SQLException.class)
    public RespBean mySqlException(SQLException e){
        if(e instanceof SQLIntegrityConstraintViolationException){
            return RespBean.error("该数据有外关联数据，操作失败");
        }
        e.printStackTrace();
        return RespBean.error("数据库异常，操作失败!");
    }

    @ExceptionHandler(AuthenticationException.class)
    public RespBean mySqlException(Exception e){
        if(e instanceof UsernameNotFoundException){
            return RespBean.error("用户名或密码不正确,------handler");
        }
        return RespBean.error("权限认证出错");
    }

}
