package com.example.weather.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Component;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.WriteConcern;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSInputFile;

@Component
public class DatabaseService {
	Mongo mongo = new Mongo("localhost", 27017);
	DB db = mongo.getDB("test");
	DBCollection collection = db.getCollection("downloads_meta");

	GridFS gridFS = new GridFS(db, "downloads");

	public void writeToDatabase(File file) {
		GridFSInputFile gfsFile = null;
		try {
			gfsFile = gridFS.createFile(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		gfsFile.setFilename(file.getName());
		gfsFile.save();

		BasicDBObject info = new BasicDBObject();
		info.put("name", file.getName());
		info.put("fileName", file.getName());
		info.put("rawName", "mongodb-osx-x86_64-2-1.2.1.tgz");
		info.put("rawPath", "src/main/resources/temp/");
		collection.insert(info, WriteConcern.SAFE);
		System.out.println("file saved to database");
	}

	public void getFiles(ArrayList<String> fileNames) {
		for (String name : fileNames) {
			GridFSDBFile gfsFileOut = gridFS.findOne(name);
			System.out.println(gfsFileOut.getInputStream());
			InputStream inputStream = gfsFileOut.getInputStream();
			try {
				writeFromInputstreamToFile(inputStream, gfsFileOut);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}

		List<DBObject> resultsInDescendingOrderByDate = getResultsInDescendingOrderByDate(20);
		for (DBObject dbObject : resultsInDescendingOrderByDate) {
			System.out.println("results " + dbObject.toString());
		}

	}

	public void getFile(String filename) {
		GridFSDBFile gfsFileOut = gridFS.findOne(filename);
		System.out.println(gfsFileOut.getInputStream());
		InputStream inputStream = gfsFileOut.getInputStream();
		try {
			writeFromInputstreamToFile(inputStream, gfsFileOut);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	public void writeFromInputstreamToFile(InputStream inputStream, GridFSDBFile gfsFileOut) throws IOException {
		String home = System.getProperty("user.home");
		File targetFile = new File(home + "/Downloads/" + gfsFileOut.getFilename());
		FileUtils.copyInputStreamToFile(inputStream, targetFile);
	}

	public List<DBObject> getResultsInDescendingOrderByDate(int limit) {

		List<DBObject> myList = null;
		DBCursor myCursor = collection.find().sort(new BasicDBObject("date", -1)).limit(10);
		myList = myCursor.toArray();

		return myList;
	}

}
