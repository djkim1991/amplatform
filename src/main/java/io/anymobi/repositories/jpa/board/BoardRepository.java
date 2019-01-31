package io.anymobi.repositories.jpa.board;

import io.anymobi.domain.entity.board.Board;
import io.anymobi.domain.entity.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by KimYJ on 2017-07-12.
 */
@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {
    Board findByUser(User user);
}