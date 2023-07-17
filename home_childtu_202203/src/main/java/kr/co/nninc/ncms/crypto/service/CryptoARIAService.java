package kr.co.nninc.ncms.crypto.service;

import java.io.UnsupportedEncodingException;

import org.apache.commons.codec.binary.Base64;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

import egovframework.rte.fdl.cryptography.EgovCryptoService;
import egovframework.rte.fdl.cryptography.EgovPasswordEncoder;
//import org.apache.commons.net.util.Base64;
import kr.co.nninc.ncms.common.CommonConfig;

@Service("cryptoService")
public class CryptoARIAService {
	private String plainPassword = "ncmsv2";
	
	ApplicationContext context = new ClassPathXmlApplicationContext("egovframework/spring/com/context-ariacrypto.xml");

	private EgovCryptoService cryptoService = (EgovCryptoService) context.getBean("ARIACryptoService");
	//@Resource(name="ARIACryptoService")
	//EgovCryptoService cryptoService;

	private EgovPasswordEncoder passwordEncoder = (EgovPasswordEncoder) context.getBean("passwordEncoder");
	//@Resource(name="passwordEncoder")
	//private EgovPasswordEncoder passwordEncoder;


	public String encryptData(String plainText) throws Exception {
		String encodeText = null;
		String passwdAlgorithm = CommonConfig.get("crypto.password.algorithm");
	
		try {
			byte[] encrypted = cryptoService.encrypt(plainText.getBytes("UTF-8"), plainPassword);
			encodeText = Base64.encodeBase64String(encrypted);
		}catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	
			return encodeText;
	}
	
	public String decryptData(String encodeText) throws Exception {
		String plainText = null;
		String passwdAlgorithm = CommonConfig.get("crypto.password.algorithm");
	
		try {
			byte[] base64dec = Base64.decodeBase64(encodeText);
			byte[] decrypted = cryptoService.decrypt(base64dec, plainPassword);
		
			plainText = new String(decrypted, "UTF-8");
		}catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	
		return plainText;
	}
}
