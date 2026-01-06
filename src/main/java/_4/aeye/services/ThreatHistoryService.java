package _4.aeye.services;

import _4.aeye.entites.ThreatHistory;
import _4.aeye.rep.ThreatHistoryRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ThreatHistoryService {

    private final ThreatHistoryRepository threatHistoryRepository;

    public ThreatHistoryService(ThreatHistoryRepository threatHistoryRepository) {
        this.threatHistoryRepository = threatHistoryRepository;
    }

    /**
     * Record a new threat occurrence
     * 
     * @param cameraName Name of the camera that detected the threat
     */
    public void recordThreat(String cameraName) {
        ThreatHistory threat = new ThreatHistory(cameraName, LocalDateTime.now());
        threatHistoryRepository.save(threat);
    }

    /**
     * Get all threat history ordered by timestamp (newest first)
     * 
     * @return List of all threat history records
     */
    public List<ThreatHistory> getAllThreats() {
        return threatHistoryRepository.findAllByOrderByTimestampDesc();
    }
}
