package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true) //JPA의 모든 데이터 변경, 로직은 가급적이면 트랜잭션 안에서 실행되어야 한다. /readOnly=true를 주면 JPA가 조회하는 곳에서 성능을 최적화 시켜줌 - 쓰기에선 사용X
@RequiredArgsConstructor //final이 있는 필드만 가지고 생성자를 만들어 준다. -> 생성자 인젝션 - 생성자 부분 생략 가능
public class MemberService {

//    @Autowired // 필드 인젝션
//    private MemberRepository memberRepository;

//    @Autowired // setter 인젝션 / 테스트 코드 작성을 할 떄 mock 같은 걸 주입해 줄 수 있음 / 필드 인젝션은 주입하기가 까다로움 / 단점은 중간에 set해서 변경될 우려가 있다.
//    public void setMemberRepository(MemberRepository memberRepository) {
//        this.memberRepository = memberRepository;
//    }

    private final MemberRepository memberRepository; //최초 할당할 때 값 체크하기 용이하다. /최초 할당 후 변경할 일이 없기 때문에 final 넣는걸 권장

//    @Autowired //생성자가 하나일 경우 생략 가능
//    public MemberService(MemberRepository memberRepository) {
//        this.memberRepository = memberRepository;
//    }

    //회원 가입
    @Transactional //이렇게 따로 @Transactional을 설정하면 우선권을 갖는다. / 기본 값은 readOnly=false
    public Long join(Member member) {
        validateDuplicateMember(member); //중복 회원 검증
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        //Exception - 이 로직은 단순한 로직으로 동시에 WAS를 둬서 가입 시 동시성 문제가 생길 수 있음 /데이터베이스에 있는 member의 name을 유니크 제약조건으로 잡아야함
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    //회원 전체 조회
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    //회원 단일 조회
    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }
}
