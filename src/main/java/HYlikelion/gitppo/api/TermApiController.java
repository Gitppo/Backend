package HYlikelion.gitppo.api;

import HYlikelion.gitppo.domain.Term.Term;
import HYlikelion.gitppo.domain.Term.TermAgreement;
import HYlikelion.gitppo.dto.StatusEnum;
import HYlikelion.gitppo.dto.TermDTO;
import HYlikelion.gitppo.service.TermService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import java.util.List;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/term")
//@Tag(name = "terms", description = "이용약관 API")
public class TermApiController {

    private final TermService termService;

    @Operation(summary = "이용약관 확인", description = "이용약관을 가져옵니다.")
    @ApiResponses({
        @ApiResponse(code = 200, message = "", response = TermDTO.class),
        @ApiResponse(code = 400, message = "error")
    })
    @GetMapping("/signup")
    public ResponseEntity<TermDTO.GetResult> getTerms() throws NotFoundException {
        List<Term> terms = termService.findTerms();

        HttpHeaders header = new HttpHeaders();

        TermDTO.GetResult dto = new TermDTO.GetResult();
        dto.setStatus(StatusEnum.OK);
        dto.setData(terms);

        return new ResponseEntity<>(dto, header, HttpStatus.OK);
    }

    @Operation(summary = "이용약관 동의 여부 저장", description = "이용약관 동의 여부를 저장합니다.")
    @ApiResponses({
        @ApiResponse(code = 200, message = ""),
        @ApiResponse(code = 400, message = "error")
    })
    @PostMapping("/signup")
    public ResponseEntity<Boolean> saveAgreements(
        @Parameter @RequestBody List<TermDTO.Post> agreements)
        throws Exception {
        List<TermAgreement> save = termService.saveTermAgreement(agreements);

        if (save.size() == 0) {
            return ResponseEntity.badRequest().build();
        }

        return new ResponseEntity<>(true, HttpStatus.OK);
    }

}
