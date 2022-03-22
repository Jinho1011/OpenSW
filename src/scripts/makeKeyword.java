package scripts;

/**
 * 3주차 실습 코드
 * 
 * kkma 형태소 분석기를 이용하여 index.xml 파일을 생성하세요.
 * 
 * index.xml 파일 형식은 아래와 같습니다.
 * (키워드1):(키워드1에 대한 빈도수)#(키워드2):(키워드2에 대한 빈도수)#(키워드3):(키워드3에 대한 빈도수) ... 
 * e.g., 라면:13#밀가루:4#달걀:1 ...
 * 
 * input : collection.xml
 * output : index.xml 
 */

public class makeKeyword {

	private String input_file;
	private String output_flie = "./index.xml";
	
	public makeKeyword(String file) {
		this.input_file = file;
	}

	public void convertXml() {
		System.out.println("3주차 실행완료");
	}

}
