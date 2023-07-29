package clubhouse.clubhouse.domain.member.controller;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import clubhouse.clubhouse.domain.member.entity.Member;
import clubhouse.clubhouse.domain.member.entity.MemberJoinRequest;
import clubhouse.clubhouse.domain.member.repository.MemberRepository;

@SpringBootTest
@AutoConfigureMockMvc
class MemberControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private MemberRepository memberRepository;

	@Test
	@DisplayName("회원가입 성공")
	void test1() throws Exception {

		String email = "dohyung@clubhouse.com";
		String password = "1q2w3e4r";

		mockMvc.perform(MockMvcRequestBuilders.post("/v1/users/join")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(new MemberJoinRequest(email, password))))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andDo(MockMvcResultHandlers.print());
	}

	@Test
	@DisplayName("회원가입 실패")
	void test2() throws Exception {
		String email = "dohyung@clubhouse.com";
		String password = "1q2w3e4r";

		Member member1 = Member.builder()
				.email(email)
					.password(password)
						.build();

		memberRepository.save(member1);

		mockMvc.perform(MockMvcRequestBuilders.post("/v1/users/join")
			.contentType(MediaType.APPLICATION_JSON)
			.content(objectMapper.writeValueAsString(new MemberJoinRequest(email, password))))
			.andExpect(MockMvcResultMatchers.status().isConflict())
			.andDo(MockMvcResultHandlers.print());


	}

}