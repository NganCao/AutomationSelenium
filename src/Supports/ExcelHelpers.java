package Supports;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelHelpers {

	private FileInputStream fileIn;
	private FileOutputStream fileOut;
	private Workbook wb;
	private Sheet sheet;
	private Cell cell;
	private Row row;
	private String excelFilPath;
//	private Map<String, Integer> columns = new HashMap<>();
	private CellType cellType;
	private int noRow, noCol;

	public void setCellData(String value, int rownum, int colnum) throws Exception {
		try {
			row = sheet.getRow(rownum);
			if (row == null) {
				row = sheet.createRow(rownum);
			}

			cell = row.getCell(colnum);
			if (cell == null) {
				cell = row.createCell(colnum);
			}

			cell.setCellValue(value);

			// write Excel file
			fileOut = new FileOutputStream(excelFilPath);
			wb.write(fileOut);
			fileOut.flush();
			fileOut.close();
		} catch (Exception e) {
			throw (e);
		}

	}

	private void getDataFile(String excelPath, String sheetName) throws Exception {
		File file = new File(excelPath);
		fileIn = new FileInputStream(file);
		wb = WorkbookFactory.create(fileIn);
		sheet = wb.getSheet(sheetName);
		noRow = sheet.getLastRowNum();
		noCol = sheet.getRow(0).getLastCellNum();
	}

	private Map<String, Integer> getRowAndCellNumber() {
		Map<String, Integer> map = new HashMap<>();
		map.put("row", noRow);
		map.put("cell", noCol);
		return map;
	}

	public Map<String, Integer> getExcelFile(String excelPath, String sheetName) throws Exception {
		getDataFile(excelPath, sheetName);
		fileIn.close();
		wb.close();
		return getRowAndCellNumber();
	}

	public String getDataAtPosition(int rowNo, int colNo) throws Exception {
		try {
			cell = sheet.getRow(rowNo).getCell(colNo);
			String data = null;
			if (null != cell) {
				cellType = cell.getCellType();
				switch (cellType) {
				case STRING:
					data = cell.getStringCellValue();
					break;
				case NUMERIC:
					if (DateUtil.isCellDateFormatted(cell)) {
						data = String.valueOf(cell.getDateCellValue());
					} else {
						data = String.valueOf((long) cell.getNumericCellValue());
					}
					break;
				case BOOLEAN:
					data = String.valueOf(cell.getBooleanCellValue());
					break;
				case BLANK:
					data = "";
					break;
				case _NONE:
					break;
				case ERROR:
					data = String.valueOf(cell.getErrorCellValue());
					break;
				case FORMULA:
					data = String.valueOf(cell.getCellFormula());
					break;
				default:
					data = "";
				}
			} else {
				data = "";
			}
			return data;
		} catch (Exception e) {
			System.out.println(e);
			return "";
		}
	}

	public void readExcelFile(String excelPath, String sheetName) throws Exception {
		String data;
		getDataFile(excelPath, sheetName);
		for (int i = 0; i <= noRow; i++) {
			System.out.print("|");
			for (int j = 0; j <= noCol; j++) {
				data = getDataAtPosition(i, j);
				if (data == null) {
					System.out.print("NULL\t|");
				} else if (data.equals("")) {
					System.out.print("BLANK\t|");
				} else if (null != data) {
					System.out.print("data\t|");
				}
			}
			System.out.println();
		}
		wb.close();
		fileIn.close();
	}

	public List<String> getDataList_byRow(String excelPath, String sheetName, int row) throws Exception {
		List<String> list = new ArrayList<>();
		for (int i = 0; i <= noCol; i++) {
			list.add(getDataAtPosition(row, i));
		}
		wb.close();
		fileIn.close();
		return list;
	}

	public List<String> getDataList_byColumn(String excelPath, String sheetName, int column) throws Exception {
		List<String> list = new ArrayList<>();
		for (int i = 1; i <= noRow; i++) {
			list.add(getDataAtPosition(i, column));
		}
		wb.close();
		fileIn.close();
		return list;
	}

	public void writeExcelFileAtPosition(String xpath, String sheetname, int rowNum, int colNum, String value)
			throws Exception {
		try {
			fileIn = new FileInputStream(new File(xpath));
			wb = WorkbookFactory.create(fileIn);
			sheet = wb.getSheet(sheetname);
			Row r = sheet.getRow(rowNum);

			Cell c = r.createCell(colNum);
			c.setCellValue(value);

			fileOut = new FileOutputStream(new File(xpath));
			wb.write(fileOut);
			fileIn.close();
			fileOut.close();
			wb.close();
		} catch (Exception e) {
			System.out.println(e);
		}

	}
}
