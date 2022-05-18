package com.dimsen.miniproject.domain.dao;

import com.dimsen.miniproject.constant.AppConstant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "USER")
public class UserDao {

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

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ProfileTypeDao> profileTypeDaoList;
}
