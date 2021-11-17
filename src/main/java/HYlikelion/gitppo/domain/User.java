package HYlikelion.gitppo.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

@Entity
@Table(name="user")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) // 이 id 값 생성을 db에 넘김 (auto: mysql인 경우에만)
    private long usr_id;

    @Column(nullable = false)
    private String usr_token;

    @Column(length=10, nullable = false)
    private String usr_name;

    @Column(nullable = false)
    private LocalDate usr_birth;

    @Column(length=20, nullable = false)
    private String usr_phone;

    @Column(nullable = false)
    private LocalDate usr_join_date;

    private LocalDateTime usr_login_date;
}
