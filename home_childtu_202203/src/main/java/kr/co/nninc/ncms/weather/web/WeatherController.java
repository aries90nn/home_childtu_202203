package kr.co.nninc.ncms.weather.web;

import java.text.DecimalFormat;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import kr.co.nninc.ncms.common.Func;

@Controller
public class WeatherController {
	@RequestMapping(value = "/weather.do")
	public String write(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		model.addAttribute("request", request);

		boolean flag_xml = false; // xml 읽어올지 여부

		String ck_weather_tm = Func.getCookie(request, "ck_weather_tm");
		String ck_weather_str = Func.getCookie(request, "ck_weather_str");

		if ("".equals(ck_weather_tm)) { // 처음로딩이면
			flag_xml = true;
		} else { // xml 읽어온적 있으면

			// 기상청 : 3시간마다 발표됨
			// 발표시간 쿠키값+3시간 이 지났으면 신규 예보가 있는것이므로 xml 다시 읽어온다
			int ck_y = Func.cInt(ck_weather_tm.substring(0, 4));
			int ck_m = Func.cInt(ck_weather_tm.substring(4, 6));
			int ck_d = Func.cInt(ck_weather_tm.substring(6, 8));
			int ck_h = Func.cInt(ck_weather_tm.substring(8, 10));

			Calendar cal2 = Calendar.getInstance();
			cal2.set(ck_y, ck_m - 1, ck_d, ck_h, 0);
			cal2.add(cal2.HOUR_OF_DAY, 3); // 3시간후

			Calendar calnow = Calendar.getInstance();

			if (calnow.compareTo(cal2) > 0) {
				flag_xml = true;
			}
		}

		// xml 읽어오기
		if (flag_xml) {

			// out.print("xml_read");

			// 초기화
			ck_weather_tm = "";
			ck_weather_str = "";

			try {
				DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
				DocumentBuilder builder = factory.newDocumentBuilder();
				Document doc = builder.parse("http://www.kma.go.kr/wid/queryDFSRSS.jsp?zone=4713066000"); // 경상북도 경주시
				Element root = doc.getDocumentElement();
				NodeList nlData = root.getElementsByTagName("data");

				// rss 에서 day 값 0:오늘/1:내일/2:모레 는 발표일 기준이라서 발표일 값이 필요
				String header_tm = root.getElementsByTagName("tm").item(0).getTextContent(); // 발표일 (ex 201601202300)
				int tm_y = Func.cInt(header_tm.substring(0, 4));
				int tm_m = Func.cInt(header_tm.substring(4, 6));
				int tm_d = Func.cInt(header_tm.substring(6, 8));

				Calendar cal = Calendar.getInstance();
				cal.set(tm_y, tm_m - 1, tm_d); // 발표일 세팅

				// 첫번째 예보 날씨
				Node bNode1 = nlData.item(0);
				Element bElement1 = (Element) bNode1;
				String day1_temp = bElement1.getElementsByTagName("temp").item(0).getTextContent() + "℃";
				String day1_hour = bElement1.getElementsByTagName("hour").item(0).getTextContent();
				String day1_wfKor = bElement1.getElementsByTagName("wfKor").item(0).getTextContent();
				String day1_wfEn = bElement1.getElementsByTagName("wfEn").item(0).getTextContent();

				int int_hout = Func.cInt(day1_hour);
				if (int_hout == 24)
					int_hout = 0;
				if (int_hout >= 12) {
					if (int_hout > 12)
						int_hout = int_hout - 12;
					day1_hour = Func.cStr(int_hout) + " pm";
				} else {
					day1_hour = Func.cStr(int_hout) + " am";
				}

				// 오늘,내일,모레 최저/최고기온 조회
				// 당일자료는 없는게 대부분 (최저/최고기온 지난시간이면 해당값은 없음)
				float[] tempMax = new float[3]; // 일자 죄처기온
				float[] tempMin = new float[3]; // 일자 죄고기온
				DecimalFormat format = new DecimalFormat("0");
				int max_day = 0;

				String[] tempWfEn = new String[3]; // 오후3시날씨 (일별날씨가 없어서..이걸로쓰자)
				String[] tempWfKor = new String[3]; //

				for (int i = 0; i < 20; i++) {
					Node bNode = nlData.item(i);
					if (bNode == null) {
						break;
					} else {
						Element bElement = (Element) bNode;
						int day = Func.cInt(bElement.getElementsByTagName("day").item(0).getTextContent());
						tempMax[day] = Func.cflt(bElement.getElementsByTagName("tmx").item(0).getTextContent());
						tempMin[day] = Func.cflt(bElement.getElementsByTagName("tmn").item(0).getTextContent());
						max_day = day;

						int tempDay = Func.cInt(bElement.getElementsByTagName("hour").item(0).getTextContent());
						if (tempDay == 15) {
							tempWfEn[day] = bElement.getElementsByTagName("wfEn").item(0).getTextContent();
							tempWfKor[day] = bElement.getElementsByTagName("wfKor").item(0).getTextContent();
						}
					}
				}

				// 쿠키값 생성
				ck_weather_str = ck_weather_str + day1_wfKor + "@" + day1_wfEn + "@" + day1_hour + "@" + day1_temp
						+ "##";

				int start_i = 0;
				int end_i = 0;
				if (max_day < 2) {
					start_i = 0;
					end_i = 1;
				} else {
					start_i = 1;
					end_i = 2;
					cal.add(cal.DATE, 1);
				}
				for (int i = start_i; i <= end_i; i++) {

					String str_tmx = "";
					if (tempMax[i] < -100) {
						str_tmx = "-";
					} else {
						str_tmx = Func.cStr(format.format(tempMax[i]));
					}

					String str_tmn = "";
					if (tempMin[i] < -100) {
						str_tmn = "-";
					} else {
						str_tmn = Func.cStr(format.format(tempMin[i]));
					}

					// 쿠키값 생성
					ck_weather_str = ck_weather_str + tempWfKor[i] + "@" + tempWfEn[i] + "@" + getDayWeekEng(cal) + "@"
							+ str_tmn + "/" + str_tmx + "##";

					cal.add(cal.DATE, 1);
				}
				// 쿠키 저장
				Func.setCookie(request, response, "ck_weather_tm", header_tm);
				Func.setCookie(request, response, "ck_weather_str", ck_weather_str);
			} catch (Exception e) {
				throw e;
			}
		} else {
			// out.print("no_read");
		}

		return "/site/common/file/weather";
	}

	public static String getWeatherIcon(String wfKor, String wfEn) throws Exception {
		String wIcon = wfEn.replaceAll(" ", "_").replaceAll("/", "");
		return "<img src=\"/img/common/t_weather_" + wIcon + ".gif\" alt=\"" + wfKor + "\" />";
	}

	public static String getDayWeekEng(Calendar cal) throws Exception {
		String strWeek_eng = "";
		switch (cal.get(Calendar.DAY_OF_WEEK)) {
		case 1:
			strWeek_eng = "Sun";
			break;
		case 2:
			strWeek_eng = "Mon";
			break;
		case 3:
			strWeek_eng = "Tue";
			break;
		case 4:
			strWeek_eng = "Wed";
			break;
		case 5:
			strWeek_eng = "Thu";
			break;
		case 6:
			strWeek_eng = "Fri";
			break;
		case 7:
			strWeek_eng = "Sat";
			break;
		}
		return strWeek_eng;
	}
}
