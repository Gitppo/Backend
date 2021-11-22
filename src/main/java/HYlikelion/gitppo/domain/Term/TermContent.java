package HYlikelion.gitppo.domain.Term;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Entity
@RequiredArgsConstructor
@NoArgsConstructor
@Schema(description = "이용약관")
public class TermContent {

    @Id
    @Getter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID")
    private Long term_content_id;

    @NonNull
    @Getter
    @Schema(description = "약관 내용 제목")
    private String term_content_title;

    @NonNull
    @Getter
    @Schema(description = "약관 내용")
    private String term_content_contents;

    @NonNull
    @ManyToOne(targetEntity = Term.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "term_id")
    @Schema(description = "약관 내용")
    private Term term;
}
