package com.kaishengit.crm.controller;

import com.kaishengit.crm.controller.exception.ForbiddenException;
import com.kaishengit.crm.controller.exception.NotFoundException;
import com.kaishengit.crm.entity.Account;
import com.kaishengit.crm.entity.Task;
import com.kaishengit.crm.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 待办事项控制器
 * @author fankay
 */
@Controller
@RequestMapping("/task")
public class TaskController extends BaseController {

    @Autowired
    private TaskService taskService;

    /**
     * 待办事项列表
     * @return
     */
    @GetMapping
    public String taskList(Model model, HttpSession session) {
        Account account = getCurrentAccount(session);
        List<Task> taskList = taskService.findTaskByAccountId(account.getId());

        model.addAttribute("taskList",taskList);
        return "task/home";
    }

    /**
     * 新增待办事项
     * @return
     */
    @GetMapping("/new")
    public String newTask() {
        return "task/new";
    }

    @PostMapping("/new")
    public String newTask(Task task) {
        taskService.saveNewTask(task);
        return "redirect:/task";
    }

    /**
     * 删除待办事项
     */
    @GetMapping("/{id:\\d+}/del")
    public String deleteTask(@PathVariable Integer id,HttpSession session) {
        Account account = getCurrentAccount(session);

        Task task = taskService.findTaskById(id);
        if(task == null) {
            throw new NotFoundException();
        }
        if(!task.getAccountId().equals(account.getId())) {
            throw new ForbiddenException();
        }
        taskService.deleteById(id);
        return "redirect:/task";
    }

}
