package HYlikelion.gitppo.service;

import HYlikelion.gitppo.domain.Test;
import HYlikelion.gitppo.domain.User;
import HYlikelion.gitppo.dto.UserDTO;
import HYlikelion.gitppo.repository.UserRepository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import javassist.NotFoundException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    @NonNull
    private final UserRepository userRepository;

    // 기존에 만든 유저인지 확인
    public boolean checkUser(String id) {
        return userRepository.existsById(Long.parseLong(id));
    }

    public User findUser(String id) throws NotFoundException {
        return userRepository.findById(Long.parseLong(id)).orElseThrow(() -> new NotFoundException("Work space with id : " + id + "is not valid"));
    }

    public User saveUser(String accessToken, String id) {
        User user = User.builder()
            .usr_id(Long.parseLong(id))
            .userToken(accessToken)
            .usr_name("홍길동")
            .usr_birth(LocalDate.now())
            .usr_phone("010-1111-1111")
            .usr_login_date(LocalDateTime.now())
            .usr_join_date(LocalDate.now())
            .build();
        userRepository.save(user);
        return user;
    }

    public User saveUserInfo(UserDTO.Post dto) throws NotFoundException {
        User user =  userRepository.findById(dto.getUsr_id()).orElseThrow(() -> new NotFoundException("Cannot find user"));
        User realUser = User.builder()
            .usr_id(user.getUsr_id())
            .userToken(user.getUserToken())
            .usr_name(dto.getUsr_name())
            .usr_birth(dto.getUsr_birth())
            .usr_phone(dto.getUsr_phone())
            .usr_login_date(LocalDateTime.now())
            .usr_join_date(LocalDate.now())
            .build();
        userRepository.save(realUser);
        return user;
    }

}
