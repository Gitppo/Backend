package HYlikelion.gitppo.dto;

import lombok.Data;

@Data
public class TestDTO {
    private StatusEnum status;
    private String message;
    private Object data;

    public TestDTO() {
        this.status = StatusEnum.BAD_REQUEST;
        this.data = null;
        this.message = null;
    }
}