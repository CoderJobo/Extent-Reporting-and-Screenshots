package core;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelDataProvider implements ITestData {
	
	private String testDataFilepath;
	private FileInputStream fileStream = null;
	private XSSFWorkbook testDataWorkbook = null;
	private XSSFSheet testDataSheet = null;
	private int lastRow = 0;
	
	public ExcelDataProvider(String testDataFile, String sheetName) throws IOException {
		this.testDataFilepath = testDataFile;
		System.out.println("testDataFilepath = " + this.testDataFilepath);
		fileStream = new FileInputStream(new File(this.testDataFilepath));
		testDataWorkbook = new XSSFWorkbook(fileStream);
		testDataSheet = testDataWorkbook.getSheet(sheetName);
		System.out.println("Sheet name = " + sheetName);
		System.out.println("First row number = " + testDataSheet.getFirstRowNum());
		lastRow = testDataSheet.getLastRowNum();
		System.out.println("Last row number = " + testDataSheet.getLastRowNum());
	}
	
	public List<HashMap<String, String>> retrieveDataFromDataSource(String testName){
		
		System.out.println("Running retrieveDataFromSpreadsheet...");
		List<HashMap<String, String>> retrievedData = new ArrayList<>();
		
		for(int row = 1; row <= lastRow; row++) {  // Skip header row
			// Getting test case name
			String tcName = getCellDataAsString(testDataSheet.getRow(row).getCell(0));
			System.out.println("tcName = " + tcName);
			// Checking if the extracted test case is equal to the parameter testName
			if(testName.equalsIgnoreCase(tcName.trim())){
				System.out.println("Test name = tcName");
				// Getting the last column number for the test case
				int lastCol = testDataSheet.getRow(row).getLastCellNum();
			
				HashMap<String, String> record = new HashMap<>();

				for (int col = 1; col < lastCol; col++) {
					System.out.println("Getting key...");
					String key = getCellDataAsString(testDataSheet.getRow(0).getCell(col));
					System.out.println("Getting value...");
					String value = getCellDataAsString(testDataSheet.getRow(row).getCell(col));
					System.out.println(key + " = " + value);
					record.put(key, value);
				}
				retrievedData.add(record);
				System.out.println("retrievedData after record input: " + retrievedData);
				// String nextTestName = getCellDataAsString(testDataSheet.getRow(row + 1).getCell(0));
				if(testDataSheet.getRow(row+1)!= null){
					String nextTestName = getCellDataAsString(testDataSheet.getRow(row+1).getCell(0));
					if(!testName.equals(nextTestName)) break;
				}else{
					break;
				}

			}
		}
		System.out.println("Returning from retrieveDataFromSpreadsheet\n");
		System.out.println("retrievedData returned from retrieveDataFromDataSource: " + retrievedData);
		return retrievedData;
	}
	
	public String getTestData(String testCaseName, String columnName) {
		String value = "";
		for(int i = 0; i <= lastRow; i++) {
			String testName = getCellDataAsString(testDataSheet.getRow(i).getCell(0));
			if(testName.equalsIgnoreCase(testCaseName)) {
				int j = 0;
				String colName = getCellDataAsString(testDataSheet.getRow(0).getCell(j));
				
				while(!colName.isEmpty()) {
					colName = getCellDataAsString(testDataSheet.getRow(0).getCell(j));
					if(colName.equalsIgnoreCase(columnName)) {
						value = getCellDataAsString(testDataSheet.getRow(i).getCell(j));
						break;
					}
					j++;
				}
				break;
			}
		}
		return value;
	}
	
	public int getIterationCountForTest(String testName) {
		int itr = 1;
		for(int i = 0; i <= lastRow; i++) {
			String tcName = getCellDataAsString(testDataSheet.getRow(i).getCell(0));
			if(testName.equalsIgnoreCase(tcName)) {
				String nextTestName = getCellDataAsString(testDataSheet.getRow(i + 1).getCell(0));
				if(!testName.equals(nextTestName)) {
					break;
				}
				itr++;
			}
		}
		return itr;
	}
	
	public String getCellDataAsString(XSSFCell cell) {
		String cellDataAsString = "";
		if(cell != null) {
			CellType cellType = cell.getCellType();
			switch(cellType) {
			case BLANK :
				cellDataAsString = ""; 
				break;
			case BOOLEAN :
				cellDataAsString = (cell.getBooleanCellValue()) ? "true" : "false"; 
				break;
			case ERROR : 
				cellDataAsString = cell.getErrorCellString();
				break; 
			case FORMULA :
				cellDataAsString = cell.getStringCellValue(); 
				break;
			case NUMERIC :
				cellDataAsString = Double.toString(cell.getNumericCellValue()); 
				break;
			case STRING : 
			default : 
				cellDataAsString = cell.getStringCellValue(); 
			}
		}
		return cellDataAsString.trim();
	}

}
