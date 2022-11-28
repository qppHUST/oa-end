package hust.cs.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import hust.cs.config.CustomAuthorityDeserializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * <p>
 * 
 * </p>
 *
 * @author qpphust
 * @since 2022-11-23
 */
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_admin")
@ApiModel(value="Admin对象", description="")
public class Admin implements Serializable , UserDetails {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.AUTO)
    @Getter
    @Setter
    private Integer id;

    @ApiModelProperty(value = "姓名")
    @Getter
    @Setter
    private String name;

    @ApiModelProperty(value = "手机号码")
    @Getter
    @Setter
    private String phone;

    @ApiModelProperty(value = "住宅电话")
    @Getter
    @Setter
    private String telephone;

    @ApiModelProperty(value = "联系地址")
    @Getter
    @Setter
    private String address;

    @ApiModelProperty(value = "是否启用")
    @Setter
    private Boolean enabled;

    @ApiModelProperty(value = "用户名")
    @Getter
    @Setter
    private String username;

    @ApiModelProperty(value = "密码")
    @Getter
    @Setter
    private String password;

    @ApiModelProperty(value = "用户头像")
    @Getter
    @Setter
    private String userFace;

    @ApiModelProperty(value = "备注")
    @Getter
    @Setter
    private String remark;

    @ApiModelProperty(value = "角色")
    @TableField(exist = false)
    @Getter
    @Setter
    private List<Role> roles;

    @Override
    @JsonDeserialize(using = CustomAuthorityDeserializer.class)
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = roles
                .stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }
}
