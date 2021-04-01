package com.example.android_demo.repository.entity.submit;

import java.io.Serializable;

/**
 * Created by: PeaceJay
 * Created date: 2021/3/30
 * Description:
 */
public class SRegister {

    /**
     * username : 1732037
     * password : a123456
     * phone : 123312
     */

    private String username;
    private String password;
    private String phone;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
