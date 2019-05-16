package com.bear.model;

import javax.persistence.*;

@Table(name = "users")
public class Users {
    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    private String username;

    private String password;

    private String age;

    private String email;

    private String phone;

    /**
     * 班级
     */
    private String classname;

    /**
     * 盐
     */
    private String salt;

    /**
     * 0 不可用 1 可用
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
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return age
     */
    public String getAge() {
        return age;
    }

    /**
     * @param age
     */
    public void setAge(String age) {
        this.age = age;
    }

    /**
     * @return email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * @param phone
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * 获取班级
     *
     * @return classname - 班级
     */
    public String getClassname() {
        return classname;
    }

    /**
     * 设置班级
     *
     * @param classname 班级
     */
    public void setClassname(String classname) {
        this.classname = classname;
    }

    /**
     * 获取盐
     *
     * @return salt - 盐
     */
    public String getSalt() {
        return salt;
    }

    /**
     * 设置盐
     *
     * @param salt 盐
     */
    public void setSalt(String salt) {
        this.salt = salt;
    }

    /**
     * 获取0 不可用 1 可用
     *
     * @return available - 0 不可用 1 可用
     */
    public Integer getAvailable() {
        return available;
    }

    /**
     * 设置0 不可用 1 可用
     *
     * @param available 0 不可用 1 可用
     */
    public void setAvailable(Integer available) {
        this.available = available;
    }
}