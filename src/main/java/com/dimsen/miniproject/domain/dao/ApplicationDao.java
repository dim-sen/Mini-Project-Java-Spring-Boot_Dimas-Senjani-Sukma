package com.dimsen.miniproject.domain.dao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "APPLICATION")
public class ApplicationDao {

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
}
