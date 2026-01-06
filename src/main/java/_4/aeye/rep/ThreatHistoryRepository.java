package _4.aeye.rep;

import _4.aeye.entites.ThreatHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ThreatHistoryRepository extends JpaRepository<ThreatHistory, Long> {
    List<ThreatHistory> findAllByOrderByTimestampDesc();
}
