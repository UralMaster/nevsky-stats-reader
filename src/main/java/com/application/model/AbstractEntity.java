package com.application.model;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.UUID;

/**
 * Abstract entity which provides unique identifier in format of {@link UUID}.
 *
 * @author Ilya Ryabukhin
 * @since 15.04.2022
 */
@MappedSuperclass
public abstract class AbstractEntity {

    @Id
    @GeneratedValue
    private UUID id;

    /**
     * Returns identifiers of entity
     *
     * @return identifiers of entity
     */
    @Nullable
    public UUID getId() {
        return id;
    }

    /**
     * Sets id for this entity
     *
     * @param id for setting
     */
    public void setId(@NonNull UUID id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        if (id != null) {
            return id.hashCode();
        }
        return super.hashCode();
    }

    @Override
    public boolean equals(@NonNull Object obj) {
        if (!(obj instanceof AbstractEntity)) {
            return false;
        }
        AbstractEntity other = (AbstractEntity) obj;

        if (id != null) {
            return id.equals(other.id);
        }
        return super.equals(other);
    }
}
