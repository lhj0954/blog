package com.tastyNut.blog2.repository;

import com.tastyNut.blog2.model.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface BoardRepository extends JpaRepository <Board, Integer> {
}
