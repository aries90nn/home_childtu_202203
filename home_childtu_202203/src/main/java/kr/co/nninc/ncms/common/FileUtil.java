package kr.co.nninc.ncms.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import kr.co.nninc.ncms.common.service.MessageService;

/**
 * 파일관리 관련 유틸리티 클래스
 * 
 * @author 나눔
 * @since 2017.09.04
 * @version 1.0
 */
@Component("fileutil")
public class FileUtil {
	public static final int BUFF_SIZE = 2048;
	public final String[] FILE_TYPE_IMAGE = { "jpg", "gif", "png" };
	public final String[] FILE_TYPE_COMMON_ALL = { "hwp", "doc", "docx", "xls", "xlsx", "ppt", "pptx", "pdf", "jpg",
			"gif", "txt", "zip" };
	
	/** 메시지 출력할때 사용 */
	@Resource(name = "messageService")
	private MessageService messageService;
	
	/**
	 * @title : 새로운 파일명 이름을 결정한다
	 * @return : String 새로운 파일명
	 */
	public static String getNewFileName(String orginFileName) {
		StringBuffer sb = new StringBuffer();
		
		int idx = orginFileName.lastIndexOf(".");
		// 확장자를 뺀 파일명
		String onlyOrginFileName = orginFileName.substring(0, idx);
		// 원파일명의 확장자
		String orginExtsn = orginFileName.substring(idx + 1).toLowerCase();

		// 파일명
		sb.append(onlyOrginFileName);
		// 확장자
		sb.append(".").append(orginExtsn);
		return sb.toString();
	}

	/**
	 * @title: 파일명의 확장자를 가져온다
	 * @param file_name
	 * @return 확장자
	 */
	public static String getFileExt(String file_name) {
		int idx = file_name.lastIndexOf(".");
		String Ext = file_name.substring(idx + 1).toLowerCase();
		return Ext;
	}

	/**
	 * 파일정보를 파싱하여 저장하고 파일정보를 리턴한다.
	 * 
	 * @param files
	 *            파일Map
	 * @param storePath
	 *            저장경로
	 * @param essntl
	 *            필수여부
	 * @param fildName
	 *            항목명
	 * @param paramName
	 *            input명
	 * @param maxFileSize
	 *            최대 허용용량
	 * @param arrFileExt
	 *            허용 파일 확장자
	 * @return FileDTO 파일 정보
	 * @throws Exception
	 */
	public FileDTO wfile(Model model, Map<String, MultipartFile> files, String file_path, boolean essntl, String fildName,
			String paramName, long maxFileSize, String[] arrFileExt) throws Exception {

		FileDTO filedto = new FileDTO();
		
		MultipartFile file = files.get(paramName);
		if (null != file && !file.isEmpty()) {

			// 원본 파일명
			String orignl_file_name = file.getOriginalFilename();
			// 원본 파일명이 없는 경우(첨부가 되지 않은 input file type)
			if ("".equals(orignl_file_name)) {
				if (essntl) {
					filedto.setError_msg("파일이 존재하지않습니다.");
					return filedto;
				} else {
					return filedto;
				}
			}

			// 파일 확장자
			String file_ext = getFileExt(orignl_file_name);
			boolean allowExtsn = false;
			for (int i = 0; i < arrFileExt.length; i++) {
				if (arrFileExt[i].equals(file_ext)) {
					allowExtsn = true;
					break;
				}
			}
			if (!allowExtsn) {
				filedto.setError_msg("허용되지않는 파일확장자입니다. ( "+orignl_file_name+" )");
				return filedto;
			}
			// 새로운 파일명
			//String file_name = getNewFileName(orignl_file_name);
			String file_name = orignl_file_name;

			int idx = orignl_file_name.lastIndexOf(".");
			// 확장자를 뺀 파일명
			String onlyOrginFileName = orignl_file_name.substring(0, idx);
			
			File target = new File(file_path, orignl_file_name);
			int k = 1;
			//file중복체크
			 while (target.exists()){
				 orignl_file_name = onlyOrginFileName + "_"+k+"."+file_ext;
				 target = new File(file_path, orignl_file_name);
				 file_name = orignl_file_name;
				 k = k + 1;
			}

			// 파일 사이즈
			long fileSize = file.getSize();
			if (0 != maxFileSize) {
				if (fileSize > maxFileSize) {
					filedto.setError_msg("파일사이즈가 허용초과되었습니다. ( "+getFileSize(Func.cStr(fileSize))+" )");
					return filedto;
				}
			}

			// 파일 저장
			String filePath = file_path + File.separator + file_name;
			file.transferTo(new File(filePath));

			// 파일DTO
			filedto = new FileDTO();
			filedto.setFile_ext(file_ext);
			filedto.setFile_path(file_path);
			filedto.setFile_size(Long.toString(fileSize));
			filedto.setOrignl_file_name(orignl_file_name);
			filedto.setFile_name(file_name);

		} else {
			if (essntl) {
				filedto.setError_msg("파일이 존재하지않습니다.");
				return filedto;
			} else {
				return filedto;
			}
		}

		return filedto;

	}
	
