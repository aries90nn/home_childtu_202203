package kr.co.nninc.ncms.common;

import java.io.File;

import org.springframework.stereotype.Component;

import net.coobird.thumbnailator.Thumbnails;

/**
 * 썸네일 함수
 * 
 * @author 나눔
 * @since 2017.11.16
 * @version 1.0
 */
@Component("FuncThumb")
public class FuncThumb {

	public static void GD2_make_thumb(int width, int height, String thum_path, String org_path, String filename) throws Exception {
		String fileExt = filename.substring(filename.lastIndexOf('.') + 1).toLowerCase();
		
		File image = new File(org_path + "/" + filename);
		File thumbnail = new File(org_path + thum_path + filename);
		if (fileExt.equals("bmp") || fileExt.equals("png") || fileExt.equals("gif") || fileExt.equals("jpg") || fileExt.equals("jpeg")) {
			if (image.exists()) {
				thumbnail.getParentFile().mkdirs(); 
				Thumbnails.of(image).size(width, height).outputFormat(fileExt).toFile(thumbnail);
			}
		}
	}
}