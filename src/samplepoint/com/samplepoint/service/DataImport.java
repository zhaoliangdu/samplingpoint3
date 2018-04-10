package com.samplepoint.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader; 
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service; 

@Service
public class DataImport {
	@Autowired
	DigitalDataService dService;
	@Autowired
	AnalogDataService aService;
	@Autowired
	RadioDataService rService;
	@Autowired
	CDRDataService cService;

	/**
	 * 读取文件到数据库
	 * 
	 * @param files
	 * @param area
	 * @param eid
	 * @param filePath
	 * @return
	 */
	public boolean readFileToDataBase(String realPath) {
		//String timeStr = LocalDate.now().toString() + System.currentTimeMillis() + "_";
		String[] filepaths = realPath.split(";");
		System.err.println("length:" + filepaths.length);
		if (filepaths != null) {
			List<File> filelist = new ArrayList<>();
			for (int i = 0; i < filepaths.length; i++) {
				filelist.add(new File(filepaths[i]));
			}
			// OperationFileUtils.uploadFIles(filelist, realPath, timeStr);
			for (int i = 0; i < filelist.size(); i++) {
				try {
					String encoding = "GBK";
					System.err.println("list:" + filelist.get(i));
					if (filelist.get(i).isFile() && filelist.get(i).exists()) {
						System.err.println("开始读文件：");
						InputStreamReader isread = new InputStreamReader(new FileInputStream(filelist.get(i)),
								encoding);
						BufferedReader bufferedReader = new BufferedReader(isread);
						int t = 0;
						String lineTxt = bufferedReader.readLine();
						readfile: while (lineTxt != null) {
							System.err.println(lineTxt);
							if (t >= 1) {
								String[] pointarr = lineTxt.split("\t");
								if ("调频".equals(pointarr[5]) || "调幅".equals(pointarr[5])) {
									if (rService.readerFileToRadioData(filelist.get(i))) {
										break readfile;
									} else {
										return false;
									}
								} else if ("数字电视".equals(pointarr[5])) {
									if (dService.readerFileToDigitalData(filelist.get(i))) {
										break readfile;
									} else {
										return false;
									}
								} else if ("CDR".equals(pointarr[5])) {
									if (cService.readerFileToCDRData(filelist.get(i))) {
										break readfile;
									} else {
										return false;
									}
								} else if ("模拟电视".equals(pointarr[5])) {
									if (aService.readerFileToRadioData(filelist.get(i))) {
										break readfile;
									} else {
										return false;
									}
								}
							}
							t++;
							lineTxt = bufferedReader.readLine();
						}
						isread.close();
						bufferedReader.close();
					} else {
						System.err.println("找不到指定的文件..");
					}
				} catch (Exception e) {
					System.err.println(e.getMessage());
					e.printStackTrace();
				}
			}
		}
		return true;
	}
}
