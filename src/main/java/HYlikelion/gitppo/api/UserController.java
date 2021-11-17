package HYlikelion.gitppo.api;

import HYlikelion.gitppo.domain.User;
import HYlikelion.gitppo.repository.UserRepository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserRepository userRepository;

    @PostMapping("/join")
    public String join(@RequestBody User user) {
        user.setUsr_login_date(LocalDateTime.now());
        User newUser = userRepository.save(user);
        return user.getUsr_name() + "님의 회원가입을 축하합니다.";
    }
}
