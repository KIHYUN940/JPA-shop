package jpabook.jpashop.service;

import jakarta.persistence.EntityManager;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class) //JUit 실행할 때 스프링과 엮어서 실행
@SpringBootTest //스프링 부트를 띄운 상태로 테스트 / 스프링컨테이너 안에서 테스트 돌림
@Transactional //테스트 케이스에서 @Transactional을 사용하면 기본값이 롤백
public class MemberServiceTest {

    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;

    @Test
//    @Rollback(value = false)
    public void 회원가입() throws Exception {
        //given
        Member member = new Member();
        member.setName("kim");

        //when
        Long savedId = memberService.join(member);

        //then
        Assert.assertEquals(member, memberRepository.findOne(savedId));
    }

    @Test(expected = IllegalStateException.class)
    public void 중복_회원_예외() throws Exception {
        //given
        Member member1 = new Member();
        member1.setName("kim");

        Member member2 = new Member();
        member2.setName("kim");

        //when
        memberService.join(member1);
        memberService.join(member2);  // 예외가 발생해야 함

        //then
        Assert.fail("예외가 발생해야 한다.");
    }
}

/**
스프링 부트를 쓰면 메모리 DB를 사용할 수 있음
 */