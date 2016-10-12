package com.sky.demo.web_demo_multi_tenant_separate_db.dao;

import com.sky.demo.web_demo_multi_tenant_separate_db.dto.anlog.AnLogDto;
import com.sky.demo.web_demo_multi_tenant_separate_db.dto.anlog.AnLogForm;
import com.sky.demo.web_demo_multi_tenant_separate_db.model.AnLog;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by user on 16/9/18.
 */
public interface JdbcAnLogDao {

    public AnLogDto select(Map<String, Object> condition);

    public List<AnLogDto> selectList(Map<String, Object> condition);

    public long selectCount(Map<String, Object> condition);

    public int insert(AnLog record);

    public int batchInsert(List<AnLog> records);

    public int update(AnLog record);

    public int batchUpdate(List<AnLog> records);

    public int delete(final Long id);

    public int batchDelete(List<Long> ids);

}
