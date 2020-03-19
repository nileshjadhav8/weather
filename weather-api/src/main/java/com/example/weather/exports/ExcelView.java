package com.example.weather.exports;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.hssf.util.HSSFColor.HSSFColorPredefined;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.servlet.view.document.AbstractXlsView;

import com.example.weather.domain.CityWeather;

public class ExcelView extends AbstractXlsView {

	private static final List<String> HEADERS = new Headers().getHeaders();

	@Override
	protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		response.setHeader("Content-Disposition", "attachment; filename=\"weather.xls\"");

		@SuppressWarnings("unchecked")
		List<CityWeather> weathers = (List<CityWeather>) model.get("weathers");

		Sheet sheet = workbook.createSheet(weathers.get(0).getPlace().getCity() + " Weather Forecast");
		sheet.setDefaultColumnWidth(30);

		CellStyle style = workbook.createCellStyle();
		Font font = workbook.createFont();
		font.setFontName("Arial");
		style.setFillForegroundColor(HSSFColor.BLUE.index);
		style.setFillBackgroundColor(HSSFColorPredefined.BLUE.getIndex());
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		font.setBold(true);
		font.setColor(HSSFColor.WHITE.index);
		style.setFont(font);

		Row header = sheet.createRow(0);
		for (int i = 0; i < 14; i++) {
			logger.info(HEADERS.get(i));
		}
		for (int i = 0; i < 14; i++) {
			header.createCell(i).setCellValue(HEADERS.get(i));
		}

		int rowCount = 1;
		for (CityWeather weather : weathers) {
			Row courseRow = sheet.createRow(rowCount++);
			courseRow.createCell(0).setCellValue(weather.getPlace().getCountry());
			courseRow.createCell(1).setCellValue(weather.getPlace().getCity());
			courseRow.createCell(2).setCellValue(weather.getCoordinates().getLat());
			courseRow.createCell(3).setCellValue(weather.getCoordinates().getLongitude());
			courseRow.createCell(4).setCellValue(weather.getWeather().getDescription());
			courseRow.createCell(5).setCellValue(weather.getWeather().getIcon().toString());
			courseRow.createCell(6).setCellValue(weather.getMainInfo().getTemp());
			courseRow.createCell(7).setCellValue(weather.getWeather().getDewPoint());
			courseRow.createCell(8).setCellValue(weather.getMainInfo().getPressure());
			courseRow.createCell(9).setCellValue(weather.getMainInfo().getHumidity());
			courseRow.createCell(10).setCellValue(weather.getMainInfo().getWindGust());
			courseRow.createCell(11).setCellValue(weather.getMainInfo().getCloudCover());
			courseRow.createCell(12).setCellValue(weather.getVisibility());
			courseRow.createCell(13).setCellValue(weather.getWind().getSpeed());
		}

	}

}
