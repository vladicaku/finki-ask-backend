package finki.ask.service;

import java.util.List;

import javax.transaction.Transactional;

import finki.ask.model.ResultInfo;

@Transactional
public interface ResultInfoService {
	
	public List<ResultInfo> getResultInfoForTest(long id);
	
}
