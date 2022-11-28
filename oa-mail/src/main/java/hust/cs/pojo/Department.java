package hust.cs.pojo;

import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 
 * </p>
 *
 * @author qpphust
 * @since 2022-11-23
 */
@Data
@EqualsAndHashCode(callSuper = false,of = "name")
@RequiredArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class Department implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    @NonNull
    private String name;

    private Integer parentId;

    private String depPath;

    private Boolean enabled;

    private Boolean isParent;

    private List<Department> children;

    private Integer result;
}

