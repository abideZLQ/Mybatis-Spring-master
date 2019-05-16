package com.bear.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "sign")
public class Sign {
    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    private Integer uid;

    private Date signintime;

    private Integer instatus;

    private Date signouttime;

    private Integer outstatus;

    private Date sdate;


    /**
     * 0 不可用 1 可用
     */
    private Integer available;

    // 新增属性User
    private Users users;

    public Users getUsers() {
        return users;
    }
    public void setUsers(Users users) {
        this.users = users;
    }

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
     * @return uid
     */
    public Integer getUid() {
        return uid;
    }

    /**
     * @param uid
     */
    public void setUid(Integer uid) {
        this.uid = uid;
    }

    /**
     * @return signintime
     */
    public Date getSignintime() {
        return signintime;
    }

    /**
     * @param signintime
     */
    public void setSignintime(Date signintime) {
        this.signintime = signintime;
    }

    /**
     * @return instatus
     */
    public Integer getInstatus() {
        return instatus;
    }

    /**
     * @param instatus
     */
    public void setInstatus(Integer instatus) {
        this.instatus = instatus;
    }

    /**
     * @return signouttime
     */
    public Date getSignouttime() {
        return signouttime;
    }

    /**
     * @param signouttime
     */
    public void setSignouttime(Date signouttime) {
        this.signouttime = signouttime;
    }

    /**
     * @return outstatus
     */
    public Integer getOutstatus() {
        return outstatus;
    }

    /**
     * @param outstatus
     */
    public void setOutstatus(Integer outstatus) {
        this.outstatus = outstatus;
    }

    /**
     * @return sdate
     */
    public Date getSdate() {
        return sdate;
    }

    /**
     * @param sdate
     */
    public void setSdate(Date sdate) {
        this.sdate = sdate;
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