package com.sky.demo.web_demo_multi_tenant_separate_db.service;


import com.sky.demo.web_demo_multi_tenant_separate_db.base.Pager;
import com.sky.demo.web_demo_multi_tenant_separate_db.dto.anlog.AnLogForm;
import com.sky.demo.web_demo_multi_tenant_separate_db.dto.anlog.AnLogInsertRequest;
import com.sky.demo.web_demo_multi_tenant_separate_db.dto.anlog.AnLogQueryRequest;
import com.sky.demo.web_demo_multi_tenant_separate_db.dto.anlog.AnLogUpdateRequest;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by rg on 2015/6/11.
 */
@Service
public interface AnLogService {

    AnLogForm query(long id);

    Pager<AnLogForm> queryList(AnLogQueryRequest record);

    boolean delete(long id);

    boolean deleteList(List<Long> ids);

    boolean add(AnLogInsertRequest record);

    boolean addList(List<AnLogInsertRequest> records);

    boolean update(AnLogUpdateRequest record);

    boolean updateList(List<AnLogUpdateRequest> records);

}
