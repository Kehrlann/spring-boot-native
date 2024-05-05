package wf.garnier.nativedemo.books;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Collection;

import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

@Component
public class ExportService {

	public byte[] export(Collection<Book> books) {
		var workbook = new XSSFWorkbook();
		var createHelper = workbook.getCreationHelper();
		var font = workbook.createFont();
		font.setFontHeightInPoints((short) 18);

		var defaultCellStyle = workbook.createCellStyle();
		defaultCellStyle.setFont(font);
		var dateCellStyle = workbook.createCellStyle();
		dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("yyyy-MM-dd"));
		dateCellStyle.setFont(font);
		var headerCellStyle = workbook.createCellStyle();
		headerCellStyle.setFillForegroundColor(IndexedColors.LAVENDER.getIndex());
		headerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		headerCellStyle.setFont(font);

		var sheet = workbook.createSheet("book-list");
		sheet.createFreezePane(0, 1);

		int currentRow = 0;
		var row = sheet.createRow(currentRow);
		var cell = row.createCell(0);
		cell.setCellValue("Title");
		cell.setCellStyle(headerCellStyle);
		cell = row.createCell(1);
		cell.setCellValue("Author");
		cell.setCellStyle(headerCellStyle);
		cell = row.createCell(2);
		cell.setCellValue("Genre");
		cell.setCellStyle(headerCellStyle);
		cell = row.createCell(3);
		cell.setCellValue("Publication Date");
		cell.setCellStyle(headerCellStyle);

		for (Book book : books) {
			currentRow++;
			row = sheet.createRow(currentRow);
			cell = row.createCell(0);
			cell.setCellValue(book.title());
			cell.setCellStyle(defaultCellStyle);
			cell = row.createCell(1);
			cell.setCellValue(book.author());
			cell.setCellStyle(defaultCellStyle);
			cell = row.createCell(2);
			cell.setCellValue(book.genre());
			cell.setCellStyle(defaultCellStyle);
			var dateCell = row.createCell(3);
			dateCell.setCellValue(book.publicationDate());
			dateCell.setCellStyle(dateCellStyle);
		}

		sheet.setColumnWidth(0, 50 * 256);
		sheet.setColumnWidth(1, 30 * 256);
		sheet.setColumnWidth(2, 30 * 256);
		sheet.setColumnWidth(3, 30 * 256);

		try (ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream()) {
			workbook.write(byteArrayOut);
			return byteArrayOut.toByteArray();
		}
		catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
