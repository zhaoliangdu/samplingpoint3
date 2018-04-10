/**
 * 清理缓存、上传文件
 */
package com.samplepoint.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author 作者: ZhaoLiangdu
 * @time 创建时间：2017年12月15日上午10:52:46
 */
public class OperationFileUtils {
	public static void main(String[] args) {
		File file = new File("D:\\测试数据\\97.0__(2017_10_10 16_41_06)_CDR .txt");
		System.err.println(file.exists() + "-" + file.getAbsolutePath() + "-" + file.getPath() + "-" + file.isFile());
	}

	/**
	 * 删除文件
	 * 
	 * @param filePath
	 * @return
	 */
	public static boolean delFile(String filePath) {
		File file = new File(filePath);
		boolean delete = false;
		if (file.isFile()) {
			System.err.println("delFilename:" + file.getName() + "-path:" + file.getPath());
			delete = file.delete();
		}
		return delete;
	}

	/**
	 * 文件上传
	 * 
	 * @param files
	 * @param filePath
	 */
	public static void uploadFIle(MultipartFile file, String realPath) {
		System.err.println("filePath:" + realPath);
		// 保存到硬盘
		try {
			file.transferTo(new File(realPath));
			// 复制文件到项目访问路径
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 多文件上传
	 * 
	 * @param files
	 * @param realPath
	 * @param serverPath
	 * @param timeStr
	 */
	public static void uploadFIles(List<File> filelist, String realPath, String timeStr) {
		for (int i = 0; i < filelist.size(); i++) {
			System.err.println("filename:" + filelist.get(i).getName());
			System.err.println("filepath:" + filelist.get(i).getAbsolutePath());
		}
	}

	/**
	 * 创建所需文件夹
	 */
	public static void mkFiles() {
		String dir0 = "d://magic-ma969//";
		String dir1 = dir0 + "log";
		String dir2 = dir0 + "fileupload//";
		String dir21 = dir2 + "audio";
		String dir22 = dir2 + "pointdata";
		String dir23 = dir2 + "video";
		String dir24 = dir2 + "transmit";
		File file0 = new File(dir0);
		if (!file0.isDirectory()) {
			file0.mkdir();
			File file1 = new File(dir1);
			if (!file1.isDirectory()) {
				file1.mkdirs();
				File file2 = new File(dir2);
				if (!file2.isDirectory()) {
					file2.mkdirs();
					File file21 = new File(dir21);
					File file22 = new File(dir22);
					File file23 = new File(dir23);
					File file24 = new File(dir24);
					if (!(file21.isDirectory() && file22.isDirectory() && file23.isDirectory()
							&& file24.isDirectory())) {
						file21.mkdirs();
						file22.mkdirs();
						file23.mkdirs();
						file24.mkdirs();
					}
				}
			}
		}
	}

	/**
	 * 复制文件
	 * 
	 * @param filePath
	 * @param targetPath
	 */
	public static Long filesCopy(String filepath, String targetPath) {
		long copy = 0;
		try {
			File file = new File(filepath);
			if (file.isFile()) {
				copy = Files.copy(Paths.get(filepath), new FileOutputStream(targetPath));
			}
			System.err.println(copy);
		} catch (IOException e) {
			System.err.println("文件未找到！");
			e.printStackTrace();
		}
		// 返回文件大小字节byte
		return copy;
	}

}
