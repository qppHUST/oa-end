package hust.cs.mapper;

import hust.cs.pojo.Admin;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import hust.cs.pojo.Menu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author qpphust
 * @since 2022-11-23
 */
@Mapper
public interface AdminMapper extends BaseMapper<Admin> {
    List<Admin> getAllAdmins(@Param(value = "id") Integer id, @Param(value = "keywords") String keywords);
}
