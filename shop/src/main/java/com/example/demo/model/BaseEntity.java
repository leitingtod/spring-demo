package com.example.demo.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class BaseEntity implements Serializable {
    @Id
    private String id;

    @Column(columnDefinition = "int default 0")
    @Version
    private Integer hbVersion = 0;


    @PrePersist
    public void ensureId() {
        id = IdGenerator.createUUID();
    }
}
