package com.api.taskmanager.service;

import com.api.taskmanager.BaseTestClass;
import com.api.taskmanager.model.Board;
import com.api.taskmanager.model.User;
import com.api.taskmanager.repository.BoardRepository;
import com.api.taskmanager.response.BoardDtoResponse;
import com.api.taskmanager.response.UserDtoResponse;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class BoardServiceTest extends BaseTestClass {

    @Mock
    BoardRepository repository;

    @InjectMocks
    BoardService service;

    @Test
    public void findAllWithReturnValues() {
        when(repository.findAll()).thenReturn(defaultBoardList);
        assertEquals(1, service.findAll().size());
        assertEquals(BoardDtoResponse.class, service.findAll().get(0).getClass());
        verify(repository, times(2)).findAll();
    }
}