package finki.ask.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import finki.ask.model.Question;
import finki.ask.model.Result;
import finki.ask.model.ResultInfo;
import finki.ask.model.StudentAnswer;
import finki.ask.model.Test;
import finki.ask.service.ResultInfoService;
import finki.ask.service.ResultService;
import finki.ask.service.TestService;

@Service
public class ResultInfoServiceImpl implements ResultInfoService{
	
	@Autowired
	private TestService testService;
	
	@Autowired
	private ResultService resultService;
	
	
	@Override
	public List<ResultInfo> getResultInfoForTest(long id) {
		//Test test = (Test) testService.findForUser(id);
		Test test = (Test) testService.findById(id);
		List<ResultInfo> resultsInfo = new ArrayList<>(test.getQuestions().size());
		
		for (Question question : test.getQuestions()) {
			ResultInfo resultInfo = new ResultInfo();
			resultInfo.setQuestion(question);
			List<Result> results = resultService.findAllSpecific(test, question);
			
			for (Result r : results) {
				if (r.getTotalCorrect() == r.getAnsweredCorrect()) {
					resultInfo.setCorrect(resultInfo.getCorrect() + 1);
				}
				else if (r.getAnsweredCorrect() == 0) {
					resultInfo.setIncorrect(resultInfo.getIncorrect() + 1);
				}
				else if (r.getAnsweredCorrect() < r.getTotalCorrect()) {
					resultInfo.setPartialCorrect(resultInfo.getPartialCorrect() + 1);
				}
			}
			resultsInfo.add(resultInfo);
		}
		
		return resultsInfo;
	}
}
