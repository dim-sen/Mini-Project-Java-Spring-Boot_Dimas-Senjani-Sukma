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
@Table(name = "JOB_LOCATION")
@SQLDelete(sql = "UPDATE JOB_LOCATION SET is_deleted = true WHERE id =?")
@Where(clause = "is_deleted = false")
public class JobLocationDao extends BaseDao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "location_name", nullable = false)
    private String locationName;

    @OneToMany(mappedBy = "location", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<JobInformationDao> jobInformationDaoList;
}
