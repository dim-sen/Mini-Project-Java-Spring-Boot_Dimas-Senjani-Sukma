package com.dimsen.miniproject.domain.dao;

import com.dimsen.miniproject.domain.common.BaseDao;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "JOB_INFORMATION")
@SQLDelete(sql = "UPDATE JOB_INFORMATION SET is_deleted = true WHERE id =?")
@Where(clause = "is_deleted = false")
public class JobInformationDao extends BaseDao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "job_title", nullable = false)
    private String jobTitle;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "salary", nullable = false)
    private Long salary;

    @Column(name = "job_status", nullable = false)
    private String jobStatus;

    @Column(name = "posting_date", nullable = false)
    private LocalDateTime postingDate;

    @Column(name = "last_application_date", nullable = false)
    private LocalDateTime lastApplicationDate;

    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private JobCategoryDao category;

    @ManyToOne
    @JoinColumn(name = "location_id", referencedColumnName = "id")
    private JobLocationDao location;

    @OneToMany(mappedBy = "information", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ApplicationDao> applicationDaoList;

    @ManyToOne
    @JoinColumn(name = "campany_id", referencedColumnName = "id")
    private UserDao company;
}
