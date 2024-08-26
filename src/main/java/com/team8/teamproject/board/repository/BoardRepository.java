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

    //최신순 정렬
    public List<Board> findAllByOrderByLatest() {
        return em.createQuery("select b from Board b where b.isDeleted = false order by b.createdAt desc", Board.class)
                .getResultList();
    }

    //인기순 정렬
    public List<Board> findAllByOrderByPopular() {
        return em.createQuery("select b from Board b where b.isDeleted = false order by b.viewCount desc", Board.class)
                .getResultList();
    }



}
