package finki.ask.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.annotation.JsonView;

import finki.ask.json.view.View;
import finki.ask.model.Test;
import finki.ask.model.TestType;
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

	/**
	 * @param name
	 *            Question name
	 * @param id
	 *            Creator (user) id
	 */
	@Override
	public List<Test> findByNameForUser(String name, Long id) {
		return populateIsActive(testRepository.findByNameForUser(name, id));
	}

	/**
	 * @param name
	 *            Question name
	 * @param id
	 *            Creator (user) id
	 */
	@Override
	public List<Test> findByNameExceptForUser(String name, Long id) {
		return populateIsActive(testRepository.findByNameExceptForUser(name, id));
	}

	@Override
	public List<Test> findForUser(long id) {
		return populateIsActive(testRepository.findForUser(id));
	}

	@Override
	public List<Test> findOtherTestsExceptForUser(long id) {
		return populateIsActive(testRepository.findOtherTestsExceptForUser(id));
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
