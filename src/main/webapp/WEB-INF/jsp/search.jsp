<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!-- ================= Search Page ================= -->

<section class="novel-search-page">

	<!-- ================= Classic Search ================= -->
	<section class="search-card">
		<h2>🔍 小説検索</h2>

		<div class="search-bar">
			<input type="text" id="nameInput" placeholder="小説名・作者名を入力" />
			<button id="nameSearchBtn">検索</button>
		</div>
	</section>

	<!-- ================= Genre & Tag Search ================= -->
	<section class="search-card">
		<h2>🎯 ジャンル・タグ検索</h2>

		<div class="filter-box">
			<div class="filter-item">
				<label>ジャンル</label> <select id="genreSelect" multiple></select>
			</div>

			<div class="filter-item">
				<label>タグ</label> <select id="tagSelect" multiple></select>
			</div>
		</div>

		<div class="filter-action">
			<button id="filterSearchBtn">条件で検索</button>
		</div>

	</section>

	<!-- ================= Same-Liking Search ================= -->
	<section class="search-card highlight">
		<h2>🤍 好みが似ている人を探す</h2>
		<p class="sub-text">好きな小説を入力すると、好みが似ているユーザーを探します</p>

		<div class="liking-box">
			<input type="text" id="likingSearch" placeholder="好きな小説名を入力" />
			<button id="addSearchBtn">追加</button>
		</div>


		<div id="novelNames" class="liking-list">
			<span class="chip-text"></span>
			<button class="chip-remove"></button>
		</div>

		<div class="liking-action">
			<button id="likeSearchBtn">ユーザー検索</button>
		</div>

	</section>

	<section class="result-section">

		<div class="result-header">
			<h2>Search Results</h2>
			<span id="result-count"></span>
		</div>

		<div class="result-body">
			<div id="search-result" class="search-result"></div>
		</div>

	</section>

        <div class="scroll-top">
            <p><a href="#"><img src="img/top_button.png" width="50" height="50" alt="TOPへ戻る"></a></p>
        </div>

</section>
