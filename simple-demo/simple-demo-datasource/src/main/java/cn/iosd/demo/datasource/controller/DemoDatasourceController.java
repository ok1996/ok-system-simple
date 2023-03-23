package cn.iosd.demo.datasource.controller;

import cn.iosd.demo.datasource.domain.DemoDatasource;
import cn.iosd.starter.datasource.base.BaseController;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author ok1996
 */
@Tag(name = "datasource示例模块数据库Controller")
@RestController
@RequestMapping("/datasource")
public class DemoDatasourceController extends BaseController<DemoDatasource> {

}
