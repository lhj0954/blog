package com.tastyNut.blog2.service;

import com.tastyNut.blog2.dto.ReplySaveRequestDto;
import com.tastyNut.blog2.model.Board;
import com.tastyNut.blog2.model.Reply;
import com.tastyNut.blog2.model.User;
import com.tastyNut.blog2.repository.BoardRepository;
import com.tastyNut.blog2.repository.ReplyRepository;
import com.tastyNut.blog2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private ReplyRepository replyRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public void 글쓰기(Board board, User user) {
        board.setCount(0);
        board.setUser(user);
        boardRepository.save(board);
    }

    @Transactional(readOnly = true)
    public Page<Board> 글목록(Pageable pageable){
        return boardRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Board 내용보기(int id) {
        return boardRepository.findById(id)
                .orElseThrow(()->{
                    return new IllegalArgumentException("이미 삭제된 게시글 입니다.");
                });
    }

    @Transactional
    public void 삭제하기(int id) {
        boardRepository.deleteById(id);
    }

    @Transactional
    public void 글수정하기(int id, Board requestBoard) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> {
                    return new IllegalArgumentException("글찾기 실패");
                });
        board.setTitle(requestBoard.getTitle());
        board.setContent(requestBoard.getContent());

    }

    @Transactional
    public void 댓글쓰기(ReplySaveRequestDto replySaveRequestDto) {

        User user = userRepository.findById(replySaveRequestDto.getUserId())
                .orElseThrow(() -> {
                    return new IllegalArgumentException("유저찾기 실패");
                });

        Board board = boardRepository.findById(replySaveRequestDto.getBoardId())
                .orElseThrow(() -> {
                    return new IllegalArgumentException("보드찾기 실패");
                });

        Reply reply = Reply.builder()
                .user(user)
                .board(board)
                .content(replySaveRequestDto.getContent())
                .build();

        replyRepository.save(reply);
    }

    @Transactional
    public void 댓글삭제(int replyId) {
        replyRepository.deleteById(replyId);
    }
}