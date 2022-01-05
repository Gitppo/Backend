package HYlikelion.gitppo.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    private long usr_id;

    @Column(nullable = false)
    private String userToken;

    @Column(length = 10, nullable = false)
    private String usr_name;

    @Column(nullable = false)
    private LocalDate usr_birth;

    @Column(length = 20, nullable = false)
    private String usr_phone;

    @Column(nullable = false)
    private LocalDate usr_join_date;

    private LocalDateTime usr_login_date;
}
