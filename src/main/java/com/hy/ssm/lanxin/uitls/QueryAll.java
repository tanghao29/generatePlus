package com.hy.ssm.lanxin.uitls;

import com.hy.ssm.lanxin.entity.Accounts;
import org.apache.ibatis.annotations.Param;

public class QueryAll {

public String query(@Param("accounts")Accounts accounts){
    StringBuffer sql=new StringBuffer("SELECT * FROM accounts a INNER JOIN course c ON a.cid=c.cid where 1=1 ");

        if (accounts.getAname()!=null && accounts.getAname()!="null" && !accounts.getAname().equals("null") && accounts.getAname()!="") {
            sql.append(" and a.aname like '%"+accounts.getAname()+"%'");
        }
        if (accounts.getAsex() != -1 && accounts.getAsex() !=null) {
            sql.append(" and a.asex="+accounts.getAsex()+"");
        }
        if (accounts.getAge()!=-1 && accounts.getAge()!=null ) {
            sql.append(" and a.age="+accounts.getAge()+"");
        }
        if (accounts.getCid() !=null && accounts.getCid()!="null" && !accounts.getCid().equals("null") && accounts.getCid()!="") {
            sql.append(" and a.cid="+accounts.getCid()+"");
        }

    return sql.toString();
}

}
