package _4.aeye.rest;

import _4.aeye.dtos.ThreatHistoryDto;
import _4.aeye.entites.ThreatHistory;
import _4.aeye.services.ThreatHistoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/threats")
public class ThreatHistoryController {

    private final ThreatHistoryService threatHistoryService;

    public ThreatHistoryController(ThreatHistoryService threatHistoryService) {
        this.threatHistoryService = threatHistoryService;
    }

    /**
     * Get all threat history (requires ADMIN or MANAGER role)
     * 
     * @return List of threats with camera name and timestamp
     */
    @GetMapping("/history")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<List<ThreatHistoryDto>> getThreatHistory() {
        List<ThreatHistory> threats = threatHistoryService.getAllThreats();
        List<ThreatHistoryDto> threatDtos = threats.stream()
                .map(threat -> new ThreatHistoryDto(threat.getCameraName(), threat.getTimestamp()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(threatDtos);
    }

    /**
     * Record a new threat (can be called by your threat detection system)
     * 
     * @param cameraName Name of the camera that detected the threat
     */
    @PostMapping("/record")
    public ResponseEntity<Void> recordThreat(@RequestParam String cameraName) {
        threatHistoryService.recordThreat(cameraName);
        return ResponseEntity.ok().build();
    }
}
