package com.example.weather.exports;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.weather.domain.CityWeather;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class PdfView extends AbstractPdfView {

	public static final String DEST = "results/tables/simple_table.pdf";
	private List<CityWeather> weathers;
	private PdfPTable table;

	private static final List<String> HEADERS = new Headers().getHeaders();

	public PdfView(Map<String, Object> weatherMap) {
		weathers = (List<CityWeather>) weatherMap.get("weathers");
	}

	@Override
	protected void buildPdfDocument(Map<String, Object> weatherMap, Document document, PdfWriter writer,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		createPdf(DEST, document);

	}

	public void createPdf(String dest, Document document) throws IOException, DocumentException {
		Font subtitleFont = FontFactory.getFont("Times Roman", 6, BaseColor.BLACK);
		document.open();
		document.add(new Chunk(""));

		table = new PdfPTable(14);
		table.setWidthPercentage(105);

		for (int i = 0; i < 14; i++) {
			PdfPCell cell = new PdfPCell(new Phrase(HEADERS.get(i), subtitleFont));
			table.addCell(cell);
		}

		for (CityWeather cityWeather : weathers) {
			PdfPCell cell1 = new PdfPCell(new Phrase(cityWeather.getPlace().getCountry()));
			PdfPCell cell2 = new PdfPCell(new Phrase(cityWeather.getPlace().getCity()));
			PdfPCell cell3 = new PdfPCell(new Phrase(cityWeather.getCoordinates().getLat()));
			PdfPCell cell4 = new PdfPCell(new Phrase(cityWeather.getCoordinates().getLongitude()));
			PdfPCell cell5 = new PdfPCell(new Phrase(cityWeather.getWeather().getDescription()));
			PdfPCell cell6 = new PdfPCell(new Phrase(cityWeather.getWeather().getIcon()));
			PdfPCell cell7 = new PdfPCell(new Phrase(String.valueOf(cityWeather.getMainInfo().getTemp())));
			PdfPCell cell8 = new PdfPCell(new Phrase(cityWeather.getWeather().getDewPoint()));
			PdfPCell cell9 = new PdfPCell(new Phrase(String.valueOf(cityWeather.getMainInfo().getPressure())));
			PdfPCell cell10 = new PdfPCell(new Phrase(String.valueOf(cityWeather.getMainInfo().getHumidity())));
			PdfPCell cell11 = new PdfPCell(new Phrase(String.valueOf(cityWeather.getMainInfo().getWindGust())));
			PdfPCell cell12 = new PdfPCell(new Phrase(String.valueOf(cityWeather.getMainInfo().getCloudCover())));
			PdfPCell cell13 = new PdfPCell(new Phrase(String.valueOf(cityWeather.getVisibility())));
			PdfPCell cell14 = new PdfPCell(new Phrase(String.valueOf(cityWeather.getWind().getSpeed())));
			addToTableAndResize(cell1);
			addToTableAndResize(cell2);
			addToTableAndResize(cell3);
			addToTableAndResize(cell4);
			addToTableAndResize(cell5);
			addToTableAndResize(cell6);
			addToTableAndResize(cell7);
			addToTableAndResize(cell8);
			addToTableAndResize(cell9);
			addToTableAndResize(cell10);
			addToTableAndResize(cell11);
			addToTableAndResize(cell12);
			addToTableAndResize(cell13);
			addToTableAndResize(cell14);
		}
		document.add(table);
		document.close();
	}

	private void addToTableAndResize(PdfPCell cell) {
		cell.setFixedHeight(30);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(cell);
	}

}
