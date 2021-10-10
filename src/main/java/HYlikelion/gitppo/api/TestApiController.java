package HYlikelion.gitppo.api;

import HYlikelion.gitppo.domain.Test;
import HYlikelion.gitppo.dto.StatusEnum;
import HYlikelion.gitppo.dto.TestDTO;
import HYlikelion.gitppo.service.TestService;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TestApiController {

    private final TestService testService;

    @GetMapping("/user/{id}")
    public ResponseEntity<TestDTO> findByid(@PathVariable Long id) {
        Test test = new Test("hi");
        testService.saveTest(test);

        TestDTO dto = new TestDTO();

        HttpHeaders header = new HttpHeaders();
        header.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));

        dto.setStatus(StatusEnum.OK);
        dto.setData(test);
        dto.setMessage("고범석 찾기 성공");

        return new ResponseEntity<>(dto, header, HttpStatus.OK);
    }

}
