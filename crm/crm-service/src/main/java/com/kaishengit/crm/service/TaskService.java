package com.kaishengit.crm.service;

import com.kaishengit.crm.entity.Task;

import java.util.List;

/**
 * 待办事项业务层
 * @author fankay
 */
public interface TaskService {
    /**
     * 保存新的待办事项
     * @param task
     */
    void saveNewTask(Task task);

    /**
     * 根据用户ID查找对应的待办事项列表
     * @param id
     * @return
     */
    List<Task> findTaskByAccountId(Integer id);

    /**
     * 根据ID查找待办事项
     * @param id
     * @return
     */
    Task findTaskById(Integer id);

    /**
     * 根据ID删除待办事项
     * @param id
     */
    void deleteById(Integer id);
}
