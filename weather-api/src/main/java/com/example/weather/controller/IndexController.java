package com.example.weather.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.repository.WeatherRepository;
import com.example.weather.domain.CityWeather;
import com.example.weather.exports.AbstractPdfView;
import com.example.weather.exports.ExcelView;
import com.example.weather.exports.PdfView;
import com.example.weather.service.CitySearchService;
import com.example.weather.service.DatabaseService;
import com.example.weather.service.WeatherService;
import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfWriter;
import com.mongodb.MongoClient;
import com.mongodb.gridfs.GridFSDBFile;

@Controller
@RequestMapping("/")
@SessionAttributes("filenames")
public class IndexController {
	@Autowired
	private WeatherRepository repository;

	@Autowired
	private WeatherService weatherService;

	@Autowired
	DatabaseService databaseService;

	private List<String> filenames = new ArrayList<>();

	private CityWeather cityWeather;

	private ArrayList<CityWeather> weathers;

	private Map<String, Object> weatherMap;

	private static String UPLOADED_FOLDER = "src/main/resources/temp/";
	
	private static final Logger logger = LoggerFactory.getLogger(IndexController.class);


	public String getIndex() {
		return "index";
	}

	@RequestMapping(value = "/searchCity/{city}")
	public String searchCity(@PathVariable("city") String city, Model model, RedirectAttributes redirectAttributes) {
		cityWeather = weatherService.searchByCity(city);
		weatherMap = new HashMap<>();
		weathers = new ArrayList<>();
		weathers.add(cityWeather);
		repository.save(cityWeather);
		model.addAttribute("weather", cityWeather);
		model.addAttribute("weathers", weathers);
		weatherMap.put("weathers", weathers);
		return "weather";
	}

	@RequestMapping(value = "/excelview", method = RequestMethod.GET)
	public ModelAndView showExcel(HttpServletRequest request, HttpServletResponse response) {
		return new ModelAndView(new ExcelView(), weatherMap);
	}

	@RequestMapping(value = "/pdfview", method = RequestMethod.GET)
	public ModelAndView showPdf(HttpServletRequest request, HttpServletResponse response) {
		return new ModelAndView(new PdfView(weatherMap));

	}

	// city weather aratıldıktan sonra database'e save edilen bilgileri gösterir.
	@RequestMapping("/database/showall")
	public String getWeathers(Model model) {
		List<CityWeather> databaseWeathers = repository.findAll();
		model.addAttribute("databaseWeathers", databaseWeathers);
		for (CityWeather cityWeather : databaseWeathers) {
			logger.info("retrieved from database: " + cityWeather);
		}
		return "database";
	}

	@RequestMapping("/upload")
	public String singleFileUpload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes,
			Model model) {

		if (file.isEmpty()) {
			redirectAttributes.addFlashAttribute("message", "Please select a file to upload");
			return "redirect:uploadStatus";
		}

		try {

			// Get the file and save it somewhere
			byte[] bytes = file.getBytes();
			Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
			Files.write(path, bytes);
			databaseService.writeToDatabase(path.toFile());
			filenames.add(file.getOriginalFilename());

			redirectAttributes.addFlashAttribute("message",
					"You successfully uploaded '" + file.getOriginalFilename() + "'");
			model.addAttribute("filenames", filenames);

		} catch (IOException e) {
			e.printStackTrace();
		}
		return "redirect:/uploadStatus";
	}

	@RequestMapping("/uploadStatus")
//	@ResponseBody
	public String uploadStatus() {
//        return "File uploaded to "+UPLOADED_FOLDER;
		return "uploadok.html";
	}

	@RequestMapping("/getFiles")
	public String getAllFiles(@ModelAttribute("filenames") ArrayList<String> filenames) {
		for (String name : filenames) {
			logger.info(name);
		}
		databaseService.getFiles(filenames);
		return "getfiles.html";
	}

	@RequestMapping("/showFiles")
	public String showFiles(@ModelAttribute("filenames") ArrayList<String> filenames, HttpSession session) {
		if (session.getAttribute("filenames") != null) {
			for (String name : filenames) {
				logger.info(name);
			}
		}
		return "showfiles.html";
	}

	@RequestMapping("/getFile/{filename}")
	@ResponseBody
	public String getFile(@PathVariable("filename") String filename) {
		databaseService.getFile(filename);
		String message = "file download ok! " + filename;
		return message;
	}

}
