package kr.co.nninc.ncms.common;

import java.io.Serializable;

/**
 * 파일정보 클래스
 * 
 * @author 나눔
 * @since 2017.09.04
 * @version 1.0
 */
public class FileDTO  implements Serializable {

	private static final long serialVersionUID = 5740378415853886868L;

	/** 원파일명 */
	private String orignl_file_name;

	/** 파일크기 */
	private String file_size;

	/** 저장파일명 */
	private String file_name;

	/** 파일저장경로 */
	private String file_path;

	/** 파일확장자 */
	private String file_ext;

	/** 파일순서 */
	private int file_num;

	/** 에러메시지 */
	private String error_msg;

	public String getOrignl_file_name() {
		return orignl_file_name;
	}

	public void setOrignl_file_name(String orignl_file_name) {
		this.orignl_file_name = orignl_file_name;
	}

	public String getFile_size() {
		return file_size;
	}

	public void setFile_size(String file_size) {
		this.file_size = file_size;
	}

	public String getFile_name() {
		return file_name;
	}

	public void setFile_name(String file_name) {
		this.file_name = file_name;
	}

	public String getFile_path() {
		return file_path;
	}

	public void setFile_path(String file_path) {
		this.file_path = file_path;
	}

	public String getFile_ext() {
		return file_ext;
	}

	public void setFile_ext(String file_ext) {
		this.file_ext = file_ext;
	}

	public int getFile_num() {
		return file_num;
	}

	public void setFile_num(int file_num) {
		this.file_num = file_num;
	}

	public String getError_msg() {
		return error_msg;
	}

	public void setError_msg(String error_msg) {
		this.error_msg = error_msg;
	}
}