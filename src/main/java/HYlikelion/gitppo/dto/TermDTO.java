package HYlikelion.gitppo.dto;

import HYlikelion.gitppo.domain.Term.Term;
import java.util.List;
import lombok.Data;

public class TermDTO {

    @Data
    public static class Post {

        private Long term_id;
        //        private Long user_id;
        private Boolean term_ag_is_agree;
    }

    @Data
    public static class GetResult {

        private StatusEnum status;
        private String message;
        private List<Term> data;

        public GetResult() {
            this.status = StatusEnum.BAD_REQUEST;
            this.data = null;
            this.message = null;
        }
    }


}

