package org.tinkerbell.security.entity.po;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

/**
 * Created by nn_liu on 2017/3/8.
 */


@Entity
@Table(name = "auth_role")
public class AuthRole {

    @Id
    @Column(name = "roleid")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer roleid;

    @Column(name = "rolename")
    private String roleName;

    @Column(name = "description")
    private String description;

    @Column(name = "datachange_lasttime")
    private Timestamp dataChange_LastTime;

    @ManyToMany(mappedBy = "authRoles")
    private List<AuthUser> authUsers;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name="auth_role_perm",joinColumns=@JoinColumn(name="roleid"),
            inverseJoinColumns=@JoinColumn(name="permissionid"))
    private List<AuthPermission> authPerms;

    public Integer getRoleid() {
        return roleid;
    }

    public void setRoleid(Integer roleid) {
        this.roleid = roleid;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getDataChange_LastTime() {
        return dataChange_LastTime;
    }

    public void setDataChange_LastTime(Timestamp dataChange_LastTime) {
        this.dataChange_LastTime = dataChange_LastTime;
    }

    public List<AuthUser> getAuthUsers() {
        return authUsers;
    }

    public void setAuthUsers(List<AuthUser> authUsers) {
        this.authUsers = authUsers;
    }

    public List<AuthPermission> getAuthPerms() {
        return authPerms;
    }

    public void setAuthPerms(List<AuthPermission> authPerms) {
        this.authPerms = authPerms;
    }

}
