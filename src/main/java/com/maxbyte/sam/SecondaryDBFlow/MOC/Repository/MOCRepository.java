package com.maxbyte.sam.SecondaryDBFlow.MOC.Repository;

import com.maxbyte.sam.SecondaryDBFlow.AIM.Entity.Aim;
import com.maxbyte.sam.SecondaryDBFlow.MOC.Entity.MOC;
import com.maxbyte.sam.SecondaryDBFlow.SA.Entity.SA;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MOCRepository extends JpaRepository<MOC, Integer>, JpaSpecificationExecutor<MOC> {
    List<MOC> findByMocNumber(String mocNumber);
    List<MOC> findByCreatedOnBetween(LocalDateTime startTime, LocalDateTime endTime);
    List<MOC> findByOrganizationCodeAndCreatedOnBetween(String orgCode, LocalDateTime from, LocalDateTime to);
    Page<MOC> findByOrganizationCodeAndCreatedOnBetween(String orgCode, LocalDateTime from, LocalDateTime to, Pageable pageable);
    List<MOC> findByOrganizationCode(String organizationCode);
    //List<RCAStepOneTeams> findByStepOneId(Integer id);

    @Query("SELECT r FROM MOC r WHERE r.status = 0")
    List<MOC> findOpenMOCs();

    @Query("SELECT r FROM MOC r WHERE r.status BETWEEN 1 AND 6")
    List<MOC> findInProgressMOCs();

    @Query("SELECT r FROM MOC r WHERE r.status BETWEEN 7 AND 9")
    List<MOC> findCompleteMOCs();
    @Query("SELECT r FROM MOC r WHERE r.status = 10")
    List<MOC> findRevertBackMOCs();
    @Query("SELECT r FROM MOC r WHERE r.status = 11")
    List<MOC> findClosedMOCs();
    List<MOC> findByDepartmentAndCreatedOnBetween(String department, LocalDateTime startTime, LocalDateTime endTime);
    List<MOC> findByAreaAndCreatedOnBetween(String area, LocalDateTime startTime, LocalDateTime endTime);

    Page<MOC> findByCreatedOnBetween(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);
    Page<MOC> findByAreaAndCreatedOnBetween(String area, LocalDateTime startTime, LocalDateTime endTime, Pageable pageable);
    Page<MOC> findByDepartmentAndCreatedOnBetween(String department, LocalDateTime localDateTime, LocalDateTime localDateTime1, Pageable pageable);

    @Query("SELECT DISTINCT m.area FROM MOC m")
    List<String> findByArea();

    Page<MOC> findByOrganizationCodeAndDepartmentAndAreaAndCreatedOnBetween(String organizationCode, String department, String area, LocalDateTime startDateTime, LocalDateTime endDateTime, PageRequest pageRequest);

    Page<MOC> findByOrganizationCodeAndDepartmentAndCreatedOnBetween(String organizationCode, String department, LocalDateTime startDateTime, LocalDateTime endDateTime, PageRequest pageRequest);

    Page<MOC> findByOrganizationCodeAndAreaAndCreatedOnBetween(String organizationCode, String area, LocalDateTime startDateTime, LocalDateTime endDateTime, PageRequest pageRequest);

    Page<MOC> findByDepartmentAndAreaAndCreatedOnBetween(String department, String area, LocalDateTime startDateTime, LocalDateTime endDateTime, PageRequest pageRequest);

    @Query("SELECT m FROM MOC m WHERE m.status BETWEEN 0 AND 6 AND m.initiatorName = :initiatorName AND m.organizationCode = :organizationCode")
    List<MOC> findPending(String initiatorName, String organizationCode);

    //          DashBoard

    @Query("SELECT m FROM MOC m WHERE m.mocNumber IN :mocNumbers AND m.status In (7, 9) " +
            "AND m.organizationCode = :organizationCode")
    List<MOC> findByMocNumberInStatusBetweenAndOrganizationCode(List<String> mocNumbers, String organizationCode);

    MOC findAllByMocNumber(String mocNumber);

    @Query("SELECT m FROM MOC m " +
            "LEFT JOIN MOCStepSixAL ms ON m.mocNumber = ms.mocNumber " +
            "LEFT JOIN MOCStepSeven mseven ON m.mocNumber = mseven.mocNumber " +
            "LEFT JOIN MOCStepEight meight ON m.mocNumber = meight.mocNumber " +
            "LEFT JOIN MOCStepNine mnine ON m.mocNumber = mnine.mocNumber " +
            "WHERE m.status = 0 AND m.organizationCode = :organizationCode " +
            "AND (m.initiatorName = :userName OR ms.approverName = :userName OR " +
            " mseven.plantHead = :userName OR mseven.unitHead = :userName OR" +
            " meight.operationalHead = :userName OR " +
            " mnine.plantHead = :userName OR mnine.unitHead = :userName)")
    List<MOC> findOpenMOCs(String userName, String organizationCode);


    @Query("SELECT m FROM MOC m " +
            "LEFT JOIN MOCStepSixAL ms ON m.mocNumber = ms.mocNumber " +
            "LEFT JOIN MOCStepSeven mseven ON m.mocNumber = mseven.mocNumber " +
            "LEFT JOIN MOCStepEight meight ON m.mocNumber = meight.mocNumber " +
            "LEFT JOIN MOCStepNine mnine ON m.mocNumber = mnine.mocNumber " +
            "WHERE m.status BETWEEN 1 AND 6 AND m.organizationCode = :organizationCode " +
            "AND (m.initiatorName = :userName OR ms.approverName = :userName OR " +
            " mseven.plantHead = :userName OR mseven.unitHead = :userName OR" +
            " meight.operationalHead = :userName OR " +
            " mnine.plantHead = :userName OR mnine.unitHead = :userName)")
    List<MOC> findInProgressMOCs(String userName, String organizationCode);

    @Query("SELECT m FROM MOC m " +
            "LEFT JOIN MOCStepSixAL ms ON m.mocNumber = ms.mocNumber " +
            "LEFT JOIN MOCStepSeven mseven ON m.mocNumber = mseven.mocNumber " +
            "LEFT JOIN MOCStepEight meight ON m.mocNumber = meight.mocNumber " +
            "LEFT JOIN MOCStepNine mnine ON m.mocNumber = mnine.mocNumber " +
            "WHERE m.status BETWEEN 7 AND 9 AND m.organizationCode = :organizationCode " +
            "AND (m.initiatorName = :userName OR ms.approverName = :userName OR " +
            " mseven.plantHead = :userName OR mseven.unitHead = :userName OR" +
            " meight.operationalHead = :userName OR " +
            " mnine.plantHead = :userName OR mnine.unitHead = :userName)")
    List<MOC> findCompleteMOCs(String userName, String organizationCode);

    @Query("SELECT m FROM MOC m " +
            "LEFT JOIN MOCStepSixAL ms ON m.mocNumber = ms.mocNumber " +
            "LEFT JOIN MOCStepSeven mseven ON m.mocNumber = mseven.mocNumber " +
            "LEFT JOIN MOCStepEight meight ON m.mocNumber = meight.mocNumber " +
            "LEFT JOIN MOCStepNine mnine ON m.mocNumber = mnine.mocNumber " +
            "WHERE m.status = 10 AND m.organizationCode = :organizationCode " +
            "AND (m.initiatorName = :userName OR ms.approverName = :userName OR " +
            " mseven.plantHead = :userName OR mseven.unitHead = :userName OR" +
            " meight.operationalHead = :userName OR " +
            " mnine.plantHead = :userName OR mnine.unitHead = :userName)")
    List<MOC> findRevertBackMOCs(String userName, String organizationCode);

    @Query("SELECT m FROM MOC m " +
            "LEFT JOIN MOCStepSixAL ms ON m.mocNumber = ms.mocNumber " +
            "LEFT JOIN MOCStepSeven mseven ON m.mocNumber = mseven.mocNumber " +
            "LEFT JOIN MOCStepEight meight ON m.mocNumber = meight.mocNumber " +
            "LEFT JOIN MOCStepNine mnine ON m.mocNumber = mnine.mocNumber " +
            "WHERE m.status = 11 AND m.organizationCode = :organizationCode " +
            "AND (m.initiatorName = :userName OR ms.approverName = :userName OR " +
            " mseven.plantHead = :userName OR mseven.unitHead = :userName OR" +
            " meight.operationalHead = :userName OR " +
            " mnine.plantHead = :userName OR mnine.unitHead = :userName)")
    List<MOC> findClosedMOCs(String userName, String organizationCode);

}
