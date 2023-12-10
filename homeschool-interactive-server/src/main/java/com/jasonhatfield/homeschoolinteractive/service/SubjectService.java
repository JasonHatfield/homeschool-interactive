package com.jasonhatfield.homeschoolinteractive.service;

import com.jasonhatfield.homeschoolinteractive.model.Subject;
import com.jasonhatfield.homeschoolinteractive.repository.SubjectRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SubjectService {

    private final SubjectRepository subjectRepository;

    // Constructor injection of SubjectRepository
    public SubjectService(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

    // Retrieve all subjects from the repository
    public List<Subject> getAllSubjects() {
        return subjectRepository.findAll();
    }

    // Retrieve a subject by its ID from the repository
    public Optional<Subject> getSubjectById(Long id) {
        return subjectRepository.findById(id);
    }

    // Save a subject to the repository
    public Subject saveSubject(Subject subject) {
        return subjectRepository.save(subject);
    }

    // Delete a subject from the repository by its ID
    public void deleteSubject(Long id) {
        subjectRepository.deleteById(id);
    }

    // Update a subject in the repository by its ID
    public Optional<Subject> updateSubject(Long id, Subject subjectDetails) {
        return subjectRepository.findById(id)
                .map(subject -> {
                    subject.setName(subjectDetails.getName());
                    return subjectRepository.save(subject);
                });
    }

}
