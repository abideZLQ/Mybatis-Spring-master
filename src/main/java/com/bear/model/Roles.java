package com.bear.model;

import javax.persistence.*;
import java.util.List;

@Table(name = "roles")
public class Roles {
    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    private String role;

    /**
     * 描述
     */
    private String description;

    /**
     * 0 不可用 1可用
     */
    private Integer available;


    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return role
     */
    public String getRole() {
        return role;
    }

    /**
     * @param role
     */
    public void setRole(String role) {
        this.role = role;
    }

    /**
     * 获取描述
     *
     * @return description - 描述
     */
    public String getDescription() {
        return description;
    }

    /**
     * 设置描述
     *
     * @param description 描述
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * 获取0 不可用 1可用
     *
     * @return available - 0 不可用 1可用
     */
    public Integer getAvailable() {
        return available;
    }

    /**
     * 设置0 不可用 1可用
     *
     * @param available 0 不可用 1可用
     */
    public void setAvailable(Integer available) {
        this.available = available;
    }
}