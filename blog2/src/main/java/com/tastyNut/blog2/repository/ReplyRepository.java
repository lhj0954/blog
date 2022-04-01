package com.tastyNut.blog2.repository;

import com.tastyNut.blog2.model.Reply;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReplyRepository extends JpaRepository<Reply, Integer> {
}
