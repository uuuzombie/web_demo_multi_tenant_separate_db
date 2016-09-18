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

    AnLogDto select(Map<String, Object> condition);

    List<AnLogDto> selectList(Map<String, Object> condition);

    long selectCount(Map<String, Object> condition);

    int insert(AnLog record);

    int batchInsert(List<AnLog> records);

    int update(AnLog record);

    int batchUpdate(List<AnLog> records);

    int delete(final Long id);

    int batchDelete(List<Long> ids);

}