	public static String getFileSize(String size)
	{
		String gubn[] = {"Byte", "KB", "MB" } ;
		String returnSize = new String ();
		int gubnKey = 0;
		double changeSize = 0;
		long fileSize = 0;
		try{
			fileSize =  Long.parseLong(size);
			for( int x=0 ; (fileSize / (double)1024 ) >0 ; x++, fileSize/= (double) 1024 ){
				gubnKey = x;
				changeSize = fileSize;
			}
			returnSize = changeSize + gubn[gubnKey];
		}catch ( Exception ex){ returnSize = "0.0 Byte"; }
		return returnSize;
	}

	/**
	 * 여러개의 첨부파일 등록
	 * 
	 * @param mf
	 * @param file_path
	 * @param maxFileSize
	 * @param arrFileExt
	 * @param prefix
	 * @param replaceFileName
	 * @param replaceExtsn
	 * @return List<FileDTO>
	 * @throws Exception
	 */
	public List<FileDTO> parseFileInfMulti(List<MultipartFile> mf, String file_path, long maxFileSize,
			String[] arrFileExt, String prefix, String replaceFileName, String replaceExtsn) throws Exception {

		List<FileDTO> result = new ArrayList<FileDTO>();

		FileDTO filedto = null;

		MultipartFile file;

		for (int i = 0; i < mf.size(); i++) {

			file = mf.get(i);

			String orignl_file_name = file.getOriginalFilename();
			// 원 파일명이 없는 경우 건너띔 (첨부가 되지 않은 input file type)
			if ("".equals(orignl_file_name)) {
				continue;
			}

			// 파일 확장자
			String file_ext = getFileExt(orignl_file_name);
			boolean allowExtsn = false;
			for (int j = 0; j < arrFileExt.length; j++) {
				if (arrFileExt[j].equals(file_ext)) {
					allowExtsn = true;
					break;
				}
			}
			if (!allowExtsn) {
				System.out.println("허용되지않는파일입니다.");
				//throw processException("fileMng.fail.notallow", new String[] { orignl_file_name });
			}
			// 새로운 파일명
			String file_name = getNewFileName(orignl_file_name);

			// 파일 사이즈
			long _size = file.getSize();
			if (0 != maxFileSize) {
				if (_size > maxFileSize) {
					System.out.println("사이즈오류.");
					//throw processException("fileMng.fail.maxsize",
					//		new String[] { orignl_file_name, String.valueOf(maxFileSize) });
				}
			}

			// 파일 저장
			String filePath = file_path + File.separator + file_name;
			file.transferTo(new File(filePath));

			// 파일VO
			filedto = new FileDTO();

			filedto.setFile_ext(file_ext);
			filedto.setFile_path(file_path);
			filedto.setFile_size(Long.toString(_size));
			filedto.setOrignl_file_name(orignl_file_name);
			filedto.setFile_name(file_name);

			result.add(filedto);

		}

		return result;

	}

	public List<FileDTO> parseFileInf(Map<String, MultipartFile> files, String storePath, String newNameType,
			String prefix) throws Exception {

		// 반환할 파일VO리스트
		List<FileDTO> result = new ArrayList<FileDTO>();

		FileDTO filedto = null;
		String file_path = "";

		// 업로드될 파일이 있다면
		if (!files.isEmpty()) {

			Iterator<Entry<String, MultipartFile>> itr = files.entrySet().iterator();
			MultipartFile file;
			while (itr.hasNext()) {

				Entry<String, MultipartFile> entry = itr.next();
				file = entry.getValue();
				String orignl_file_name = file.getOriginalFilename();

				// 원 파일명이 없는 경우 건너띔 (첨부가 되지 않은 input file type)
				if ("".equals(orignl_file_name)) {
					continue;
				}

				// 파일 확장자
				String file_ext = getFileExt(orignl_file_name);

				// 새로운 파일명
				String file_name = ""; // 새로운 파일명 생성해야됨

				// 파일 사이즈
				long _size = file.getSize();
				file_path = storePath + File.separator + file_name;
				file.transferTo(new File(file_path));

				// 파일VO
				filedto = new FileDTO();
				filedto.setFile_ext(file_ext);
				filedto.setFile_path(file_path);
				filedto.setFile_size(Long.toString(_size));
				filedto.setOrignl_file_name(orignl_file_name);
				filedto.setFile_name(file_name);

				// 파일 저장
				// writeFile(file, newName, storePath);

				// 파일리스트 추가
				result.add(filedto);

			}

		}

		return result;

	}

