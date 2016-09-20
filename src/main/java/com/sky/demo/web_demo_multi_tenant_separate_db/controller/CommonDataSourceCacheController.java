package com.sky.demo.web_demo_multi_tenant_separate_db.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by user on 16/9/20.
 */
@RequestMapping("/datasource")
@Controller
public class CommonDataSourceCacheController {

    private static final Logger logger = LoggerFactory.getLogger(CommonDataSourceCacheController.class);

}
