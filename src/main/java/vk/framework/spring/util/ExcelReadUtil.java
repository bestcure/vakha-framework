package vk.framework.spring.util;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFFormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Value;

import vk.framework.spring.dataaccess.util.DataMap;

public class ExcelReadUtil {
	@Value("#{file[\'upload.temp.path\']}")
	private static String uploadTempPath;

	public static List<DataMap> excelRead(String sourceFile, int startRow) throws Exception {
		String fileExt = sourceFile.substring(sourceFile.lastIndexOf(".") + 1);
		if (fileExt.equalsIgnoreCase("xls")) {
			return excelReadXls(sourceFile, startRow);
		} else if (fileExt.equalsIgnoreCase("xlsx")) {
			return excelReadXlxs(sourceFile, startRow);
		} else {
			throw new Exception("This file type is not supported. \nfile :" + sourceFile);
		}
	}

	public static List<DataMap> excelRead(URL sourceUrl, int startRow) throws Exception {
		String sourceFile = "";
		String fileName = "";
		String fileExt = "";
		String filePath = "";

		try {
			fileName = sourceUrl.getPath();
			fileName = fileName.substring(fileName.indexOf("/") + 1, fileName.length());
			fileName = fileName.replace("/", "_");
			fileExt = sourceUrl.getFile().substring(sourceUrl.getFile().lastIndexOf(".") + 1);
			filePath = uploadTempPath;
			File ex = new File(filePath + File.separator + fileName);
			FileUtils.copyURLToFile(sourceUrl, ex);
			sourceFile = ex.getCanonicalPath();
		} catch (Exception arg6) {
			throw new Exception(arg6);
		}

		if (fileExt.equalsIgnoreCase("xls")) {
			return excelReadXls(sourceFile, startRow);
		} else if (fileExt.equalsIgnoreCase("xlsx")) {
			return excelReadXlxs(sourceFile, startRow);
		} else {
			throw new Exception("This file type is not supported. \nfile :" + sourceUrl.toString());
		}
	}

	public static List<DataMap> excelReadXls(String sourceFile, int startRow) throws Exception {
		ArrayList dataList = new ArrayList();
		Workbook workbook = null;
		Sheet sheet = null;
		Row row = null;
		Cell cell = null;

		try {
			FileInputStream e = new FileInputStream(sourceFile);
			workbook = WorkbookFactory.create(e);
			if (workbook != null) {
				sheet = workbook.getSheetAt(0);
				if (sheet != null) {
					int rowEndIndex = sheet.getLastRowNum();
					byte columnStartIndex = 0;
					int columnEndIndex = sheet.getRow(1).getLastCellNum() - 1;
					String cellValue = "";

					for (int i = startRow; i <= rowEndIndex; ++i) {
						row = sheet.getRow(i);
						DataMap DataMap = new DataMap();

						for (int column = columnStartIndex; column <= columnEndIndex; ++column) {
							cell = row.getCell((short) column);
							cellValue = "";
							if (cell != null) {
								switch (cell.getCellType()) {
									case 0 :
										cellValue = String.valueOf((long) cell.getNumericCellValue());
										break;
									case 1 :
										cellValue = cell.getRichStringCellValue().getString();
										break;
									case 2 :
										cellValue = cell.getCellFormula();
									case 3 :
									default :
										break;
									case 4 :
										cellValue = String.valueOf(cell.getBooleanCellValue());
										break;
									case 5 :
										cellValue = Byte.toString(cell.getErrorCellValue());
								}
							}

							DataMap.put("cell" + column, cellValue);
						}

						dataList.add(DataMap);
					}
				}
			}

			return dataList;
		} catch (Exception arg15) {
			arg15.printStackTrace();
			throw new Exception(arg15);
		}
	}

	public static List<DataMap> excelReadXlxs(String sourceFile, int startRow) throws Exception {
		ArrayList dataList = new ArrayList();

		try {
			FileInputStream file = new FileInputStream(sourceFile);
			XSSFWorkbook workbook = new XSSFWorkbook(file);
			XSSFFormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
			XSSFSheet sheet = workbook.getSheetAt(0);
			Iterator rowIterator = sheet.iterator();

			while (rowIterator.hasNext()) {
				Row e = (Row) rowIterator.next();
				Iterator cellIterator = e.cellIterator();
				DataMap DataMap = new DataMap();

				while (cellIterator.hasNext()) {
					Cell cell = (Cell) cellIterator.next();
					switch (evaluator.evaluateInCell(cell).getCellType()) {
						case 0 :
							DataMap.put("cell" + cell.getColumnIndex(), Double.valueOf(cell.getNumericCellValue()));
							break;
						case 1 :
							DataMap.put("cell" + cell.getColumnIndex(), cell.getStringCellValue());
							break;
						case 2 :
							DataMap.put("cell" + cell.getColumnIndex(), cell.getCellFormula());
							break;
						case 3 :
						default :
							DataMap.put("cell" + cell.getColumnIndex(), cell.getStringCellValue());
							break;
						case 4 :
							DataMap.put("cell" + cell.getColumnIndex(), Boolean.valueOf(cell.getBooleanCellValue()));
							break;
						case 5 :
							DataMap.put("cell" + cell.getColumnIndex(), Byte.valueOf(cell.getErrorCellValue()));
					}
				}

				dataList.add(DataMap);
			}

			file.close();
			return dataList;
		} catch (Exception arg11) {
			arg11.printStackTrace();
			throw new Exception(arg11);
		}
	}
}