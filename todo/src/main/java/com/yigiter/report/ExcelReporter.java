package com.yigiter.report;

import com.yigiter.domain.Role;
import com.yigiter.domain.Todo;
import com.yigiter.domain.User;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.StringJoiner;

public class ExcelReporter {


    // !!! USER ******************
    static String SHEET_USER = "Users";
    static String[] USER_HEADERS = {"id","FirstName","LastName","PhoneNumber","Email","Roles"};

    // !!! TO_DO ******************
    static String SHEET_TODO="Todo_List";
    static  String[] TODO_HEADERS = {"id", "Title", "Message", "Status"};



    //*******************USER_REPORT***********************

    public static ByteArrayInputStream getUserExcelReport(List<User> users) throws IOException {
        Workbook workbook = new XSSFWorkbook();

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Sheet sheet = workbook.createSheet(SHEET_USER);
        Row headerRow =  sheet.createRow(0);


        for(int i=0; i< USER_HEADERS.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(USER_HEADERS[i]);
        }


        int rowId = 1;
        for(User user : users) {
            Row row = sheet.createRow(rowId++);
            row.createCell(0).setCellValue(user.getId());
            row.createCell(1).setCellValue(user.getFirstName());
            row.createCell(2).setCellValue(user.getLastName());
            row.createCell(3).setCellValue(user.getPhoneNumber());
            row.createCell(4).setCellValue(user.getEmail());


            // Customer , Administrator
            StringJoiner sj = new StringJoiner(",");

            for(Role role : user.getRoles()) {
                sj.add(role.getType().getName());
            }

            row.createCell(7).setCellValue(sj.toString());
        }

        workbook.write(out);
        workbook.close();

        return new ByteArrayInputStream(out.toByteArray());

    }


    //*******************TODO_REPORT***********************

    public static ByteArrayInputStream getTodoExcelReport(List<Todo> todos) throws IOException {
        Workbook workbook = new XSSFWorkbook();

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Sheet sheet = workbook.createSheet(SHEET_TODO);
        Row headerRow =  sheet.createRow(0);


        for(int i=0; i< TODO_HEADERS.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(TODO_HEADERS[i]);
        }


        int rowId = 1;
        for(Todo todo: todos) {
            Row row = sheet.createRow(rowId++);
            row.createCell(0).setCellValue(todo.getId());
            row.createCell(1).setCellValue(todo.getTitle());
            row.createCell(2).setCellValue(todo.getMessage());
            row.createCell(3).setCellValue(todo.isStatus() ? "Done" : "Not Done");
        }
        workbook.write(out);
        workbook.close();
        return new ByteArrayInputStream(out.toByteArray());
    }

}
