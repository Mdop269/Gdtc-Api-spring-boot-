package com.GdtcApi.GdtcApi.Repsoitory;

import com.GdtcApi.GdtcApi.Entities.EmpAccountDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmpAccountDetailRepo extends JpaRepository<EmpAccountDetail, Integer> {

}
