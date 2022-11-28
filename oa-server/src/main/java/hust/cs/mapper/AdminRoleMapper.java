package hust.cs.mapper;

import hust.cs.pojo.AdminRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author qpphust
 * @since 2022-11-23
 */
@Mapper
public interface AdminRoleMapper extends BaseMapper<AdminRole> {

    Integer addAdminRole(@Param(value = "adminId") Integer adminId, @Param(value = "rids") Integer[] rids);
}
