package clubhouse.clubhouse.domain.member.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;

import clubhouse.clubhouse.domain.member.entity.Gender;
import clubhouse.clubhouse.domain.member.entity.Member;
import clubhouse.clubhouse.domain.member.entity.MemberJoinRequest;
import clubhouse.clubhouse.domain.member.entity.MemberLoginRequest;
import clubhouse.clubhouse.domain.member.repository.MemberRepository;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class MemberControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private BCryptPasswordEncoder encoder;

	@BeforeEach
	void clear(){
		memberRepository.deleteAll();
	}

	@Test
	@DisplayName("회원가입 성공")
	void join_success() throws Exception {

		String email = "dohyung@clubhouse.com";
		String password = "1q2w3e4r";

		Member member = Member.builder()
			.email(email)
			.password(password)
			.build();


		mockMvc.perform(MockMvcRequestBuilders.post("/v1/users/join")
				.with(csrf())
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(member)))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andDo(MockMvcResultHandlers.print());
	}

	@Test
	@DisplayName("회원가입 중복")
	void join_fail() throws Exception {
		String email = "dohyung@clubhouse.com";
		String password = "1q2w3e4r";

		Member member1 = Member.builder()
				.email(email)
				.password(password)
				.build();

		memberRepository.save(member1);

		MemberJoinRequest memberJoinRequest = MemberJoinRequest.builder()
			.email(email)
			.password(password)
			.birthDate(LocalDate.parse("2000-01-01"))
			.gender(Gender.valueOf("MALE"))
			.name("김도형")
			.phone("01012345678")
			.univ("숭실대학교")
			.build();

		mockMvc.perform(MockMvcRequestBuilders.post("/v1/users/join")
				.with(csrf())
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(memberJoinRequest))
			)
			.andExpect(MockMvcResultMatchers.status().isConflict())
			.andDo(MockMvcResultHandlers.print());
	}

	@Test
	@DisplayName("로그인 성공")
	@WithMockUser
	void login_success() throws Exception{
		String email = "dohyung@clubhouse.com";
		String password = encoder.encode("1q2w3e4r");

		Member member = Member.builder()
			.email(email)
			.password(password)
			.build();

		memberRepository.save(member);

		mockMvc.perform(MockMvcRequestBuilders.post("/v1/users/login")
				.with(csrf())
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(new MemberLoginRequest(email, "1q2w3e4r")))
			)
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andDo(MockMvcResultHandlers.print());
	}

	@Test
	@DisplayName("로그인 실패 - 일치하는 email 없음")
	@WithAnonymousUser
	void login_fail1() throws Exception{

		String email = "dohyung@clubhouse.com";
		String password = encoder.encode("1q2w3e4r");

		MemberJoinRequest memberJoinRequest = MemberJoinRequest.builder()
			.email(email)
			.password(password)
			.birthDate(LocalDate.parse("2000-01-01"))
			.gender(Gender.valueOf("MALE"))
			.name("김도형")
			.phone("01012345678")
			.univ("숭실대학교")
			.build();

		mockMvc.perform(MockMvcRequestBuilders.post("/v1/users/login")
				.with(csrf())
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(memberJoinRequest)))
			.andExpect(MockMvcResultMatchers.status().isNotFound())
			.andDo(MockMvcResultHandlers.print());
	}

	@Test
	@DisplayName("로그인 실패 - 비밀번호 틀림")
	@WithMockUser
	void login_fail2() throws Exception{
		String email = "dohyung@clubhouse.com";
		String password = encoder.encode("1q2w3e4r");

		Member member1 = Member.builder()
			.email(email)
			.password(password)
			.build();

		memberRepository.save(member1);

		Member member2 = Member.builder()
				.email(email)
				.password("1234")
				.build();

		mockMvc.perform(MockMvcRequestBuilders.post("/v1/users/login")
				.with(csrf())
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(member2))
			)
			.andExpect(MockMvcResultMatchers.status().isUnauthorized())
			.andDo(MockMvcResultHandlers.print());
	}

}