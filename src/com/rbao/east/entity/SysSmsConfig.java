package com.rbao.east.entity;

import java.io.Serializable;

public class SysSmsConfig extends BaseEntity implements Serializable{
    private Integer id;

    private String sysSmsAccount;

    private String sysSmsPassword;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSysSmsAccount() {
        return sysSmsAccount;
    }

    public void setSysSmsAccount(String sysSmsAccount) {
        this.sysSmsAccount = sysSmsAccount == null ? null : sysSmsAccount.trim();
    }

    public String getSysSmsPassword() {
        return sysSmsPassword;
    }

    public void setSysSmsPassword(String sysSmsPassword) {
        this.sysSmsPassword = sysSmsPassword == null ? null : sysSmsPassword.trim();
    }
}