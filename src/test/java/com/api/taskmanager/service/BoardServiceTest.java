//package com.api.taskmanager.service;
//
//import com.api.taskmanager.BaseTestClass;
//import com.api.taskmanager.model.Board;
//import com.api.taskmanager.repository.BoardRepository;
//import com.api.taskmanager.response.BoardDtoResponse;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//class BoardServiceTest extends BaseTestClass {
//
//    BoardRepository repository = Mockito.mock(BoardRepository.class);
//    BoardService service = new BoardService(repository);
//
//    @Test
//    public void findAllBoardDtoResponseWithReturn() {
//        when(repository.findAll()).thenReturn(defaultMockBoardList);
//
//        assertEquals(1, service.findAll().size());
//        assertEquals(BoardDtoResponse.class, service.findAll().get(0).getClass());
//        verify(repository, times(2)).findAll();
//    }
//
//    @Test
//    public void findAllBoardDtoResponseWithNoReturn() {
//        when(repository.findAll()).thenReturn(List.of());
//
//        assertEquals(0, service.findAll().size());
//        verify(repository, times(1)).findAll();
//    }
//
//    @Test
//    public void findByIdWithReturn() {
//        when(repository.findById(anyLong())).thenReturn(Optional.of(defaultMockBoard));
//        BoardDtoResponse returnedBoardDtoResponse = service.findById(1L);
//
//        assertNotNull(returnedBoardDtoResponse.getId());
//        verify(repository, times(1)).findById(anyLong());
//    }
//
//    @Test
//    public void findByIdWithNoReturn() {
//        when(repository.findById(anyLong())).thenReturn(Optional.empty());
//
//        assertNull(service.findById(1L).getId());
//        verify(repository, times(1)).findById(anyLong());
//    }
//
//    @Test
//    public void createBoard() {
//        when(repository.save(any(Board.class))).thenReturn(defaultMockBoard);
//
//        BoardDtoResponse boardDtoResponse = service.create(defaultMockBoard);
//
//        assertNotNull(boardDtoResponse);
//        verify(repository, times(1)).save(any(Board.class));
//    }
//
//    @Test
//    public void updateBoard() {
//        when(repository.save(any(Board.class))).thenReturn(defaultMockBoard);
//
//        BoardDtoResponse boardDtoResponse = service.update(defaultMockBoard);
//
//        assertNotNull(boardDtoResponse);
//        verify(repository, times(1)).save(any(Board.class));
//    }
//
//    @Test
//    public void remove() {
//        service.remove(defaultMockBoard);
//        verify(repository, times(1)).delete(any(Board.class));
//    }
//}