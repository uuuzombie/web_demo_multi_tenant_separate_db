package com.sky.demo.web_demo_multi_tenant_separate_db.dao;

import java.util.List;
import java.util.Map;

import com.sky.demo.web_demo_multi_tenant_separate_db.dto.anlog.AnLogForm;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;


import com.sky.demo.web_demo_multi_tenant_separate_db.model.AnLog;


/**
 * Created by rg on 2015/6/11.
 */
//@Repository   //for MyBatis
public interface AnLogDao {

    public AnLogForm selectById(@Param("id") final Long id);

    public List<AnLogForm> selectList(Map<String, Object> condition);  //for MyBatis

    public List<AnLogForm> selectList(Map<String, Object> condition, RowBounds rowBounds);  //for MyBatis

    public int selectCount(Map<String, Object> condition);

    public int deleteById(@Param("id") final Long id);

    public int batchDelete(List<Long> ids);

    public int insert(AnLog record);

    public int batchInsert(List<AnLog> recordList);

    public int update(AnLog record);

    public int batchUpdate(List<Long> ids, AnLog record);

}
