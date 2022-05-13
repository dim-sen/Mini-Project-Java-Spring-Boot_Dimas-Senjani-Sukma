package com.dimsen.miniproject.repository;

import com.dimsen.miniproject.domain.dao.JobInformationDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobInformationRepository extends JpaRepository<JobInformationDao, Long> {

    List<JobInformationDao> findJobInformationDaoByJobTitleContaining(String jobTitle);

    @Query(value = "select ji from JobInformationDao ji where upper(ji.jobTitle) like upper(concat('%',:jobTitle,'%'))")
    List<JobInformationDao> findAllByJobTitle(@Param("jobTitle") String jobTitle);

    @Query(value = "select ji from JobInformationDao ji where upper(ji.category.categoryName) like upper(concat('%',:categoryName,'%'))")
    List<JobInformationDao> findAllByCategoryName(@Param("categoryName") String categoryName);

    List<JobInformationDao> findJobInformationDaoByLocationLocationNameContaining(String locationName);

    @Query(value = "select ji from JobInformationDao ji where upper(ji.location.locationName) like upper(concat('%',:locationName,'%'))")
    List<JobInformationDao> findAllByLocationName(@Param("locationName") String locationName);

}
