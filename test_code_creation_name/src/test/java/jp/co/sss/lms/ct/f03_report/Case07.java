package jp.co.sss.lms.ct.f03_report;

import static jp.co.sss.lms.ct.util.WebDriverUtils.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.amazonaws.services.guardduty.model.Evidence;

/**
 * 結合テスト レポート機能
 * ケース07
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース07 受講生 レポート新規登録(日報) 正常系")
public class Case07 {

	/** 前処理 */
	@BeforeAll
	static void before() {
		createDriver();
	}

	/** 後処理 */
	@AfterAll
	static void after() {
		closeDriver();
	}

	@Test
	@Order(1)
	@DisplayName("テスト01 トップページURLでアクセス")
	void test01() {
		goTo("http://localhost:8080/lms");
		//表示されているタイトルの確認
		assertEquals("ログイン | LMS", webDriver.getTitle());
		//URLの確認
		assertEquals("http://localhost:8080/lms/", webDriver.getCurrentUrl());
		//エビデンス取得
		getEvidence(new Evidence() {
		});
	}

	@Test
	@Order(2)
	@DisplayName("テスト02 初回ログイン済みの受講生ユーザーでログイン")
	void test02() {
		//画面入力
		WebElement idElement = webDriver.findElement(By.id("loginId"));
		idElement.clear(); // 初期値をクリア
		idElement.sendKeys("StudentAA01");

		WebElement passElement = webDriver.findElement(By.id("password"));
		passElement.clear(); // 初期値をクリア
		passElement.sendKeys("StudentAA0");

		WebElement loginElement = webDriver.findElement(By.className("btn-primary"));
		loginElement.click();

		//遷移後の画面確認
		//表示されているタイトルの確認
		visibilityTimeout(By.className("breadcrumb"), 10);
		assertEquals("コース詳細 | LMS", webDriver.getTitle());
		//URLの確認
		assertEquals("http://localhost:8080/lms/course/detail", webDriver.getCurrentUrl());
		//エビデンス取得
		getEvidence(new Evidence() {
		});
	}

	@Test
	@Order(3)
	@DisplayName("テスト03 未提出の研修日の「詳細」ボタンを押下しセクション詳細画面に遷移")
	void test03() {
		//セクション全体の取得
		final List<WebElement> sectionElements = webDriver
				.findElements(By.cssSelector("table.table-hover.sctionList tr"));
		//セクションを個別に取得
		for (int i = 0; i < sectionElements.size(); i++) {
			WebElement result = webDriver.findElements(By.cssSelector("table.table-hover.sctionList tr")).get(i);
			WebElement sectionIdElement = result.findElement(By.cssSelector("input[name='sectionId']"));
			String sectionId = sectionIdElement.getAttribute("value");
			//未提出のセクション詳細ボタンを押下
			if (sectionId.equals("4")) {
				scrollBy("300");
				result.findElement(By.cssSelector("input[type='submit']")).click();
				visibilityTimeout(By.className("breadcrumb"), 10);
				assertEquals("セクション詳細 | LMS", webDriver.getTitle());
				//URLの確認
				assertEquals("http://localhost:8080/lms/section/detail", webDriver.getCurrentUrl());
				//エビデンス取得
				getEvidence(new Evidence() {
				});
				break;
			}

		}
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 「提出する」ボタンを押下しレポート登録画面に遷移")
	void test04() {
		WebElement reportElement = webDriver
				.findElement(By.cssSelector("table tr input[type='submit'][value='日報【デモ】を提出する']"));
		reportElement.click();
		visibilityTimeout(By.className("bs-component"), 10);
		assertEquals("レポート登録 | LMS", webDriver.getTitle());
		//URLの確認
		assertEquals("http://localhost:8080/lms/report/regist", webDriver.getCurrentUrl());
		//エビデンス取得
		getEvidence(new Evidence() {
		});
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 報告内容を入力して「提出する」ボタンを押下し確認ボタン名が更新される")
	void test05() {
		//「提出する」ボタンを取得
		WebElement reportrRegistElement = webDriver
				.findElement(By.cssSelector(".well.bs-component button[type='submit'].btn.btn-primary"));
		//「提出する」ボタンを押下し,セクション詳細画面に遷移
		reportrRegistElement.click();
		visibilityTimeout(By.className("breadcrumb"), 10);
		assertEquals("セクション詳細 | LMS", webDriver.getTitle());
		assertEquals("提出済み日報【デモ】を確認する", webDriver
				.findElement(By.cssSelector("table tr input[type='submit'][value='提出済み日報【デモ】を確認する']"))
				.getAttribute("value"));
		//エビデンス取得
		getEvidence(new Evidence() {
		});
	}

}
