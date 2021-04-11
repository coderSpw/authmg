package com.spw.authmg.configuration.datasource;

import com.spw.authmg.constant.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description 多数据源配置 （读写数据源）
 * @Author spw
 * @Date 2021/3/28
 */
@Slf4j
@Component
public class DynamicDatasource extends AbstractRoutingDataSource {
    @Autowired
    @Qualifier("master")
    private DataSource master;
    @Autowired
    @Qualifier("slave")
    private DataSource slave;

    @Override
    protected Object determineCurrentLookupKey() {
        log.info("数据源切换为：{}", DatasourceContextHolder.getDatasource());
        return DatasourceContextHolder.getDatasource();
    }

    @Override
    public void afterPropertiesSet() {
        //将主库设置为默认数据源
        this.setDefaultTargetDataSource(master);
        //添加多数据源，后期路由key使用
        Map<Object, Object> targetDataSource = new HashMap<>(3);
        targetDataSource.put(Constants.DatasourceType.MASTER, master);
        targetDataSource.put(Constants.DatasourceType.SLAVE, slave);
        this.setTargetDataSources(targetDataSource);
        //将targetDataSources 加载到 resolvedDataSources
        super.afterPropertiesSet();
    }
}
