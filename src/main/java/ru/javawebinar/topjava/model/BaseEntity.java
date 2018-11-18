package ru.javawebinar.topjava.model;

import java.util.Objects;

/*
 * primary kay
 */
public class BaseEntity {
    public static final int START_SEQ = 100000;

    protected Integer id;

    public BaseEntity() {

    }

    protected BaseEntity(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public boolean isNew() {
        return id == null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BaseEntity that = (BaseEntity) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return (id == null) ? 0 : id;
    }
}
