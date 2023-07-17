package kr.co.nninc.ncms.common;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.web.servlet.view.document.AbstractExcelView;

public class ExcelView extends AbstractExcelView{
	public static void main(String[] args) {

	}

	@Override
	protected void buildExcelDocument(Map<String, Object> modelMap,
			HSSFWorkbook workbook, HttpServletRequest req,
			HttpServletResponse res) throws Exception {

		String excelName = (String) modelMap.get("excelName");

		List<String> colName = (List<String>) modelMap.get("colName");
		List<String[]> colValue = (List<String[]>) modelMap.get("colValue");

		res.setContentType("application/msexcel");
		res.setHeader("Content-Disposition", "attachment; filename="
				+ excelName + ".xls");

		HSSFSheet sheet = workbook.createSheet(excelName);

		// 상단 메뉴명 생성
		HSSFRow menuRow = sheet.createRow(0);
		for (int i = 0; i < colName.size(); i++) {
			HSSFCell cell = menuRow.createCell(i);
			cell.setCellValue(new HSSFRichTextString(colName.get(i)));
		}

		// 내용 생성
		for (int i = 0; i < colValue.size(); i++) {
			// 메뉴 ROW가 있기때문에 +1을 해준다.
			HSSFRow row = sheet.createRow(i + 1);
			for (int j = 0; j < colValue.get(i).length; j++) {
				HSSFCell cell = row.createCell(j);
				cell.setCellValue(new HSSFRichTextString(colValue.get(i)[j]));
			}
		}
	}
}
