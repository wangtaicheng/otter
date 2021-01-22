package com.alibaba.otter.manager.biz.service.impl;

import com.alibaba.otter.manager.biz.common.exceptions.ManagerException;
import com.alibaba.otter.manager.biz.common.exceptions.RepeatConfigureException;
import com.alibaba.otter.manager.biz.dao.DataMatrixMapper;
import com.alibaba.otter.manager.biz.entity.DataMatrixDO;
import com.alibaba.otter.manager.biz.service.DataMatrixService;
import com.alibaba.otter.shared.common.model.config.data.DataMatrix;
import com.alibaba.otter.shared.common.utils.Assert;
import com.alibaba.otter.shared.common.utils.JsonUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * (DataMatrix)表服务实现类
 *
 * @author jc-wangtc@chinaunicom.cn
 * @since 2021-01-21 08:38:38
 */
@Service("dataMatrixService")
public class DataMatrixServiceImpl extends ServiceImpl<DataMatrixMapper, DataMatrixDO> implements DataMatrixService {
    private static final Logger logger = LoggerFactory.getLogger(DataMatrixServiceImpl.class);

    /**
     * 添加
     */
    @Override
    public void create(final DataMatrix matrix) {
        Assert.assertNotNull(matrix);
        try {
            DataMatrixDO matrixlDO = modelToDo(matrix);
            if (!save(matrixlDO)) {
                String exceptionCause = "exist the same repeat canal in the database.";
                logger.warn("WARN ## {}", exceptionCause);
                throw new RepeatConfigureException(exceptionCause);
            }
        } catch (RepeatConfigureException rce) {
            throw rce;
        } catch (Exception e) {
            logger.error("ERROR ## create canal has an exception!");
            throw new ManagerException(e);
        }
    }


    /**
     * 删除
     */
    @Override
    public void remove(final Long matrixId) {
        Assert.assertNotNull(matrixId);

        try {
            removeById(matrixId);
        } catch (Exception e) {
            logger.error("ERROR ## remove canal({}) has an exception!", matrixId);
            throw new ManagerException(e);
        }


    }

    /**
     * 修改
     */
    @Override
    public void modify(final DataMatrix matrix) {
        Assert.assertNotNull(matrix);

        try {
            DataMatrixDO matrixDo = modelToDo(matrix);
            if (!updateById(matrixDo)) {

                String exceptionCause = "exist the same repeat matrix in the database.";
                logger.warn("WARN ## {}", exceptionCause);
                throw new RepeatConfigureException(exceptionCause);
            }
        } catch (RepeatConfigureException rce) {
            throw rce;
        } catch (Exception e) {
            logger.error("ERROR ## modify canal({}) has an exception!", matrix.getId());
            throw new ManagerException(e);
        }

    }

    @Override
    public List<DataMatrix> listByIds(Long... identities) {
        List<DataMatrix> matrixs = new ArrayList<DataMatrix>();
        try {
            List<DataMatrixDO> matrixDos = null;
            if (identities.length < 1) {
                matrixDos = list();
                if (matrixDos.isEmpty()) {
                    logger.debug("DEBUG ## couldn't query any canal, maybe hasn't create any canal.");
                    return matrixs;
                }
            } else {
                matrixDos = dataMatrixDao.listByMultiId(identities);
                if (matrixDos.isEmpty()) {
                    String exceptionCause = "couldn't query any canal by matrixIds:" + Arrays.toString(identities);
                    logger.error("ERROR ## {}", exceptionCause);
                    throw new ManagerException(exceptionCause);
                }
            }
            matrixs = doToModel(matrixDos);
        } catch (Exception e) {
            logger.error("ERROR ## query channels has an exception!");
            throw new ManagerException(e);
        }

        return matrixs;
    }

    @Override
    public List<DataMatrix> listAll() {
        return listByIds();
    }

    @Override
    public DataMatrix findById(Long matrixId) {
        Assert.assertNotNull(matrixId);
        DataMatrixDO canals = getById(matrixId);
        if (canals == null) {
            String exceptionCause = "query matrixId:" + matrixId + " return null.";
            logger.error("ERROR ## {}", exceptionCause);
            throw new ManagerException(exceptionCause);
        }

        return doToModel(canals);
    }

    @Override
    public DataMatrix findByGroupKey(String groupKey) {
        Assert.assertNotNull(groupKey);
        DataMatrixDO matrixDo = dataMatrixDao.findByGroupKey(groupKey);
        if (matrixDo == null) {
            String exceptionCause = "query name:" + groupKey + " return null.";
            logger.error("ERROR ## {}", exceptionCause);
            throw new ManagerException(exceptionCause);
        }

        return doToModel(matrixDo);
    }

    @Override
    public int getCount(Map condition) {
        return dataMatrixDao.getCount(condition);
    }

    @Override
    public List<DataMatrix> listByCondition(Map condition) {
        List<DataMatrixDO> matrixDos = dataMatrixDao.listByCondition(condition);
        if (matrixDos.isEmpty()) {
            logger.debug("DEBUG ## couldn't query any canal by the condition:{}",
                    JsonUtils.marshalToString(condition));
            return new ArrayList<DataMatrix>();
        }

        return doToModel(matrixDos);
    }

    /**
     * 用于Model对象转化为DO对象
     */
    private DataMatrixDO modelToDo(DataMatrix matrix) {
        DataMatrixDO matrixDo = new DataMatrixDO();
        try {
            matrixDo.setId(matrix.getId());
            matrixDo.setGroupKey(matrix.getGroupKey());
            matrixDo.setDescription(matrix.getDescription());
            matrixDo.setMaster(matrix.getMaster());
            matrixDo.setSlave(matrix.getSlave());
            matrixDo.setGmtCreate(matrix.getGmtCreate());
            matrixDo.setGmtModified(matrix.getGmtModified());
        } catch (Exception e) {
            logger.error("ERROR ## change the matrix Model to Do has an exception");
            throw new ManagerException(e);
        }
        return matrixDo;
    }

    /**
     * 用于DO对象转化为Model对象
     */
    private DataMatrix doToModel(DataMatrixDO matrixDo) {
        DataMatrix matrix = new DataMatrix();
        try {
            matrix.setId(matrixDo.getId());
            matrix.setGroupKey(matrixDo.getGroupKey());
            matrix.setDescription(matrixDo.getDescription());
            matrix.setMaster(matrixDo.getMaster());
            matrix.setSlave(matrixDo.getSlave());
            matrix.setGmtCreate(matrixDo.getGmtCreate());
            matrix.setGmtModified(matrixDo.getGmtModified());
        } catch (Exception e) {
            logger.error("ERROR ## change the canal Do to Model has an exception");
            throw new ManagerException(e);
        }

        return matrix;
    }

    private List<DataMatrix> doToModel(List<DataMatrixDO> matrixDos) {
        return matrixDos.stream().map(this::doToModel).collect(Collectors.toList());
    }
}
