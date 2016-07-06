package com.comodin.fleet.portal.quartz;

import com.comodin.fleet.core.bean.LatLngDriverBean;
import com.comodin.fleet.core.mapper.LatLngDriverBeanMapper;
import com.comodin.fleet.service.ILatLngDriverService;
import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.entity.Example;

import javax.inject.Inject;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class TestMonitorLatLngDriverJob {
    private static final Logger log = Logger.getLogger(TestMonitorLatLngDriverJob.class);

    @Inject
    private ILatLngDriverService latLngDriverService;
    @Inject
    private SqlSessionTemplate sqlSession;


    public void doIt() throws SQLException {

        log.info("...................................TestMonitorLatLngDriverJob start.....");


        LatLngDriverBeanMapper mapper = sqlSession.getMapper(LatLngDriverBeanMapper.class);

        Example example = new Example(LatLngDriverBean.class);
        Example.Criteria criteria;
        example.setTableName("t_latlng_driver_testtable");
        example.orderBy("phoneUploadTimestamp").asc();
        RowBounds rowBounds = new RowBounds(0, 10);
        List<LatLngDriverBean> latLngDriverBeanList = mapper.selectByExampleAndRowBounds(example, rowBounds);

        if (latLngDriverBeanList == null || latLngDriverBeanList.isEmpty()) {
            return;
        }

        List<Integer> ids = latLngDriverBeanList.stream().map(LatLngDriverBean::getId).collect(Collectors.toList());

        latLngDriverService.saveList(latLngDriverBeanList);

        example = new Example(LatLngDriverBean.class);
        criteria = example.createCriteria();
        criteria.andIn("id", ids);
        example.setTableName("t_latlng_driver_testtable");
        mapper.deleteByExample(example);

        log.info("...................................TestMonitorLatLngDriverJob end.....");
    }
}
