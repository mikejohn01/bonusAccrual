package com.mikejohn.bonusAccrual.dao.repository;

import com.mikejohn.bonusAccrual.dao.entity.BonusAccrualCount;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface BonusAccrualCountRepository extends CrudRepository<BonusAccrualCount, UUID> {

    @Query("select * " +
            "from bonus_accrual.bonus_accrual_count c " +
            "order by c.timestamp desc " +
            "fetch first 1 row only")
    Optional<BonusAccrualCount> findLastPayment();

    Optional<BonusAccrualCount> findById(UUID id);

}
