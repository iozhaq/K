package com.kaishengit.crm.service;

import com.github.pagehelper.PageInfo;
import com.kaishengit.crm.entity.Account;
import com.kaishengit.crm.entity.SaleChance;
import com.kaishengit.crm.entity.SaleChanceRecord;

import java.util.List;

/**
 * 销售机会业务层
 * @author fankay
 */
public interface SaleChanceService {

    /**
     * 获取机会进度列表
     * @return
     */
    List<String> findAllSalesProgress();

    /**
     * 新增销售机会
     * @param saleChance
     */
    void saveNewSalesChance(SaleChance saleChance);

    /**
     * 根据Account对象查询对应的销售机会分页列表
     * @param account
     * @param pageNo
     * @return
     */
    PageInfo<SaleChance> pageForAccountSales(Account account, Integer pageNo);

    /**
     * 根据主键查询销售机会
     * @param id
     * @return
     */
    SaleChance findSalesChanceWithCustomerById(Integer id);

    /**
     * 根据销售机会的ID查询对应的跟进记录列表
     * @param id
     * @return
     */
    List<SaleChanceRecord> findSalesChanceRecodeListBySalesId(Integer id);

    /**
     * 给销售机会添加新跟进记录
     * @param record
     */
    void saveNewSalesChanceRecode(SaleChanceRecord record);

    /**
     * 改变销售机会的进度
     * @param id
     * @param progress
     */
    void updateSalesChanceState(Integer id, String progress);

    /**
     * 删除销售机会
     * @param id
     */
    void deleteSalesChanceById(Integer id);

    /**
     * 跟进客户ID查询对应的销售记录
     * @param id
     * @return
     */
    List<SaleChance> findSalesChanceByCustId(Integer id);
}
