package com.comodin.fleet.service;

import com.comodin.fleet.basic.exception.ParameterException;
import com.comodin.fleet.basic.service.IBaseService;
import com.comodin.fleet.core.bean.VehicleBean;
import com.comodin.fleet.vo.VehicleBeanVo;

import java.util.List;

public interface IVehicleService extends IBaseService<VehicleBean> {

    /**
     * * 查询所有的任务列表；
     * 分页参数，必需参数；
     *
     * @param vo vo包含了查询参数，和必需的，分页参数。
     * @return 返回List 包含分页信息
     * @throws ParameterException 若必需参数不合法，抛出
     */
    List<VehicleBean> getListByVo(VehicleBeanVo vo) throws ParameterException;
}
