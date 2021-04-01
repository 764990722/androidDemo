package com.example.android_demo.repository.entity.receive;

import java.io.Serializable;
import java.util.List;

/**
 * Created by: PeaceJay
 * Created date: 2021/3/30
 * Description:
 */
public class RUserList implements Serializable {

    private List<ListBean> list;

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean implements Serializable {
        /**
         * id : 1
         * username : 17320378800
         * password : a123456
         * phone : 123312
         */

        private String id;
        private String username;
        private String password;
        private String phone;
        private String herdImage;

        public String getHerdImage() {
            return herdImage == null ? "" : herdImage;
        }

        public void setHerdImage(String herdImage) {
            this.herdImage = herdImage;
        }

        public String getId() {
            return id == null ? "0" : id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUsername() {
            return username == null ? "" : username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password == null ? "" : password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getPhone() {
            return phone == null ? "" : phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }
    }
}
