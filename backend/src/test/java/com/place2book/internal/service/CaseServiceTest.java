package com.place2book.internal.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import com.place2book.internal.common.exceptions.NotFoundException;
import com.place2book.internal.model.Case;
import com.place2book.internal.model.CaseLog;
import com.place2book.internal.model.CaseStatus;
import com.place2book.internal.repository.CaseLogRepository;
import com.place2book.internal.repository.CaseRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CaseServiceTest {

    @Mock
    CaseRepository caseRepository;

    @Mock
    CaseLogRepository caseLogRepository;

    CaseService service;

    @BeforeEach
    void setUp() {
        service = new CaseService(caseRepository, caseLogRepository);
    }

    static Case caseWith(long id, CaseStatus status) {
        Case c = new Case();
        c.setId(id);
        c.setStatus(status);
        return c;
    }

    @Test
    @DisplayName("findAll passes the filters on to the repository")
    void findAllPassesFilters() {
        List<Case> cases = List.of(caseWith(1, CaseStatus.REGISTERED));
        when(caseRepository.findAll(CaseStatus.REGISTERED, "4")).thenReturn(cases);

        assertThat(service.findAll(CaseStatus.REGISTERED, "4")).isEqualTo(cases);
    }

    @Test
    @DisplayName("findById returns the case when it exists")
    void findByIdReturnsCase() {
        Case c = caseWith(1, CaseStatus.REGISTERED);
        when(caseRepository.findById(1)).thenReturn(Optional.of(c));

        assertThat(service.findById(1)).isSameAs(c);
    }

    @Test
    @DisplayName("findById throws NotFoundException for an unknown case")
    void findByIdThrowsForUnknownCase() {
        when(caseRepository.findById(42)).thenReturn(Optional.empty());

        NotFoundException ex = assertThrows(NotFoundException.class, () -> service.findById(42));
        assertThat(ex.getMessage()).isEqualTo("Case with id 42 not found");
    }

    @Test
    @DisplayName("create registers the case and writes the first log entry")
    void createRegistersAndLogs() {
        Case newCase = new Case();
        newCase.setStatus(CaseStatus.RETRANSFERRED); // ignored, new cases always start as registered
        when(caseRepository.insert(any())).thenAnswer(invocation -> {
            Case inserted = invocation.getArgument(0);
            inserted.setId(7L);
            return inserted;
        });

        Case created = service.create(newCase, "Anders");

        assertThat(created.getId()).isEqualTo(7L);
        assertThat(created.getStatus()).isEqualTo(CaseStatus.REGISTERED);

        ArgumentCaptor<CaseLog> entry = ArgumentCaptor.forClass(CaseLog.class);
        verify(caseLogRepository).insert(entry.capture());
        assertThat(entry.getValue().getCaseId()).isEqualTo(7L);
        assertThat(entry.getValue().getStatus()).isEqualTo(CaseStatus.REGISTERED);
        assertThat(entry.getValue().getPerformedBy()).isEqualTo("Anders");
    }

    @Test
    @DisplayName("changeStatus updates the case and writes a log entry")
    void changeStatusUpdatesAndLogs() {
        when(caseRepository.updateStatus(1, CaseStatus.ORGANIZER_IDENTIFIED)).thenReturn(true);
        when(caseRepository.findById(1)).thenReturn(Optional.of(caseWith(1, CaseStatus.ORGANIZER_IDENTIFIED)));

        Case updated = service.changeStatus(1, CaseStatus.ORGANIZER_IDENTIFIED, "Anders");

        assertThat(updated.getStatus()).isEqualTo(CaseStatus.ORGANIZER_IDENTIFIED);

        ArgumentCaptor<CaseLog> entry = ArgumentCaptor.forClass(CaseLog.class);
        verify(caseLogRepository).insert(entry.capture());
        assertThat(entry.getValue().getCaseId()).isEqualTo(1L);
        assertThat(entry.getValue().getStatus()).isEqualTo(CaseStatus.ORGANIZER_IDENTIFIED);
        assertThat(entry.getValue().getPerformedBy()).isEqualTo("Anders");
    }

    @Test
    @DisplayName("changeStatus allows an unknown user")
    void changeStatusWithoutUser() {
        when(caseRepository.updateStatus(1, CaseStatus.REGISTERED)).thenReturn(true);
        when(caseRepository.findById(1)).thenReturn(Optional.of(caseWith(1, CaseStatus.REGISTERED)));

        service.changeStatus(1, CaseStatus.REGISTERED, null);

        ArgumentCaptor<CaseLog> entry = ArgumentCaptor.forClass(CaseLog.class);
        verify(caseLogRepository).insert(entry.capture());
        assertThat(entry.getValue().getPerformedBy()).isNull();
    }

    @Test
    @DisplayName("changeStatus throws NotFoundException and writes no log entry for an unknown case")
    void changeStatusThrowsForUnknownCase() {
        when(caseRepository.updateStatus(42, CaseStatus.RETRANSFERRED)).thenReturn(false);

        assertThrows(NotFoundException.class,
                () -> service.changeStatus(42, CaseStatus.RETRANSFERRED, "Anders"));
        verify(caseLogRepository, never()).insert(any());
    }

    @Test
    @DisplayName("getHistory returns the log entries of the case")
    void getHistoryReturnsEntries() {
        CaseLog entry = new CaseLog();
        when(caseRepository.findById(1)).thenReturn(Optional.of(caseWith(1, CaseStatus.REGISTERED)));
        when(caseLogRepository.findByCaseId(1)).thenReturn(List.of(entry));

        assertThat(service.getHistory(1)).containsExactly(entry);
    }

    @Test
    @DisplayName("getHistory throws NotFoundException for an unknown case")
    void getHistoryThrowsForUnknownCase() {
        when(caseRepository.findById(42)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> service.getHistory(42));
        verify(caseLogRepository, never()).findByCaseId(42);
    }
}
