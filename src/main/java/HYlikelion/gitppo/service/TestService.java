package HYlikelion.gitppo.service;

import HYlikelion.gitppo.domain.Test;
import HYlikelion.gitppo.repository.TestRepository;
import javassist.NotFoundException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TestService {
    @NonNull
    private final TestRepository testRepository;


    public Long saveTest(Test test)  {
        Test save = testRepository.save(test);
        return test.getId();
    }

    public Test findOne(Long id) throws NotFoundException {
        Test test = testRepository.findById(id).orElseThrow(()-> new NotFoundException("Test with id : " + id + "is not valid"));
        return test;
    }

}
