package com.hy.ssm.lanxin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hy.ssm.lanxin.entity.Accounts;
import com.hy.ssm.lanxin.uitls.QueryAll;
import com.hy.ssm.lanxin.uitls.RedisCache;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author TangHao
 * @since 2019-12-05
 */
@CacheNamespace(implementation = RedisCache.class)
public interface AccountsMapper extends BaseMapper<Accounts> {

//    根据uuid添加数据
    @SelectKey(statement = "select uuid()",keyProperty = "aid",before = true,resultType = String.class)
    @Insert("insert into accounts values(#{aid},#{aname},#{apass},#{asex},#{age},#{cid},#{img})")
    public void Add(Accounts accounts);

//    分页联合查询
    @Results({
            @Result(property = "courses.cid",column = "cid"),
            @Result(property = "courses.cname",column = "cname")
    })
    @SelectProvider(type = QueryAll.class,method = "query")
    IPage<Accounts> queryAll(@Param("page") Page<Accounts> page,@Param("accounts") Accounts accounts);

    //    联合查询
    @Results({
            @Result(property = "courses.cid",column = "cid"),
            @Result(property = "courses.cname",column = "cname")
    })
    @SelectProvider(type = QueryAll.class,method = "query")
    List<Accounts> queryAlls( @Param("accounts") Accounts accounts);





}
