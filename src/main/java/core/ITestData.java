package core;

import java.util.HashMap;
import java.util.List;

public interface ITestData {
	
	public List<HashMap<String, String>> retrieveDataFromDataSource(String testName);

}
