package io.anymobi.repositories.jpa;

/**
 * Package : io.anymobi.repositories.jpa
 * Developer Team : Anymobi System Development Division
 * Date : 2019-01-17
 * Time : 오전 12:58
 * Created by leaven
 * Github : http://github.com/onjsdnjs
 */
import io.anymobi.domain.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Integer> {
}

