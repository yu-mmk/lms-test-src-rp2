package jp.co.sss.lms.ct.f02_faq;

import static jp.co.sss.lms.ct.util.WebDriverUtils.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.amazonaws.services.guardduty.model.Evidence;

/**
 * 結合テスト よくある質問機能
 * ケース04
 * @author みまき
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース04 よくある質問画面への遷移")
public class Case04 {

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

	@Test
	@Order(3)
	@DisplayName("テスト03 上部メニューの「ヘルプ」リンクからヘルプ画面に遷移")
	void test03() {
		//機能ボタンをクリック
		WebElement buttonlinkTextElement = webDriver.findElement(By.linkText("機能"));
		buttonlinkTextElement.click();
		pageLoadTimeout(10);
		//ヘルプリンクをクリック
		WebElement linkTextElement = webDriver.findElement(By.linkText("ヘルプ"));
		linkTextElement.click();
		//ヘルプ画面に遷移
		visibilityTimeout(By.className("panel-heading"), 10);
		assertEquals("ヘルプ | LMS", webDriver.getTitle());
		//URLの確認
		assertEquals("http://localhost:8080/lms/help", webDriver.getCurrentUrl());
		//エビデンス取得
		getEvidence(new Evidence() {
		});
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 「よくある質問」リンクからよくある質問画面を別タブに開く")
	void test04() {
		// 現在のタブを保存
		String originalWindow = webDriver.getWindowHandle();

		WebElement linkTextElement = webDriver.findElement(By.linkText("よくある質問"));
		linkTextElement.click();
		//質問画面に遷移
		final WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
		wait.until(driver -> driver.getWindowHandles().size() == 2);
		// 新しいタブへ切り替える
		for (String windowHandle : webDriver.getWindowHandles()) {
			if (!windowHandle.equals(originalWindow)) {
				webDriver.switchTo().window(windowHandle);
				break;
			}
		}
		visibilityTimeout(By.className("well"), 10);
		assertEquals("よくある質問 | LMS", webDriver.getTitle());
		//URLの確認
		assertEquals("http://localhost:8080/lms/faq", webDriver.getCurrentUrl());
		//エビデンス取得
		getEvidence(new Evidence() {
		});

	}

}
