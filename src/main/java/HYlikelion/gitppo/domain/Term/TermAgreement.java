package HYlikelion.gitppo.domain.Term;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Entity
@Getter
@RequiredArgsConstructor
@NoArgsConstructor
@Schema(description = "이용약관 동의")
public class TermAgreement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID")
    private Long term_ag_id;

//    유저는 아직 안만들어져서 일단 없는 상태로 해놓음.
//    @OneToOne
//    @NonNull
//    private User user_id;

    @OneToOne
    @NonNull
    @JoinColumn(name = "term_id")
    @Schema(description = "이용약관 ID")
    private Term term;

    @NonNull
    @Schema(description = "이용약관 동의일")
    private LocalDateTime term_ag_date;

    @NonNull
    @Column(nullable = false, columnDefinition = "tinyint default 1")
    @Schema(description = "약관 동의 여부")
    private Boolean term_ag_is_agree;
}
