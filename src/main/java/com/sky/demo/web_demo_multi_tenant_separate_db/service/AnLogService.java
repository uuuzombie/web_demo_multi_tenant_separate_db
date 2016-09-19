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
public interface AnLogService {

    AnLogForm query(long id);

    List<AnLogForm> queryList(List<Long> ids);

    Pager<AnLogForm> queryList(AnLogQueryRequest queryRequest);

    boolean add(AnLogInsertRequest insertRequest);

    boolean addList(List<AnLogInsertRequest> insertRequests);

    boolean update(AnLogUpdateRequest updateRequest);

    boolean updateList(List<AnLogUpdateRequest> updateRequests);

    boolean delete(long id);

    boolean deleteList(List<Long> ids);
}
