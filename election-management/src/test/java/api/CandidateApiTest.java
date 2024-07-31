package api;

import api.dto.in.CreateCandidate;
import api.dto.in.UpdateCandidate;
import domain.Candidate;
import domain.CandidateService;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import javax.inject.Inject;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@QuarkusTest
class CandidateApiTest {
    @Inject
    CandidateApi candidateApi;

    @InjectMock
    CandidateService candidateService;

    @Test
    void create(){
        CreateCandidate createCandidate = Instancio.create(CreateCandidate.class);
        ArgumentCaptor<Candidate> candidateArgumentCaptor = ArgumentCaptor.forClass(Candidate.class);
        candidateApi.create(createCandidate);
        verify(candidateService).save(candidateArgumentCaptor.capture());
        verifyNoMoreInteractions(candidateService);

        Candidate candidate = candidateArgumentCaptor.getValue();
        assertEquals(candidate.email(),createCandidate.email());
        assertEquals(candidate.familyName(), createCandidate.familyName());
        assertEquals(candidate.givenName(), createCandidate.givenName());
        assertEquals(candidate.jobTitle(), createCandidate.jobTitle());
        assertEquals(candidate.phone(), createCandidate.phone());
        assertEquals(candidate.photo(), createCandidate.photo());

    }

    @Test
    void update(){
        String id = UUID.randomUUID().toString();
        UpdateCandidate updateCandidate = Instancio.create(UpdateCandidate.class);
        Candidate candidate = updateCandidate.toDomain(id);

        ArgumentCaptor<Candidate> candidateArgumentCaptor = ArgumentCaptor.forClass(Candidate.class);

        when(candidateService.findById(id)).thenReturn(candidate);

        var response = candidateApi.update(id, updateCandidate);
        verify(candidateService).save(candidateArgumentCaptor.capture());
        verify(candidateService).findById(id);
        verifyNoMoreInteractions(candidateService);

        assertEquals(api.dto.out.Candidate.fromDomain(candidate),response);
    }

    @Test
    void list(){
        List<Candidate> candidates = Instancio.stream(Candidate.class).limit(10).toList();
        when(candidateService.findAll()).thenReturn(candidates);

        var response = candidateApi.list();

        verify(candidateService).findAll();
        verifyNoMoreInteractions(candidateService);

        assertIterableEquals(candidates.stream().map(api.dto.out.Candidate::fromDomain).collect(Collectors.toList()), response);
    }
}