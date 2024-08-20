package com.team8.teamproject.board.repository;

import com.team8.teamproject.board.entity.Board;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class BoardRepository {

    private EntityManager em;

    public void save(Board board) {
        em.persist(board);
    }

    public Board findOne(Long id) {
        return em.find(Board.class, id);
    }

    public List<Board> findAll() {
        return em.createQuery("select b from Board b", Board.class)
                .getResultList();
    }

    public void deleteOne(Long id) {
        em.createQuery("delete from Board b where b.id = :id", Board.class)
                .setParameter("id", id);
    }

}
