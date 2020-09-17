package com.pdproject.iolibrary.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "user")
public class User extends Base {

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "avatar")
    private String avatar;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role", referencedColumnName = "id", columnDefinition = "default 1")
    private Role role;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "create_by")
    private List<FileIO> fileIOList;
}
