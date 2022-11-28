package hust.cs.service;

import hust.cs.pojo.MenuRole;
import com.baomidou.mybatisplus.extension.service.IService;
import hust.cs.pojo.RespBean;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author qpphust
 * @since 2022-11-23
 */
public interface IMenuRoleService extends IService<MenuRole> {

    RespBean updateMenuRole(Integer rid, Integer[] mids);
}
