package hust.cs.pojo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * <p>
 * 
 * </p>
 *
 * @author qpphust
 * @since 2022-11-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Employee implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    private String name;

    private String gender;

    private LocalDate birthday;

    private String idCard;

    private String  wedlock;

    private Integer nationId;

    private String nativePlace;

    private Integer politicId;

    private String email;

    private String phone;

    private String address;

    private Integer departmentId;

    private Integer jobLevelId;

    private Integer posId;

    private String engageForm;

    private String tiptopDegree;

    private String specialty;

    private String school;

    private LocalDate beginDate;

    private String workState;

    private String workID;

    private Double contractTerm;

    private LocalDate conversionTime;

    private LocalDate notWorkDate;

    private LocalDate beginContract;

    private LocalDate endContract;

    private Integer workAge;

    private Integer salaryId;

    private Nation nation;

    private Department department;

    private Joblevel joblevel;

    private Position position;


}

