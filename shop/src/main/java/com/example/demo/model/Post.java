package com.example.demo.model;

import java.util.Date;
import javax.persistence.*;
import javax.persistence.Column;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
public class Post extends BaseEntity {
    @Column(unique = true)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Temporal(TemporalType.TIMESTAMP)
    private Date updated;

    @Override
    public String toString() {
        return "Post{"
                + "username='"
                + title
                + '\''
                + ", content='"
                + content
                + '\''
                + ", updated='"
                + updated
                + '\''
                + "} "
                + super.toString();
    }
}
