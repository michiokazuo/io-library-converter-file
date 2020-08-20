package com.pdproject.iolibrary.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "user")
public class User extends Base{

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "avatar")
    private String avatar;

    @ManyToMany
    @JoinTable(name = "role_user"
            ,joinColumns = @JoinColumn(name = "id_user")
            ,inverseJoinColumns = @JoinColumn(name = "id_role"))
    private List<Role> roleList;

    @OneToMany(mappedBy = "user")
    private List<FileIO> fileIOList;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public List<Role> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<Role> roleList) {
        this.roleList = roleList;
    }

    public List<FileIO> getFileIOList() {
        return fileIOList;
    }

    public void setFileIOList(List<FileIO> fileIOList) {
        this.fileIOList = fileIOList;
    }
}
