package hust.cs.service;

import hust.cs.pojo.Menu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author qpphust
 * @since 2022-11-23
 */
public interface IMenuService extends IService<Menu> {

    List<Menu> getMenusWithRole();

    List<Menu> getMenusByAdminId();

    List<Menu> getAllMenus();
}
