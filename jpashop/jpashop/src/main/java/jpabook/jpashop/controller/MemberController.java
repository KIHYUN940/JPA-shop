package jpabook.jpashop.controller;

import jakarta.validation.Valid;
import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.service.MemberService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/members/new")
    public String createForm(Model model) {
        model.addAttribute("memberForm", new MemberForm());
        return "members/createMemberForm";
    }

    @PostMapping("/members/new")
    public String create(@Valid MemberForm form, BindingResult result) {  // @Valid 뒤에 BindingResult를 쓰면 에러가 나도 form에 data가 담겨서 코드가 실행된다.

        if (result.hasErrors()) {
            return "members/createMemberForm";
        }

        Address address = new Address(form.getCity(), form.getStreet(), form.getZipcode());

        Member member = new Member();
        member.setName(form.getName());
        member.setAddress(address);

        memberService.join(member);
        return "redirect:/";
    }


    @GetMapping("/members")
    public String list(Model model) {  //모델이라는 객체를 통해 화면에 데이터를 전달
        List<Member> members = memberService.findMembers();
        /*
        여기서는 엔티티로 반환하지만 API로 만들 때는 절대 엔티티를 외부로 반환하면 안된다. - 템플릿 엔진에서는 사용해도 괜찮다. 서버 사이드로 돌리기 때문.
        / 엔티티 로직을 추가할 시 API 스펙도 변하고 패스워드 같은 정보 노출이 있을 수 있음 -> DTO를 사용 */
        model.addAttribute("members", members);  // 인라인 단축키 ctrl + alt + N
        return "members/memberList";
    }
}
