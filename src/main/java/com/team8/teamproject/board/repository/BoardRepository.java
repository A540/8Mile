package com.team8.teamproject.board.repository;

import com.team8.teamproject.board.domain.Board;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class BoardRepository {

    private final EntityManager em;

    public void save(Board board) {
        em.persist(board);
    }

    public Optional<Board> findOne(Long id) {
        Board board  = em.find(Board.class, id);
        return Optional.ofNullable(board);
    }

    public List<Board> findAll() {
        return em.createQuery("select b from Board b", Board.class)
                .getResultList();
    }

    public List<Board> findALlByIsDeletedFalse() {
        return em.createQuery("select b from Board b where b.isDeleted = false ", Board.class)
                .getResultList();
    }

    public void deleteOne(Board board) {
        em.remove(board);
    }

}
