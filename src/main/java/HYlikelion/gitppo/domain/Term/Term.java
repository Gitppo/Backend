package HYlikelion.gitppo.domain.Term;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@RequiredArgsConstructor
@NoArgsConstructor
@Schema(description = "이용약관")
public class Term {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID")
    private Long term_id;

    @NonNull
    @Schema(description = "약관 제목")
    private String term_title;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "term_content_id")
    @Schema(description = "약관 내용")
    @Setter
    private List<TermContent> contents;

    @Column(nullable = false, columnDefinition = "tinyint default 0")
    @Schema(description = "약관 필수 여부", defaultValue = "0")
    boolean term_required = true;
}
