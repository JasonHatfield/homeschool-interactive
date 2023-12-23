package com.jasonhatfield.homeschoolinteractive.service;

import com.jasonhatfield.homeschoolinteractive.model.Subject;
import com.jasonhatfield.homeschoolinteractive.repository.SubjectRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service class for managing subjects.
 */
@Service
public class SubjectService {

    private final SubjectRepository subjectRepository;

    /**
     * Constructor injection of SubjectRepository.
     *
     * @param subjectRepository the subject repository
     */
    public SubjectService(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

    /**
     * Retrieve all subjects from the repository.
     *
     * @return the list of subjects
     */
    public List<Subject> getAllSubjects() {
        return subjectRepository.findAll();
    }

    /**
     * Retrieve a subject by its ID from the repository.
     *
     * @param id the ID of the subject
     * @return the optional subject
     */
    public Optional<Subject> getSubjectById(Long id) {
        return subjectRepository.findById(id);
    }

    /**
     * Save a subject to the repository.
     *
     * @param subject the subject to be saved
     * @return the saved subject
     */
    public Subject saveSubject(Subject subject) {
        return subjectRepository.save(subject);
    }

    /**
     * Delete a subject from the repository by its ID.
     *
     * @param id the ID of the subject to be deleted
     */
    public void deleteSubject(Long id) {
        subjectRepository.deleteById(id);
    }

    /**
     * Update a subject in the repository by its ID.
     *
     * @param id             the ID of the subject to be updated
     * @param subjectDetails the updated subject details
     * @return the optional updated subject
     */
    public Optional<Subject> updateSubject(Long id, Subject subjectDetails) {
        return subjectRepository.findById(id)
                .map(subject -> {
                    subject.setName(subjectDetails.getName());
                    return subjectRepository.save(subject);
                });
    }

}
