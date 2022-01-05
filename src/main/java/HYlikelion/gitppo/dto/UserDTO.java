package HYlikelion.gitppo.dto;

import HYlikelion.gitppo.domain.GithubProfile;
import HYlikelion.gitppo.domain.User;
import java.time.LocalDate;
import java.util.List;
import lombok.Data;

public class UserDTO {

    @Data
    public static class Post {

        private Long usr_id;
        private String usr_token;
        private String usr_name;
        private LocalDate usr_birth;
        private String usr_phone;
    }

    @Data
    public static class GetResult {

        private StatusEnum status;
        private String message;
        private User data;

        public GetResult() {
            this.status = StatusEnum.BAD_REQUEST;
            this.data = null;
            this.message = null;
        }
    }
}