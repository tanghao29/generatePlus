package com.hy.ssm.lanxin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author TangHao
 * @since 2019-12-05
 */
@TableName("accounts")
public class Accounts implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "aid",type = IdType.UUID)
    private String aid;

    private String aname;

    private String apass;

    private Integer asex;

    private Integer age;

    private String cid;

    private String img;

    @TableField(exist=false)
    private Courses courses;

    public String getAid() {
        return aid;
    }
    public void setAid(String aid) {
        this.aid = aid;
    }

    public String getAname() {
        return aname;
    }
    public void setAname(String aname) {
        this.aname = aname;
    }

    public String getApass() {
        return apass;
    }
    public void setApass(String apass) {
        this.apass = apass;
    }

    public Integer getAsex() {
        if(this.asex==null){
            this.asex=-1;
        }
        return asex;
    }
    public void setAsex(Integer asex) {
        this.asex = asex;
    }

    public Integer getAge() {
        if(this.age==null){
            this.age=-1;
        }
        return age;
    }
    public void setAge(Integer age) {
        this.age = age;
    }

    public String getCid() {
        return cid;
    }
    public void setCid(String cid) {
        this.cid = cid;
    }

    public Courses getCourses() {
        return courses;
    }
    public void setCourses(Courses courses) {
        this.courses = courses;
    }

    public String getImg() {
        return img;
    }
    public void setImg(String img) {
        this.img = img;
    }

    @Override
    public String toString() {
        return "Accounts{" +
                "aid='" + aid + '\'' +
                ", aname='" + aname + '\'' +
                ", apass='" + apass + '\'' +
                ", asex=" + asex +
                ", age=" + age +
                ", cid='" + cid + '\'' +
                '}';
    }

}
