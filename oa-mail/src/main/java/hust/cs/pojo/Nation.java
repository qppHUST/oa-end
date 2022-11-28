package hust.cs.pojo;

import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;

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
@Accessors(chain = true)
@RequiredArgsConstructor
@NoArgsConstructor
public class Nation implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    @NonNull
    private String name;


}
