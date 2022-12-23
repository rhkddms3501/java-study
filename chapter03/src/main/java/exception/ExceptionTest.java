package exception;

public class ExceptionTest {

	public static void main(String[] args) {
		int a = 10;
		int b = 10 - a;

		System.out.println("Some Code1...");
		try {
			System.out.println("Some Code2...");
			System.out.println("Some Code3...");
			int result = (1 + 2 + 3) / b;
			System.out.println("Some Code4...");
			System.out.println("Some Code5...");
		} catch (ArithmeticException e) {
//			e.printStackTrace();

			/* 예외 처리 */
			// 1. 로깅
			System.out.println("error: " + e);

			// 2. 사과 (사용자에게)
			System.out.println("사과(안내)페이지 - 미안합니당.. 헤헤");

			// 3. 정상 종료
			// System.exit(0);
			return;

		} finally {
			System.out.println("자원 정리 예: file close...");
		}
		// catch문 밑으로는 코드가 안오는게 좋다..?
		// 와도 리턴문 정도?
		System.out.println("Some Code6...");
		System.out.println("Some Code7...");
	}

}
