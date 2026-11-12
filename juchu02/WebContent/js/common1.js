/*
 * ユーザー登録入力チェック
 */
function formCheck() {
	var btn = document.form1.onbtn.value;
	var className = document.form1.classname.value;
	if (btn == "user") {
		var kokyakuname = 0;
		var kokyakukana = 0;
		var zip1 = 0;
		var zip2 = 0;
		var kenmap = 0;
		var address1 = 0;
		var address2 = 0;
		var tel1 = 0;
		var tel2 = 0;
		var tel3 = 0;
		var mail1 = 0;
		var mail2 = 0;
		var password1 = 0;
		var password2 = 0;
		var tantouname = 0;
		var year = 0;
		var month = 0;
		var day = 0;

		passlength = document.form1.password1.value.length;

		if (document.form1.kokyakuname.value == "") {
			kokyakuname = 1;
		} else if (document.form1.kokyakukana.value == "") {
			kokyakukana = 1;
		} else if (document.form1.zip1.value == "") {
			zip1 = 1;
		} else if (document.form1.zip2.value == "") {
			zip2 = 1;
		} else if (document.form1.kenmap.value == "") {
			kenmap = 1;
		} else if (document.form1.address1.value == "") {
			address1 = 1;
		} else if (document.form1.address2.value == "") {
			address2 = 1;
		} else if (document.form1.tel1.value == "") {
			tel1 = 1;
		} else if (document.form1.tel2.value == "") {
			tel2 = 1;
		} else if (document.form1.tel3.value == "") {
			tel3 = 1;
		} else if (document.form1.mail1.value == "") {
			mail1 = 1;
		} else if (document.form1.mail2.value == "") {
			mail2 = 1;
		} else if (document.form1.password1.value == "") {
			password1 = 1;
		} else if (document.form1.password2.value == "") {
			password2 = 1;
		}

		if (className == "Houjin") {
			if (document.form1.tantouname.value == "") {
				tantouname = 1;
			}
		} else {
			if (document.form1.year.value == "") {
				year = 1;
			} else if (document.form1.month.value == "") {
				month = 1;
			} else if (document.form1.day.value == "") {
				day = 1;
			}
		}

		// 必須チェック
		if (kokyakuname) {
			if (className == "Houjin") {
				document.getElementById("errorMessage").innerHTML = "会社名・団体名を入力してください。";
			} else {
				document.getElementById("errorMessage").innerHTML = "氏名を入力してください。";
			}
			return false;
		} else if (kokyakukana) {
			if (className == "Houjin") {
				document.getElementById("errorMessage").innerHTML = "会社カナ名を入力してください。";
			} else {
				document.getElementById("errorMessage").innerHTML = "カナ名を入力してください。";
			}
			return false;
		} else if (zip1 || zip2) {
			document.getElementById("errorMessage").innerHTML = "郵便番号を入力してください。";
			return false;
		} else if (kenmap) {
			document.getElementById("errorMessage").innerHTML = "都道府県を選択してください。";
			return false;
		} else if (address1) {
			document.getElementById("errorMessage").innerHTML = "住所１を入力してください。";
			return false;
		} else if (address2) {
			document.getElementById("errorMessage").innerHTML = "住所２を入力してください。";
			return false;
		} else if (tel1 || tel2 || tel3) {
			document.getElementById("errorMessage").innerHTML = "電話番号を入力してください。";
			return false;
		} else if (tantouname) {
			document.getElementById("errorMessage").innerHTML = "担当者を入力してください。";
			return false;
		} else if (year || month || day) {
			document.getElementById("errorMessage").innerHTML = "誕生日を入力してください。";
			return false;
		} else if (mail1) {
			document.getElementById("errorMessage").innerHTML = "メールアドレスを入力してください。";
			return false;
		} else if (mail2) {
			document.getElementById("errorMessage").innerHTML = "メールアドレス（確認）を入力してください。";
			return false;
		} else if (password1) {
			document.getElementById("errorMessage").innerHTML = "パスワードを入力してください。";
			return false;
		} else if (password2) {
			document.getElementById("errorMessage").innerHTML = "パスワード（確認）を入力してください。";
			return false;
			// 文字数チェック
		} else if (8 > passlength || 20 < passlength) {
			document.getElementById("errorMessage").innerHTML = "パスワードは８文字～２０文字で入力してください。";
			return false;
			// 同一チェック
		} else if (document.form1.mail1.value != document.form1.mail2.value) {
			document.getElementById("errorMessage").innerHTML = "メールアドレスが一致しません。";
			return false;
		} else if (document.form1.password1.value != document.form1.password2.value) {
			document.getElementById("errorMessage").innerHTML = "パスワードが一致しません。";
			return false;
		} else {
			return true;
		}
	}
}

/*
 * 選択ボタンセット
 */
function setValue(val) {
	document.form1.onbtn.value = val;
	return false;
}

/*
 * 商品コードセット
 */
function setCD(val) {
	document.form3.hiddenCD.value = val;
	document.form3.target = "_blank";
	document.form3.submit();
	return false;
}

/*
 * 次ページの表示先をデフォルトにクリア
 */
function clearTarget() {
	if (document.form3.target != "_blank")
		return;
	document.form3.target = "_self";
}