package HYlikelion.gitppo.api;

import HYlikelion.gitppo.domain.GithubProfile;
import HYlikelion.gitppo.domain.OAuthToken;
import HYlikelion.gitppo.domain.User;
import HYlikelion.gitppo.dto.StatusEnum;
import HYlikelion.gitppo.dto.UserDTO;
import HYlikelion.gitppo.dto.UserDTO.GetResult;
import HYlikelion.gitppo.repository.UserRepository;
import HYlikelion.gitppo.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
//@RequestMapping("/api/user")
public class UserController {

    private final String REDIRECT_URL = "http://localhost:8080/login/oauth2/code/github";
    private final String TOKEN_REQUEST_URL = "https://github.com/login/oauth/access_token";
    private final String PROFILE_REQUEST_URL = "https://api.github.com/user";

    @Autowired
    UserService userService;

    @PostMapping("/join")
    public ResponseEntity<UserDTO.GetResult> join(@RequestBody UserDTO.Post dto)
        throws NotFoundException {
        User user = userService.saveUserInfo(dto);
        HttpHeaders header = new HttpHeaders();
        UserDTO.GetResult resultDTO = new GetResult();

        resultDTO.setStatus(StatusEnum.OK);
        resultDTO.setData(user);

        return new ResponseEntity<>(resultDTO, header, HttpStatus.OK);
    }

    @GetMapping("/auth/github/callback")
    public ResponseEntity<UserDTO.GetResult> getToken(@RequestParam String code)
        throws IOException, NotFoundException {
        OAuthToken oAuthToken = getOAuthToken(code);
//        프로필 필요하면 추가.
        GithubProfile githubProfile = getGithubProfile(oAuthToken);
        System.out.println(githubProfile.toString());
        HttpHeaders header = new HttpHeaders();
        UserDTO.GetResult dto = new GetResult();
        User user;

        if (userService.checkUser(githubProfile.getId())) {
            dto.setMessage("기존 유저");
            // token 값 변경해서 save
            user = userService.findUser(githubProfile.getId());
        } else {
            dto.setMessage("유저 정보 추가 입력 필요");
            //토큰, id값을 프론트에
            user = userService.saveUser(oAuthToken.getAccessToken(), githubProfile.getId());
        }

        dto.setStatus(StatusEnum.OK);
        dto.setData(user);

        return new ResponseEntity<>(dto, header, HttpStatus.OK);
    }


    private OAuthToken getOAuthToken(String code) throws JsonProcessingException {
        HttpEntity<MultiValueMap<String, String>> codeRequestHttpEntity = getCodeRequestHttpEntity(
            code);
        RestTemplate tokenRequestTemplate = new RestTemplate();
        ResponseEntity<String> response = tokenRequestTemplate.exchange(TOKEN_REQUEST_URL,
            HttpMethod.POST,
            getCodeRequestHttpEntity(code),
            String.class);

        ObjectMapper objectMapper = new ObjectMapper();
        OAuthToken oAuthToken = null;
        return objectMapper.readValue(response.getBody(), OAuthToken.class);
    }

    private GithubProfile getGithubProfile(OAuthToken oAuthToken) throws JsonProcessingException {
        RestTemplate profileRequestTemplate = new RestTemplate();
        ResponseEntity<String> profileResponse = profileRequestTemplate.exchange(
            PROFILE_REQUEST_URL,
            HttpMethod.GET,
            getProfileRequestEntity(oAuthToken),
            String.class
        );

        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(profileResponse.getBody(), GithubProfile.class);
    }

    private HttpEntity<MultiValueMap<String, String>> getProfileRequestEntity(
        OAuthToken oAuthToken) {
        HttpHeaders infoRequestHeaders = new HttpHeaders();
        infoRequestHeaders.add("Authorization", "token " + oAuthToken.getAccessToken());
        return new HttpEntity<>(infoRequestHeaders);
    }

    private HttpEntity<MultiValueMap<String, String>> getCodeRequestHttpEntity(String code) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        // 주의!
        params.add("client_id", "d43eb25dda6b0eaa61bf");
        params.add("client_secret", "d138bd7d3cde4b7fecbca377ea3588e05beb8a56");
        params.add("code", code);
        params.add("redirect_url", REDIRECT_URL);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept", "application/json");
        return new HttpEntity<>(params, headers);
    }
}