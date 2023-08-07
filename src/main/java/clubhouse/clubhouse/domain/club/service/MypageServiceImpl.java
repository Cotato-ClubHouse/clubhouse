package clubhouse.clubhouse.domain.club.service;

import clubhouse.clubhouse.domain.club.dto.*;
import clubhouse.clubhouse.domain.club.entity.Club;
import clubhouse.clubhouse.domain.club.repository.ClubRepository;
import clubhouse.clubhouse.domain.form.entity.Form;
import clubhouse.clubhouse.domain.form.repository.FormRepository;
import clubhouse.clubhouse.domain.member.entity.Member;
import clubhouse.clubhouse.domain.member.repository.MemberClubRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MypageServiceImpl {

    private final ClubRepository clubRepository;
    private final MemberClubRepository memberClubRepository;
    private final FormRepository formRepository;

    public ClubListDto getClubList() {
        List<Club> clubs = clubRepository.findAllByAdminId("email123@gmail.com");
        List<ClubListForm> clubList = new ArrayList<>();
        for (Club c : clubs) {
            clubList.add(new ClubListForm(c.getId(), c.getName()));
        }
        return new ClubListDto(clubList);
    }

    public ClubInfoDto getClubMembers(Long clubId) {
        Club club = clubRepository.findById(clubId)
                .orElseThrow(()-> new RuntimeException("RuntimeException"));
        List<Form> forms = formRepository.findAllByClub(club);
        List<ClubFormsForm> formList = new ArrayList<>();
        for (Form f : forms) {
            formList.add(new ClubFormsForm(f.getPhotoUrl(), f.getTitle(), f.getFormStatus()));
        }
        List<Member> members = memberClubRepository.findAllByClub(club);
        List<ClubMembersForm> memberList = new ArrayList<>();
        for (Member m : members) {
            memberList.add(new ClubMembersForm(m.getName(), m.getAge(), m.getUniv(), m.getMajor()));
        }
        return new ClubInfoDto(formList, memberList);
    }
}
