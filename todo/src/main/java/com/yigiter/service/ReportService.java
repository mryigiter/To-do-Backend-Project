package com.yigiter.service;

import com.yigiter.domain.Todo;
import com.yigiter.domain.User;
import com.yigiter.dto.response.ResponseMessage;
import com.yigiter.report.ExcelReporter;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

@Service
public class ReportService {

    private final UserService userService;
    private final TodoService todoService;



    public ReportService(UserService userService, TodoService todoService) {
        this.userService = userService;
        this.todoService = todoService;

    }

    public ByteArrayInputStream getUserReport()  {
        List<User> users =   userService.getUsers();
        try {
            return ExcelReporter.getUserExcelReport(users);
        } catch (IOException e) {
            throw new RuntimeException(ResponseMessage.EXCEL_REPORT_ERROR_MESSAGE);
        }
    }

    public ByteArrayInputStream getTodoReport() {

        List<Todo> todos = todoService.getAllTodo();
        try {
            return ExcelReporter.getTodoExcelReport(todos);
        } catch (IOException e) {
            throw new RuntimeException(ResponseMessage.EXCEL_REPORT_ERROR_MESSAGE);
        }

    }
}
