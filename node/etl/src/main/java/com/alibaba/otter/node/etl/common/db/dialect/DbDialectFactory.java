/*
 * Copyright (C) 2010-2101 Alibaba Group Holding Limited.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.alibaba.otter.node.etl.common.db.dialect;

import com.alibaba.otter.node.etl.common.datasource.DataSourceService;
import com.alibaba.otter.shared.common.model.config.data.db.DbMediaSource;
import com.google.common.collect.OtterMigrateMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.DatabaseMetaData;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author jianghang 2011-10-27 下午02:12:06
 * @version 4.0.0
 */
public class DbDialectFactory implements DisposableBean {

    private static final Logger logger = LoggerFactory.getLogger(DbDialectFactory.class);
    private DataSourceService dataSourceService;
    private DbDialectGenerator dbDialectGenerator;

    // 第一层pipelineId , 第二层DbMediaSource id
    private Map<Long, Map<DbMediaSource, DbDialect>> dialects;

    public DbDialectFactory() {
        dialects = OtterMigrateMap
                .makeSoftValueComputingMapWithRemoveListenr(pipelineId -> {
                            // 构建第二层map
                            return OtterMigrateMap.makeComputingMap(source -> {
                                DataSource dataSource = dataSourceService.getDataSource(pipelineId, source);
                                final JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
                                return (DbDialect) jdbcTemplate.execute((ConnectionCallback) c -> {
                                    DatabaseMetaData meta = c.getMetaData();
                                    String databaseName = meta.getDatabaseProductName();
                                    String databaseVersion = meta.getDatabaseProductVersion();
                                    int databaseMajorVersion = meta.getDatabaseMajorVersion();
                                    int databaseMinorVersion = meta.getDatabaseMinorVersion();
                                    DbDialect dialect = dbDialectGenerator.generate(jdbcTemplate,
                                            databaseName,
                                            databaseVersion,
                                            databaseMajorVersion,
                                            databaseMinorVersion,
                                            source.getType());
                                    if (dialect == null) {
                                        throw new UnsupportedOperationException("no dialect for" + databaseName);
                                    }

                                    if (logger.isInfoEnabled()) {
                                        logger.info(String.format("--- DATABASE: %s, SCHEMA: %s ---",
                                                databaseName,
                                                (dialect.getDefaultSchema() == null) ? dialect
                                                        .getDefaultCatalog() : dialect.getDefaultSchema()));
                                    }

                                    return dialect;
                                });

                            });
                        },
                        (pipelineId, dialect) -> {
                            if (dialect == null) {
                                return;
                            }

                            for (DbDialect dbDialect : dialect.values()) {
                                dbDialect.destory();
                            }
                        });

    }

    public DbDialect getDbDialect(Long pipelineId, DbMediaSource source) {
        return dialects.get(pipelineId).get(source);
    }

    public void destory(Long pipelineId) {
        Map<DbMediaSource, DbDialect> dialect = dialects.remove(pipelineId);
        if (dialect != null) {
            for (DbDialect dbDialect : dialect.values()) {
                dbDialect.destory();
            }
        }
    }

    @Override
    public void destroy() throws Exception {
        Set<Long> pipelineIds = new HashSet<Long>(dialects.keySet());
        for (Long pipelineId : pipelineIds) {
            destory(pipelineId);
        }
    }

    // =============== setter / getter =================

    public void setDataSourceService(DataSourceService dataSourceService) {
        this.dataSourceService = dataSourceService;
    }

    public void setDbDialectGenerator(DbDialectGenerator dbDialectGenerator) {
        this.dbDialectGenerator = dbDialectGenerator;
    }

}
