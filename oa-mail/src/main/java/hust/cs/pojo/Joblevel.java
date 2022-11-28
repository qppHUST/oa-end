package hust.cs.pojo;

import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

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
public class Joblevel implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    @NonNull
    private String name;

    private String titleLevel;

    private LocalDateTime createDate;

    private Boolean enabled;


}
