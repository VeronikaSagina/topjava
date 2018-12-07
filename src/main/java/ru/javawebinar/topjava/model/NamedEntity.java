package ru.javawebinar.topjava.model;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@MappedSuperclass
@Access(AccessType.FIELD)
public class NamedEntity extends BaseEntity {

    @Column(name = "name")
    @NotBlank
    protected String name;

    NamedEntity() {
    }

     NamedEntity(Integer id, String name) {
        super(id);
        this.name = name;
    }

    protected NamedEntity(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return String.format("Entity %s (%s, %s)", getClass().getName(), getId(), name);
    }
}
