package org.smlions.comfortplant.repository;

import org.smlions.comfortplant.domain.entity.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long> {
    @Query("select o from Activity o " +
            "where o.user.email = :email ")
    List<Activity> findActivities(@Param("email") String email);// 현재 로그인한 사용자의 Activity들을 조회함
}
