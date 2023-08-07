package clubhouse.clubhouse.domain.club.controller;

import clubhouse.clubhouse.domain.club.entity.CategoryName;
import clubhouse.clubhouse.domain.club.service.ClubServiceImpl;
import clubhouse.clubhouse.domain.club.dto.ClubRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/clubs")
public class ClubController {
    private final ClubServiceImpl clubService;

    @PostMapping("/club")
    public ResponseEntity<String> saveClub(@RequestBody ClubRequestDto clubRequestDto) {
        clubService.saveClub(clubRequestDto);
        return ResponseEntity.ok("Club saved");
    }

    @DeleteMapping("/{clubId}")
    public ResponseEntity<String> deleteClub(@PathVariable long clubId) {
        clubService.deleteClub(clubId);
        return ResponseEntity.ok("Club deleted");
    }
}
