package com.dimsen.miniproject.domain.dao;

import com.dimsen.miniproject.domain.common.BaseDao;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "PROFILE_TYPE")
@SQLDelete(sql = "UPDATE PROFILE_TYPE SET is_deleted = true WHERE user_id =?")
@Where(clause = "is_deleted = false")
public class ProfileTypeDao extends BaseDao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "gender")
    private String gender;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "contact", nullable = false)
    private String contact;

    @Column(name = "website")
    private String website;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "education")
    private String education;

    @Column(name = "professional_summary")
    private String professionalSummary;

    @Column(name = "profile_image", nullable = false)
    private String profileImage;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserDao user;

}
