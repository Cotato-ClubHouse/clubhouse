package clubhouse.clubhouse.domain.club.service;

import clubhouse.clubhouse.domain.club.dto.*;
import clubhouse.clubhouse.domain.club.entity.Club;
import clubhouse.clubhouse.domain.club.repository.ClubRepository;
import clubhouse.clubhouse.domain.form.entity.Form;
import clubhouse.clubhouse.domain.form.repository.FormRepository;
import clubhouse.clubhouse.domain.member.entity.Member;
import clubhouse.clubhouse.domain.member.entity.MemberClub;
import clubhouse.clubhouse.domain.member.repository.MemberClubRepository;
import clubhouse.clubhouse.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MypageServiceImpl implements MypageService{

    private final ClubRepository clubRepository;
    private final MemberClubRepository memberClubRepository;
    private final FormRepository formRepository;
//    private final MemberRepository memberRepository;

    public ClubListDto getClubList(String email) {
        List<Club> clubs = clubRepository.findAllByAdminId(email);
        List<ClubListForm> clubList = new ArrayList<>();
        for (Club c : clubs) {
            clubList.add(new ClubListForm(c.getId(), c.getName()));
        }
        return new ClubListDto(clubList);
    }

    public ClubInfoDto getClubInfo(Long clubId) {
        Club club = clubRepository.findById(clubId)
                .orElseThrow(()-> new RuntimeException("RuntimeException"));
        List<Form> forms = formRepository.findAllByClub(club);
        List<ClubFormsForm> formList = new ArrayList<>();
        for (Form f : forms) {
            formList.add(new ClubFormsForm(f.getPhotoUrl(), f.getTitle(), f.getFormStatus()));
        }
        List<Member> members = new ArrayList<>();
        List<MemberClub> memberClub = memberClubRepository.findAllByClub(club);
        for(MemberClub m : memberClub){
            members.add(m.getMember());
        }
        List<ClubMembersForm> memberList = new ArrayList<>();
        for (Member m : members) {
            memberList.add(new ClubMembersForm(m.getName(), (long) m.getAge(m.getBirthDate()), m.getUniv(), m.getMajor()));
        }
        return new ClubInfoDto(formList, memberList);
    }
}
