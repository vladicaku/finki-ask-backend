package finki.ask.service;

import java.util.List;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import finki.ask.model.ResultInfo;

@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
public interface ResultInfoService {
	
	public List<ResultInfo> getResultInfoForTest(long id);
	
}
