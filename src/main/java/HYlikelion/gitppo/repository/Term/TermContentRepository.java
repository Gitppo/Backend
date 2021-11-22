package HYlikelion.gitppo.repository.Term;

import HYlikelion.gitppo.domain.Term.Term;
import HYlikelion.gitppo.domain.Term.TermContent;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TermContentRepository extends JpaRepository<TermContent, Long> {
    List<TermContent> findAllByTerm(Term term);
}