	/**
	 * 파일을 실제 물리적인 경로에 생성한다.
	 *
	 * @param file
	 * @param newName
	 * @param stordFilePath
	 * @throws Exception
	 */
	protected static void writeFile(MultipartFile file, String newName, String stordFilePath) throws Exception {
		InputStream stream = null;
		OutputStream bos = null;

		try {
			stream = file.getInputStream();
			File cFile = new File(stordFilePath);

			if (!cFile.isDirectory())
				cFile.mkdirs();

			bos = new FileOutputStream(stordFilePath + File.separator + newName);

			int bytesRead = 0;
			byte[] buffer = new byte[BUFF_SIZE];

			while ((bytesRead = stream.read(buffer, 0, BUFF_SIZE)) != -1) {
				bos.write(buffer, 0, bytesRead);
			}
		} catch (Exception e) {
			// e.printStackTrace();
			// throw new RuntimeException(e); // 보안점검 후속조치
			Logger.getLogger(FileUtil.class).debug("IGNORED: " + e.getMessage());
		} finally {
			if (bos != null) {
				try {
					bos.close();
				} catch (Exception ignore) {
					Logger.getLogger(FileUtil.class).debug("IGNORED: " + ignore.getMessage());
				}
			}
			if (stream != null) {
				try {
					stream.close();
				} catch (Exception ignore) {
					Logger.getLogger(FileUtil.class).debug("IGNORED: " + ignore.getMessage());
				}
			}
		}
	}

	/**
	 * 디렉토리를 생성한다
	 * 
	 * @param path
	 * @throws IOException
	 */
	public void mkdir(String path) throws IOException {
		File file = new File(path);
		if (!file.isDirectory()) {
			boolean _flag = file.mkdirs();
			if (!_flag)
				throw new IOException("Directory creation Failed");
		}
	}

