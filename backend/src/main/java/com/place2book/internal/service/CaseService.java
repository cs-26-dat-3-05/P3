package com.place2book.internal.service;

import java.util.List;

import com.place2book.internal.common.exceptions.NotFoundException;
import com.place2book.internal.model.Case;
import com.place2book.internal.model.CaseLog;
import com.place2book.internal.model.CaseStatus;
import com.place2book.internal.repository.CaseLogRepository;
import com.place2book.internal.repository.CaseRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CaseService {
    private final CaseRepository caseRepository;
    private final CaseLogRepository caseLogRepository;

    CaseService(CaseRepository caseRepository, CaseLogRepository caseLogRepository) {
        this.caseRepository = caseRepository;
        this.caseLogRepository = caseLogRepository;
    }

    /**
     * Finds cases by newest first. If a filter is null it will be ignored
     */
    public List<Case> findAll(CaseStatus status, String organizerId) {
        return caseRepository.findAll(status, organizerId);
    }

    /**
     * @throws NotFoundException if the case does not exist
     */
    public Case findById(long id) {
        return caseRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Case with id " + id + " not found"));
    }

    /**
     * Creates a case with status {@link CaseStatus#REGISTERED} and writes the
     * first entry in its history.
     *
     * @param newCase     the case to create, its id, status and createdAt are ignored
     * @param performedBy who created the case, or null if unknown
     * @return the created case
     */
    @Transactional
    public Case create(Case newCase, String performedBy) {
        newCase.setStatus(CaseStatus.REGISTERED);
        Case created = caseRepository.insert(newCase);
        log(created.getId(), created.getStatus(), performedBy);
        return created;
    }

    /**
     * Changes the status of a case and writes the change to its history.
     *
     * @param performedBy who changed the status, or null if unknown
     * @return the updated case
     * @throws NotFoundException if the case does not exist
     */
    @Transactional
    public Case changeStatus(long id, CaseStatus status, String performedBy) {
        if (!caseRepository.updateStatus(id, status)) {
            throw new NotFoundException("Case with id " + id + " not found");
        }
        log(id, status, performedBy);
        return findById(id);
    }

    /**
     * @return the status history of the case, oldest entry first
     * @throws NotFoundException if the case does not exist
     */
    public List<CaseLog> getHistory(long id) {
        findById(id);
        return caseLogRepository.findByCaseId(id);
    }

    private void log(long caseId, CaseStatus status, String performedBy) {
        CaseLog entry = new CaseLog();
        entry.setCaseId(caseId);
        entry.setStatus(status);
        entry.setPerformedBy(performedBy);
        caseLogRepository.insert(entry);
    }
}
