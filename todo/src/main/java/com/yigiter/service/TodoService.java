package com.yigiter.service;

import com.yigiter.domain.Todo;
import com.yigiter.dto.TodoDTO;
import com.yigiter.dto.TodoRequest;
import com.yigiter.repository.TodoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.Converter;
import java.util.ArrayList;
import java.util.List;

@Service
public class TodoService {

    private final TodoRepository todoRepository;

    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public List<TodoDTO> saveTodo(TodoRequest todoRequest) {
        Todo todo = new Todo();
        todo.setTitle(todoRequest.getTitle());
        todo.setMessage(todoRequest.getMessage());
        todoRepository.save(todo);
        return getAllTodoList();
    }

    public List<TodoDTO> getAllTodoList() {
        List<Todo> todoList = todoRepository.findAll();
        List<TodoDTO> todoDTOList = new ArrayList<>();
        for (Todo todo : todoList) {
            TodoDTO todoDTO = new TodoDTO(todo);
            todoDTOList.add(todoDTO);
        }
        return todoDTOList;
    }


    public void deleteById(Long id) {
        boolean exists = todoRepository.existsById(id);
        if (exists) {
            todoRepository.deleteById(id);
        } else throw new RuntimeException();
    }

    public void doneById(Long id) {
        Todo todo = todoRepository.findById(id).orElseThrow(
                RuntimeException::new
        );
        if (!todo.isStatus()) {
            todo.setStatus(true);
        }
        todoRepository.save(todo);
    }

    public Page<TodoDTO> getAllPages(Pageable pageable) {
        Page<Todo> todoPage = todoRepository.findAll(pageable);
        Page<TodoDTO> todoDTOPage = todoPage.map(
                getTodo -> {
                    TodoDTO todoDTO = todoTOTodoDTO(getTodo);
                    return todoDTO;
                });
        return todoDTOPage;
    }

    public TodoDTO todoTOTodoDTO(Todo todo) {
        TodoDTO todoDTO = new TodoDTO();
        todoDTO.setId(todo.getId());
        todoDTO.setTitle(todo.getTitle());
        todoDTO.setMessage(todo.getMessage());
        todoDTO.setStatus(todo.isStatus());
        return todoDTO;
    }

    public List<Todo> getAllTodo() {
        return todoRepository.findAll();
    }
}
