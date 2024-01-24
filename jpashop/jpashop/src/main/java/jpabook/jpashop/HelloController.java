package jpabook.jpashop;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {

    @GetMapping("hello")
    public String hello(Model model) {  //여기서 Model은 data를 View로 넘길 수 있음
        model.addAttribute("data", "hello!!!"); // 키 : data / 값 : hello!!!
        return "hello"; // 화면 이름
    }
}
