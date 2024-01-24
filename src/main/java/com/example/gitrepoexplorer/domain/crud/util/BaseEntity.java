package com.example.gitrepoexplorer.domain.crud.util;

import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Version;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;
@MappedSuperclass
public abstract class BaseEntity implements Serializable {

    private UUID uuid = UUID.randomUUID();
    @CreationTimestamp
    private Instant createdOn;

    @Version
    private Long version;
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BaseEntity that = (BaseEntity) o;

        return Objects.equals(uuid, that.uuid);
    }

    @Override
    public int hashCode() {
        return uuid != null ? uuid.hashCode() : 0;
    }
}
