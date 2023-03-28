package com.yigiter.domain;


import com.yigiter.domain.enums.RoleType;

import javax.persistence.*;


@Entity
@Table(name = "t_role")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    private RoleType type;


    @Override
    public String toString() {
        return "Role{" +
                "type=" + type +
                '}';
    }



    public Role(Integer id, RoleType type) {
        this.id = id;
        this.type = type;
    }

    public Role() {
    }

    public Integer getId() {
        return id;
    }

//    public void setId(Integer id) {
//        this.id = id;
//    }

    public RoleType getType() {
        return type;
    }

    public void setType(RoleType type) {
        this.type = type;
    }
}
