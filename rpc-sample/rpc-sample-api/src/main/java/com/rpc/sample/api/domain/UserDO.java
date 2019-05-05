package com.rpc.sample.api.domain;

/**
 * @author: lingjun.jlj
 * @date: 2019/5/5 14:09
 * @description:
 */

public class UserDO {

    private String Id;

    private String name;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "UserDO{" +
                "Id='" + Id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
