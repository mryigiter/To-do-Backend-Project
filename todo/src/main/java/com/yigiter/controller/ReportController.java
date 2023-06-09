package com.yigiter.controller;

import com.yigiter.service.ReportService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;

@RestController
@RequestMapping("/excel")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    // ************* USER_REPORT ************************
    @GetMapping("/download/users")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Resource> getUserReport() {
        String fileName = "users.xlsx";
        ByteArrayInputStream bais = reportService.getUserReport();
        InputStreamResource file = new InputStreamResource(bais);
        return ResponseEntity.ok().
                header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + fileName).
                contentType(MediaType.parseMediaType("application/vmd.ms-excel")).
                body(file);
    }

    // ************* TODO_REPORT ************************
    @GetMapping("/download/todo")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Resource> getTodoReport() {
        String fileName = "Todo.xlsx";
        ByteArrayInputStream bais = reportService.getTodoReport();
        InputStreamResource file = new InputStreamResource(bais);
        return ResponseEntity.ok().
                header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + fileName).
                contentType(MediaType.parseMediaType("application/vmd.ms-excel")).
                body(file);
    }
}
