package com.hy.ssm.lanxin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

@TableName("course")
public class Courses implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(value = "cid",type = IdType.UUID)
    private String cid;

    private String cname;

    public String getCid() {
        return cid;
    }
    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getCname() {
        return cname;
    }
    public void setCname(String cname) {
        this.cname = cname;
    }

    @Override
    public String toString() {
        return "Courses{" +
                "cid='" + cid + '\'' +
                ", cname='" + cname + '\'' +
                '}';
    }
}
