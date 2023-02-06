package com.mikejohn.bonusAccrual.dao.common;

import org.springframework.data.domain.Persistable;

import java.util.UUID;

public interface Persisted extends Persistable<UUID> {
    void setIds();
}
