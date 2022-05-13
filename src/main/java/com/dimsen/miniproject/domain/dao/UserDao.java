package com.dimsen.miniproject.domain.dao;

import com.dimsen.miniproject.constant.AppConstant;
import com.dimsen.miniproject.domain.common.BaseDao;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "USER")
@SQLDelete(sql = "UPDATE USER SET is_deleted = true WHERE id =?")
@Where(clause = "is_deleted = false")
public class UserDao extends BaseDao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private AppConstant.UserRole role;

    @Column(name = "account_status", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private AppConstant.AccountStatus accountStatus;

//    @OneToOne(cascade = CascadeType.ALL)
    @OneToOne(mappedBy = "user")
    private ProfileTypeDao profileType;

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<JobInformationDao> companies;

    @OneToMany(mappedBy = "applicant", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ApplicationDao> applicants;

}
