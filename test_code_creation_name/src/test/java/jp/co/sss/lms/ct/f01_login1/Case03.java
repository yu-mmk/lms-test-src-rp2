package jp.co.sss.lms.ct.f01_login1;

import static jp.co.sss.lms.ct.util.WebDriverUtils.*;
import static org.junit.jupiter.api.Assertions.*;

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
 * 結合テスト ログイン機能①
 * ケース03
 * @author みまき
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース03 受講生 ログイン 正常系")
public class Case03 {

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
		webDriver.get("http://localhost:8080/lms");
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

}
