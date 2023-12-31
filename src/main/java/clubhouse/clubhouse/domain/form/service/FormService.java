package clubhouse.clubhouse.domain.form.service;


import clubhouse.clubhouse.domain.form.dto.*;
import clubhouse.clubhouse.domain.form.entity.Form;
import clubhouse.clubhouse.domain.form.entity.Question;
import clubhouse.clubhouse.domain.form.exception.ImageException;


import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface FormService {

    //일단 클럽id로 생성하게 설정 추후에
    ResponseForm createForm(RequestFormDto formDto) throws ImageException;

    /**
     * id로 하나 찾기
     * @param formId
     * @return
     */
    Optional<Form> findById(Long formId);
    /**
     * 해당 공고에 등록된 모든 질문을 가져옴
     * @param formId
     * @return
     */
    List<Question> findAllQuestions(Long formId);

    /**
     * 메인 페이지
     * 모든 공고의 리스트 불러오기
     * 불러올 정보: 카테고리, 사진, 모집 마감일(계산), 동아리 이름, 홍보글 초안
     *  ToDO 모집 마감일 순으로 정렬해서 보내기 DO
     */
    List<ResponseAllForm> getAllFormInfo();


    /**
     * 공고 상세 조회
     * 불러올 정보: 동아리 이름, 공고 제목, 공고 내용(글), 공고 사진(S3), 마감일, 마감까지 남은 기한
     * Todo 마감일까지 남은 기한 -> 프론트에서 실시간으로? or url 새로고침할때마다 계산
     */
    ResponseFormDetails getFormDetails(Long formId);


    /**
     * 공고 삭제
     * 반환 -> 삭제된 공고 정보?
     */
    void deleteForm(Long formId);

    /**
     * 공고 수정
     * patch-> 원하는 항목 수정
     * Todo 제목, 홍보글, 마감일, 사진 개별 수정할 수 있게!
     */
    ResponsePatchForm patchForm(Long formId,RequestPatchForm requestPatchForm);

    /**
     * 공고에 필요한 질문 추가?
     * 넣지말자~
     */
}
