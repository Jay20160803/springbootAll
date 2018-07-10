package com.jk51.shiroTest.redisDemo;

/**
 * 版权所有(C) 2017 上海银路投资管理有限公司
 * 描述:
 * 作者: gaojie
 * 创建日期: 2018/7/6
 * 修改记录:
 */
public class Person {

    private String firstNamne;
    private String lastName;
    private Address address;


    public String getFirstNamne() {
        return firstNamne;
    }

    public void setFirstNamne(String firstNamne) {
        this.firstNamne = firstNamne;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
