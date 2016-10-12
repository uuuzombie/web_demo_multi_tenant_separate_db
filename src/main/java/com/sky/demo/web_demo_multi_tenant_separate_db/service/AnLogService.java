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

    public AnLogForm query(long id);

    public List<AnLogForm> queryList(List<Long> ids);

    public Pager<AnLogForm> queryList(AnLogQueryRequest queryRequest);

    public boolean add(AnLogInsertRequest insertRequest);

    public boolean addList(List<AnLogInsertRequest> insertRequests);

    public boolean update(AnLogUpdateRequest updateRequest);

    public boolean asyncUpdate(AnLogUpdateRequest updateRequest);

    public boolean updateList(List<AnLogUpdateRequest> updateRequests);

    public boolean delete(long id);

    public boolean deleteList(List<Long> ids);
}
