package vk.framework.spring.web;

import org.apache.poi.hssf.usermodel.HSSFFont;
import java.util.Iterator;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import java.util.List;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.util.CellRangeAddress;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import java.util.Map;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import vk.framework.spring.dataaccess.util.DataMap;

public class AbstractExcelExportView extends AbstractExcelView {
	protected HSSFCellStyle documentTitleStyle;
	protected HSSFCellStyle titleStyle;
	protected HSSFCellStyle titleTextStyle;
	protected HSSFCellStyle columnTextStyle;

	protected void buildExcelDocument(final Map model, final HSSFWorkbook workbook, final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {
		final String headerName = (String) model.get("excelHeader");
		final LinkedHashMap<String, String> columns = (LinkedHashMap<String, String>) model.get("excelColumns");
		final List<DataMap> resultList = (List<DataMap>) model.get("excelList");
		if (headerName == null || headerName == "") {
			throw new Exception("제목을 설정해 주세요.");
		}
		if (columns == null) {
			throw new Exception("컬럼을 설정해 주세요.");
		}
		if (resultList == null) {
			throw new Exception("내용을 설정해 주세요.");
		}
		final String fileName = headerName.replace(" ", "_");
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition",
				"attachment; filename=" + URLEncoder.encode(fileName, "UTF-8") + ".xls");
		this.documentTitleStyle = this.getStyleDocumentTitle(workbook.createCellStyle(), workbook.createFont());
		this.titleStyle = this.getStyleTitle(workbook.createCellStyle(), workbook.createFont());
		this.columnTextStyle = this.getStyleColumnText(workbook.createCellStyle(), workbook.createFont());
		final Sheet sheet = (Sheet) workbook.createSheet();
		Row row = null;
		Cell cell = null;
		row = sheet.createRow(0);
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, columns.size() - 1));
		cell = row.createCell(0);
		cell.setCellValue(headerName);
		cell.setCellStyle((CellStyle) this.documentTitleStyle);
		row.setHeight((short) 1000);
		row = sheet.createRow(1);
		int index = 0;
		for (final Map.Entry<String, String> entry : columns.entrySet()) {
			cell = row.createCell(index++);
			cell.setCellValue((String) entry.getValue());
			cell.setCellStyle((CellStyle) this.titleStyle);
		}
		int rowIndex = 2;
		Object value = null;
		for (final DataMap result : resultList) {
			row = sheet.createRow(rowIndex++);
			row.setHeight((short) 400);
			int columnIndex = 0;
			for (final Map.Entry<String, String> entry2 : columns.entrySet()) {
				cell = row.createCell(columnIndex++);
				value = result.get((Object) entry2.getKey());
				if (value instanceof Integer) {
					cell.setCellType(0);
					cell.setCellValue((double) Integer.parseInt(value.toString()));
				} else if (value instanceof Double) {
					cell.setCellType(0);
					cell.setCellValue((double) new Double(value.toString()));
				} else {
					cell.setCellType(1);
					if (value == null) {
						cell.setCellValue("");
					} else {
						cell.setCellValue(value.toString().trim());
					}
				}
				cell.setCellStyle((CellStyle) this.columnTextStyle);
			}
		}
	}

	protected HSSFCellStyle getStyleTitle(final HSSFCellStyle cellStyle, final HSSFFont font) {
		font.setFontName("맑은 고딕");
		font.setFontHeightInPoints((short) 11);
		font.setColor((short) 8);
		font.setBoldweight((short) 700);
		cellStyle.setFont(font);
		cellStyle.setAlignment((short) 2);
		cellStyle.setBorderBottom((short) 5);
		cellStyle.setBorderRight((short) 1);
		cellStyle.setBorderLeft((short) 1);
		cellStyle.setBorderTop((short) 1);
		cellStyle.setBorderBottom((short) 1);
		cellStyle.setFillBackgroundColor((short) 9);
		cellStyle.setFillForegroundColor((short) 22);
		cellStyle.setFillPattern((short) 1);
		cellStyle.setLocked(true);
		cellStyle.setWrapText(true);
		cellStyle.setVerticalAlignment((short) 1);
		return cellStyle;
	}

	protected HSSFCellStyle getStyleColumnText(final HSSFCellStyle cellStyle, final HSSFFont font) {
		font.setFontName("맑은 고딕");
		font.setFontHeightInPoints((short) 9);
		font.setColor((short) 8);
		cellStyle.setFont(font);
		cellStyle.setAlignment((short) 1);
		cellStyle.setBorderBottom((short) 5);
		cellStyle.setBorderRight((short) 1);
		cellStyle.setBorderLeft((short) 1);
		cellStyle.setBorderTop((short) 1);
		cellStyle.setBorderBottom((short) 1);
		cellStyle.setFillBackgroundColor((short) 9);
		cellStyle.setFillForegroundColor((short) 9);
		cellStyle.setFillPattern((short) 1);
		cellStyle.setLocked(true);
		cellStyle.setVerticalAlignment((short) 1);
		return cellStyle;
	}

	protected HSSFCellStyle getStyleDocumentTitle(final HSSFCellStyle cellStyle, final HSSFFont font) {
		font.setFontName("맑은 고딕");
		font.setFontHeightInPoints((short) 23);
		font.setColor((short) 8);
		font.setBoldweight((short) 700);
		cellStyle.setFont(font);
		cellStyle.setAlignment((short) 2);
		cellStyle.setBorderBottom((short) 5);
		cellStyle.setBorderRight((short) 1);
		cellStyle.setBorderLeft((short) 1);
		cellStyle.setBorderTop((short) 1);
		cellStyle.setBorderBottom((short) 1);
		cellStyle.setFillBackgroundColor((short) 9);
		cellStyle.setFillForegroundColor((short) 9);
		cellStyle.setFillPattern((short) 1);
		cellStyle.setLocked(true);
		cellStyle.setVerticalAlignment((short) 1);
		return cellStyle;
	}
}