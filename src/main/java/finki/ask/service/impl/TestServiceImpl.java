package finki.ask.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import finki.ask.model.Test;
import finki.ask.model.TestType;
import finki.ask.model.User;
import finki.ask.repository.TestRepository;
import finki.ask.service.TestService;

@Service
public class TestServiceImpl implements TestService {

	@Autowired
	private TestRepository testRepository;

	@Override
	public Test save(Test test) {
		return testRepository.save(test);
	}

	@Override
	public Test save(String json) {
		return null;
	}

	@Override
	public List<Test> findAll() {
		return populateIsActive(testRepository.findAll());
	}

	@Override
	public Page<Test> findAll(Pageable pageable) {
		return null;
	}
	
	@Override
	public Test findActiveById(long id) {
		return testRepository.findActiveById(id);
	}

	@Override
	public List<Test> findAllActive() {
		return testRepository.findAllActive();
	}
	
	@Override
	public List<Test> findActiveByType(TestType type) {
		return testRepository.findActiveByType(type);
	}
	
	@Override
	public Test findById(long id) {
		return populateIsActive(testRepository.findOne(id));
	}

	@Override
	public List<Test> findByName(String name) {
		return populateIsActive(testRepository.findByName(name));
	}

	@Override
	public List<Test> findByNameForUser(String name, User user) {
		return populateIsActive(testRepository.findByNameForUser(name, user));
	}

	@Override
	public List<Test> findByNameExceptForUser(String name, User user) {
		return populateIsActive(testRepository.findByNameExceptForUser(name, user));
	}

	@Override
	public List<Test> findForUser(User user) {
		return populateIsActive(testRepository.findForUser(user));
	}

	@Override
	public List<Test> findOtherTestsExceptForUser(User user) {
		return populateIsActive(testRepository.findOtherTestsExceptForUser(user));
	}

	@Override
	public void delete(long id) {
		testRepository.delete(id);
	}

	@Override
	public Test buildFromJson(String json) {
		return null;
	}
	
	@Override
	public List<Test> populateIsActive(List<Test> tests) {
		Date now = new Date();
		for (Test test : tests) {
			boolean flag = test.getStart().compareTo(now) <= 0 && test.getEnd().compareTo(now) >=0;
			test.setActive(flag);
		}
		return tests;
	}
	
	@Override
	public Test populateIsActive(Test test) {
		Date now = new Date();
		boolean flag = test.getStart().compareTo(now) <= 0 && test.getEnd().compareTo(now) >=0;
		test.setActive(flag);
		return test;
	}

}
