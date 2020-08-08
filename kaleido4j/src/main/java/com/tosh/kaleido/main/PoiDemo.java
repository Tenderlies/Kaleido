package com.tosh.kaleido.main;

import java.io.File;
import java.io.FileInputStream;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class PoiDemo {

	public static void update(HSSFWorkbook xls, XSSFWorkbook xlsx) {
		HSSFSheet xlsSheet = xls.getSheetAt(0);
		int xlsCursor = xlsSheet.getPhysicalNumberOfRows();
		XSSFSheet sheet = xlsx.getSheetAt(0);
//		XSSFRow title = sheet.getRow(0);
		for (int ri = 1; ri < sheet.getPhysicalNumberOfRows(); ri++) {
			XSSFRow row = sheet.getRow(ri);
			HSSFRow newRow = xlsSheet.createRow(xlsCursor++);
			for (int ci = 0; ci < row.getPhysicalNumberOfCells(); ci++) {
				XSSFCell cell = row.getCell(ci);
				if (cell != null) {
					HSSFCell newCell = newRow.createCell(ci);
					CellType type = cell.getCellType();
					newCell.setCellType(type);
					if (type == CellType.STRING) {
						newCell.setCellValue(cell.getStringCellValue());
					} else if (type == CellType.NUMERIC) {
						newCell.setCellValue(cell.getNumericCellValue());
					} else if (type == CellType.BLANK) {
						newCell.setBlank();
					} else {
						assert false : "错误的单元格类型";
					}
				}
			}
		}
	}

	public static void main(String[] args) throws Exception {
		FileInputStream xlsInput = new FileInputStream(new File("xlspath"));
		FileInputStream xlsxInput = new FileInputStream(new File("xlsxpath"));

		HSSFWorkbook xls = new HSSFWorkbook(xlsInput);

		XSSFWorkbook xlsx = new XSSFWorkbook(xlsxInput);
		update(xls, xlsx);
		xlsx.close();
		xlsxInput.close();

		xls.close();
		xlsInput.close();

	}
}
