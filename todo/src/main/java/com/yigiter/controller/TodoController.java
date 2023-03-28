package com.yigiter.controller;

import com.yigiter.dto.TodoDTO;
import com.yigiter.dto.response.ResponseMessage;
import com.yigiter.dto.response.TResponse;
import com.yigiter.dto.TodoRequest;
import com.yigiter.service.TodoService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/todo")
public class TodoController {

    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('ADMIN') or hasRole('CUSTOMER')")
    public ResponseEntity<List<TodoDTO>> addTodo(@Valid @RequestBody TodoRequest todoRequest) {
        List<TodoDTO> todoList = todoService.saveTodo(todoRequest);
        return new ResponseEntity<>(todoList, HttpStatus.CREATED);
    }

    @GetMapping("/list")
    @PreAuthorize("hasRole('ADMIN') or hasRole('CUSTOMER')")
    public ResponseEntity<List<TodoDTO>> listTodo() {
        List<TodoDTO> todoDTOList = todoService.getAllTodoList();
        return new ResponseEntity<>(todoDTOList, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('CUSTOMER')")
    public ResponseEntity<List<TodoDTO>> deleteTodo(@PathVariable Long id) {
        todoService.deleteById(id);
        List<TodoDTO> todoDTOList = todoService.getAllTodoList();
        return new ResponseEntity<>(todoDTOList, HttpStatus.OK);
    }

    @PatchMapping("/done/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('CUSTOMER')")
    public ResponseEntity<List<TodoDTO>> doneTodo(@PathVariable Long id) {
        todoService.doneById(id);
        List<TodoDTO> todoDTOList = todoService.getAllTodoList();
        return new ResponseEntity<>(todoDTOList, HttpStatus.OK);
    }

    @GetMapping("/pages")
    @PreAuthorize("hasRole('ADMIN') or hasRole('CUSTOMER')")
    public ResponseEntity<Page<TodoDTO>> pageTodo(@RequestParam("page") int page,
                                                  @RequestParam("size") int size,
                                                  @RequestParam("sort") String sort,
                                                  @RequestParam(value = "direction",
                                                          required = false, //true olursa sıralama türünü belirtmemiz lazım
                                                          defaultValue = "ASC") Sort.Direction direction) {

        Pageable pageable = PageRequest.of(page,size,Sort.by(direction,sort));
        //Pageable pageable= PageRequest.of(page,size,direction,sort);
        Page<TodoDTO> pages =todoService.getAllPages(pageable);
        return new ResponseEntity<>(pages, HttpStatus.OK);
    }


}