	/**
	 * 파일존재여부를 확인한다.
	 * 
	 * @param filePath
	 * @return boolean true:존재, false:존재안함
	 * @throws Exception
	 */
	public boolean isExistsFile(String filePath) throws Exception {
		try {
		File file = new File(filePath);
		return file.exists();
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 디렉토리인지 확인한다.
	 * 
	 * @param dir
	 * @return boolean true:디렉토리임, false:디렉토리아님
	 * @throws Exception
	 */
	public boolean isDirectory(String dir) throws Exception {
		File file = new File(dir);
		return file.isDirectory();
	}

	/**
	 * 실제 물리적인 경로를 확인한다.
	 * 
	 * @param request
	 * @param path
	 * @return String
	 */
	public String realPath(HttpServletRequest request, String path) throws Exception {
		return request.getSession().getServletContext().getRealPath(path);
	}

	/**
	 * 파일을 복사한다.
	 * 
	 * @param request
	 * @param from
	 * @param to
	 * @throws Exception
	 */
	public void copyFile(HttpServletRequest request, String from, String to) throws Exception {

		FileInputStream fis = null;
		FileOutputStream fos = null;
		FileChannel in = null;
		FileChannel out = null;

		try {
			fis = new FileInputStream(realPath(request, from));
			fos = new FileOutputStream(realPath(request, to));
			in = fis.getChannel();
			out = fos.getChannel();

			// 메모리를 사용하여 복사하는 방법
			in.transferTo(0, in.size(), out);

		} catch (Exception e) {
			throw e;
		} finally {
			if (out != null)
				out.close();
			if (in != null)
				in.close();
			if (fos != null)
				fos.close();
			if (fis != null)
				fis.close();
		}

	}

	/**
	 * 파일의 내용을 불러온다
	 * 
	 * @param file
	 *            불러올파일경로
	 * @return String 파일내용
	 */
	public String getFileContents(String file) {

		String enter = System.getProperty("line.separator");

		StringBuffer buf = new StringBuffer();
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
			String line = null;
			while ((line = in.readLine()) != null) {
				buf.append(line);
				buf.append(enter);
			}
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return buf.toString();

	}

	/**
	 * 내용을 저장한다.
	 * 
	 * @param contents
	 *            내용
	 * @param filePath
	 *            저장될 파일경로
	 * @throws Exception
	 */
	public void saveFile(String contents, String filePath) throws Exception {

		try {
			FileOutputStream out = new FileOutputStream(filePath);
			OutputStreamWriter OSW = new OutputStreamWriter(out, "UTF-8");
			OSW.write(contents);
			OSW.close();
		} catch (Exception e) {
			e.getStackTrace();
		}

	}
	

	/**
	 * 폴더체크후 삭제
	 * 
	 * @param contents
	 *            내용
	 * @param filePath
	 *            저장될 파일경로
	 * @throws Exception
	 */
	public static void FolderDelete(String strDir){
		File file = new File(strDir);
		if(file.exists()){
			boolean c = file.delete();
		}
	}
	
	/**
	 * @title : 일반파일업로드용
	 * @method : fileNormalUpload
	 */
	public String fileNormalUpload(Map<String, MultipartFile> files, String filePath, String fileName,
			long maxFileSize, String fileFilter, String fileFilterType) throws Exception {
		
		String target_name = "";
		boolean upload_status	= true;
		
		MultipartFile file = files.get(fileName);
		if (null != file && !file.isEmpty()) {

			// 원본 파일명
			String orignl_file_name = file.getOriginalFilename();
			
			//파일사이즈 체크
			long fileSize = file.getSize();
			if(fileSize > maxFileSize){
				target_name = "nfu_err3:"+fileName;
				upload_status = false;
			}
			
			//파일확장자 체크
			fileFilter = fileFilter.replaceAll(" ", "").toLowerCase();
			String[] ext_arr	= fileFilter.split(",");
			boolean status_tmp	= false;

			String[] filename_arr	= orignl_file_name.toLowerCase().split("\\.");
			String extension		= filename_arr[ filename_arr.length-1 ];

			for(int i=0;i <= ext_arr.length-1;i++){
				if(extension.equals( ext_arr[i] )){
					status_tmp = true;
				}
			}
			if(fileFilterType.toLowerCase().equals("true")){
				if(status_tmp == false){
					target_name = "nfu_err1:"+extension;
					upload_status = false;
				}
			}else{
				if(status_tmp == true){
					target_name = "nfu_err2:"+extension;
					upload_status = false;
				}
			}
			//파일확장자 체크끝
			
			// 새로운 파일명
			String file_name = orignl_file_name;

			int idx = orignl_file_name.lastIndexOf(".");
			// 확장자를 뺀 파일명
			String onlyOrginFileName = orignl_file_name.substring(0, idx);
			
			File target = new File(filePath, orignl_file_name);
			int k = 1;
			//file중복체크
			while (target.exists()){
				orignl_file_name = onlyOrginFileName + "_"+k+"."+extension;
				target = new File(filePath, orignl_file_name);
				file_name = orignl_file_name;
				k = k + 1;
			}
			
			if(upload_status){
				// 파일 저장
				String newfilePath = filePath + File.separator + file_name;
				file.transferTo(new File(newfilePath));
				
				target_name = file_name;
			}
		}
		return target_name;
	}
	
	/**
	 * 총 파일 수를 arraylist에 추가 합니다.
	 * 만약 검색된 부분이 디렉토리라면 하위 폴더를 탐색합니다.
	 */
	public static ArrayList visitAllFiles(ArrayList files, File dir) {

		if(dir.isDirectory()) {
			File[] children = dir.listFiles();
			for(File f : children) {
				// 재귀 호출 사용
				// 하위 폴더 탐색 부분
				visitAllFiles(files,f);
			}
		} else {
			files.add(dir);
		}
		return files;
	}

	public static String getDisposition(String down_filename, String browser_check) throws UnsupportedEncodingException {
		String prefix = "attachment;filename=\"";
		String encodedfilename = null;
		if (browser_check.equals("ie")) {
			encodedfilename = URLEncoder.encode(down_filename, "UTF-8").replaceAll("\\+", "%20");
		} else if (browser_check.equals("chrome")) {
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < down_filename.length(); i++) {
				char c = down_filename.charAt(i);
				if (c > '~') {
					sb.append(URLEncoder.encode("" + c, "UTF-8"));
				} else {
					sb.append(c);
				}
			}
			encodedfilename = sb.toString();
		} else {
			encodedfilename = new String(down_filename.getBytes("UTF-8"), "8859_1");
		}
		return prefix + encodedfilename + "\"";
	}
}