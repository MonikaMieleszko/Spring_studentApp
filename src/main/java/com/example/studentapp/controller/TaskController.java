package com.example.studentapp.controller;

import com.example.studentapp.model.StudentModel;
import com.example.studentapp.model.TaskModel;
import com.example.studentapp.service.StudentService;
import com.example.studentapp.service.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class TaskController {
    private final TaskService taskService;
    private final StudentService studentService; //wstrzykujemy, żeby mieć dostęp do listy kursantów

    @GetMapping("/tasks")
    public String getTaskList(Model model) {
        List<TaskModel> list = taskService.getTaskList();
        model.addAttribute("taskModel", list);
        return "tasks/tasks";
    }

    @GetMapping("/addTask")
    public String getAddTask(Model model) {
        List<StudentModel> list = studentService.getStudentList();
        model.addAttribute("studentModel", list);
        return "tasks/addTask";
    }

    @PostMapping("/addTask")
    public RedirectView postAddTask(TaskModel task) {
        taskService.addTask(task);
        return new RedirectView("/tasks");
    }

    @GetMapping("/editTask/{id}")
    //@PathVariable pozwala na odczytanie informacji ze ścieżki URL do naszej metody - w tym przypadku id
    public String editTask(@PathVariable("id") Long id, Model model) {
        TaskModel task = taskService.getTaskById(id);
        model.addAttribute("taskModel", task);
        return "tasks/editTask";
    }

    @PostMapping("/editTask/{id}")
    public RedirectView postEditTask(@PathVariable("id") Long id, TaskModel editTask) {
        taskService.saveEditTask(editTask);
        return new RedirectView("/tasks");
    }

    @PostMapping("/delTask/{id}")
    public RedirectView delTask(@PathVariable("id") Long id) {
        taskService.delTask(id);
        return new RedirectView("/tasks");
    }
}
