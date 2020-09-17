package com.pdproject.iolibrary.model;

import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class Base {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "create_date")
    @CreatedDate
    private Date createDate;

    @Column(name = "modify_date")
    @LastModifiedDate
    private Timestamp modifyDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "create_by")
    @CreatedBy
    private User createBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "modify_by")
    @LastModifiedBy
    private User modifyBy;

    @Column(name = "enabled")
    private Boolean enabled;
}
