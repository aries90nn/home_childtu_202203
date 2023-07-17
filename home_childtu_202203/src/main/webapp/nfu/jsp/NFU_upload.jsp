<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@ page import="com.oreilly.servlet.MultipartRequest"%><%@ page import="com.oreilly.servlet.multipart.DefaultFileRenamePolicy"%><%
String uploaddir		= application.getRealPath("/nfu/data/");
int sizeLimit			= 1000 * 1024 * 1024;

MultipartRequest multi= new MultipartRequest(request, uploaddir, sizeLimit, "UTF-8", new DefaultFileRenamePolicy());
String target_name = multi.getFilesystemName("NFU_add_file");


out.print( target_name );
%>