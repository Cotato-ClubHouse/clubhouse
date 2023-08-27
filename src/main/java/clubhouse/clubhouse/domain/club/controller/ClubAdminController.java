package clubhouse.clubhouse.domain.club.controller;

import clubhouse.clubhouse.domain.club.dto.ClubInfoDto;
import clubhouse.clubhouse.domain.club.service.MypageServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/mypage")
public class ClubAdminController {
    private final MypageServiceImpl mypageService;

    @GetMapping("/{clubId}")
    public ResponseEntity<ClubInfoDto> getClubInfo(@PathVariable long clubId){
        return ResponseEntity.ok(mypageService.getClubInfo(clubId));
    }





}
