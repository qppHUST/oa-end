package hust.cs.mapper;

import hust.cs.pojo.MenuRole;
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
public interface MenuRoleMapper extends BaseMapper<MenuRole> {
    Integer insertRecord(@Param("rid") Integer rid, @Param("mids") Integer[] mids);
}
