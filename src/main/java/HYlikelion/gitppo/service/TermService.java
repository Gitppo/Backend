package HYlikelion.gitppo.service;

import HYlikelion.gitppo.domain.Term.Term;
import HYlikelion.gitppo.domain.Term.TermAgreement;
import HYlikelion.gitppo.dto.TermDTO;
import HYlikelion.gitppo.repository.Term.TermAgreementRepository;
import HYlikelion.gitppo.repository.Term.TermContentRepository;
import HYlikelion.gitppo.repository.Term.TermRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javassist.NotFoundException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TermService {

    @NonNull
    private final TermRepository termRepository;

    @NonNull
    private final TermAgreementRepository termAgreementRepository;

    @NonNull
    private final TermContentRepository termContentRepository;


    public List<TermAgreement> saveTermAgreement(List<TermDTO.Post> agreements) throws Exception {
        if (agreements.size() == 0) {
            throw new Exception("Size of agreements can't be 0");
        }
        List<TermAgreement> result = new ArrayList<>();

        for (TermDTO.Post agreement : agreements) {
            Term term = termRepository.findById(agreement.getTerm_id())
                .orElseThrow(() -> new NotFoundException(
                    "Term with id : " + agreement.getTerm_id() + "is not valid"));

            TermAgreement termAgreement = new TermAgreement(term, LocalDateTime.now(),
                agreement.getTerm_ag_is_agree());
            termAgreementRepository.save(termAgreement);

            result.add(termAgreement);
        }
        return result;
    }

    public List<Term> findTerms() throws NotFoundException {
        List<Term> terms = termRepository.findAll();

        for (Term term : terms) {
            term.setContents(termContentRepository.findAllByTerm(term));
        }

        return terms;
    }

}
