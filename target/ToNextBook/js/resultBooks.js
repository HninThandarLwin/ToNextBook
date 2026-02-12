/* ================= Render Results ================= */

function renderBookResults(data) {
  const box = document.getElementById("search-result");
  box.innerHTML = "";
  box.className = "book-search";

  if (!data || data.length === 0) {
    box.textContent = "No books found";
    return;
  }

  data.forEach(book => {
    box.appendChild(createBookCard(book));
  });

  scrollToResults("search-result");
}

/******************* Name result *******************/
function renderNameResults(data) {
  renderBookResults(data);
}

/******************* Genre, Tag result *******************/
function renderFilterResults(data) {
  renderBookResults(data);
}

/******************* Like result *******************/
function renderLikeResults(data) {
  const container = document.getElementById("search-result");
  container.innerHTML = "";
  container.className = "like-search";

  if (!data || data.length === 0) {
    const empty = document.createElement("div");
    empty.textContent = "No similar users found";
    container.appendChild(empty);
    return;
  }

  data.forEach(fav => {
    container.appendChild(createFavCard(fav));
  });

  scrollToResults("search-result");
}

/* ================= Components ================= */

/* ---------- Favorite Card ---------- */
function createFavCard(fav) {
  const books = fav.books || [];

  const card = document.createElement("div");
  card.className = "fav-card";

  const header = createFavHeader(fav.userName);
  const body = document.createElement("div");
  body.className = "fav-body";

  body.appendChild(createCommonBox(books));
  body.appendChild(createOtherFavorites(books));

  card.appendChild(header);
  card.appendChild(body);

  return card;
}

/* ---------- Header ---------- */
function createFavHeader(userName) {
  const header = document.createElement("div");
  header.className = "fav-header";

  const avatar = document.createElement("div");
  avatar.className = "user-avatar";
  avatar.textContent = userName ? userName.charAt(0) : "?";

  const info = document.createElement("div");
  info.className = "user-info";

  const name = document.createElement("h3");
  name.textContent = userName || "Unknown";

  const match = document.createElement("div");
  match.className = "match-rate";
  match.textContent = "Taste Match";

  info.appendChild(name);
  info.appendChild(match);

  header.appendChild(avatar);
  header.appendChild(info);

  return header;
}

/* ---------- Common interest ---------- */
function createCommonBox(books) {
  const box = document.createElement("div");
  box.className = "common-box";

  const label = document.createElement("div");
  label.textContent = "COMMON INTERESTS:";

  const tag = document.createElement("div");
  tag.className = "common-tag";
  tag.textContent =
    books[0]?.bookName_en ||
    books[0]?.bookName_jp ||
    "Shared";

  box.appendChild(label);
  box.appendChild(tag);

  return box;
}

/* ---------- Other favorites ---------- */
function createOtherFavorites(books) {
  const wrap = document.createElement("div");

  const title = document.createElement("div");
  title.className = "other-title";
  title.textContent = "Their Other Favorites:";

  const grid = document.createElement("div");
  grid.className = "book-grid";

  books.forEach(book => {
    grid.appendChild(createBookCard(book));
  });

  wrap.appendChild(title);
  wrap.appendChild(grid);

  return wrap;
}

/* ---------- Book card ---------- */
function createBookCard(book) {
  const card = document.createElement("div");
  card.className = "book-card";

  const img = document.createElement("img");
  img.className = "book-cover";

  const ctx = document.body.dataset.ctx || "";

  img.src = book.bookCover
    ? ctx + "/img/" + book.bookCover
    : ctx + "/img/default.jpg";

  img.alt =
    (book.bookName_en || book.bookName_jp || "Book") + " cover";

  const info = document.createElement("div");
  info.className = "book-info";

  const status = document.createElement("div");
  status.className = "book-status";
  status.textContent = book.bookStatus || "";

  const title = document.createElement("div");
  title.className = "book-title";
  title.textContent = book.bookName_en || book.bookName_jp || "";

  const jp = document.createElement("div");
  jp.className = "book-jp";
  jp.textContent = book.bookName_jp || "";

  info.appendChild(status);
  info.appendChild(title);
  info.appendChild(jp);

  card.appendChild(img);
  card.appendChild(info);

  return card;
}

/* ---------- Scroll Move ---------- */
function scrollToResults(elementId) {
  const el = document.getElementById(elementId);
  if (!el) return;

  el.scrollIntoView({
    behavior: "smooth",
    block: "start"
  });
}
