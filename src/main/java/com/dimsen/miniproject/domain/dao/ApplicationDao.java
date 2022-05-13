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

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "APPLICATION")
@SQLDelete(sql = "UPDATE APPLICATION SET is_deleted = true WHERE id =?")
@Where(clause = "is_deleted = false")
public class ApplicationDao extends BaseDao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "application_status", nullable = false)
    private String applicationStatus;

    @Column(name = "date_submitted", nullable = false)
    private LocalDateTime dateSubmitted;

    @ManyToOne
    @JoinColumn(name = "information_id", referencedColumnName = "id")
    private JobInformationDao information;

    @ManyToOne
    @JoinColumn(name = "applicant_id", referencedColumnName = "id")
    private UserDao applicant;
}
