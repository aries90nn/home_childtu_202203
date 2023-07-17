package kr.co.nninc.ncms.crypto.web;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import kr.co.nninc.ncms.crypto.service.CryptoARIAService;

@Controller
public class CryptoARIAController {
	//private static final Logger
	
	@Resource(name="cryptoService")
	private CryptoARIAService cryptoService;

	
	@RequestMapping(value = { "/copycoding/cryptoAriaTest.do","/*/copycoding/cryptoAriaTest.do" })
	public void cryptoEncryptTest(ModelMap model) {
		String plainText = "암호화 해보아요!";
		
		String encrypText;
		try {
			encrypText = cryptoService.encryptData(plainText);
			System.out.println("encrypText : "+encrypText);
			
			plainText = cryptoService.decryptData(encrypText);
			System.out.println("decryptData : "+plainText);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
