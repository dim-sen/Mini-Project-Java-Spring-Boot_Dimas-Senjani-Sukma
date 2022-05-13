package com.dimsen.miniproject.domain.dao;

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
@Table(name = "JOB_CATEGORY")
@SQLDelete(sql = "UPDATE JOB_CATEGORY SET is_deleted = true WHERE id =?")
@Where(clause = "is_deleted = false")
public class JobCategoryDao extends BaseDao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "category_name", nullable = false)
    private String categoryName;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<JobInformationDao> jobInformationDaoList;
}
