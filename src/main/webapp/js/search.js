
document.addEventListener("DOMContentLoaded", () => {
	loadGenres();
	loadTags();
});

function loadGenres() {
	fetch(ctx + "/api/meta?type=genre")
		.then(res => res.json())
		.then(genres => {
			const select = document.getElementById("genreSelect");

			genres.forEach(g => {
				const opt = document.createElement("option");
				opt.value = g.id;               // IMPORTANT
				opt.textContent = g.name;
				select.appendChild(opt);
			});
			//			console.log("Genres from backend:", genres);
		});
}

function loadTags() {
	fetch(ctx + "/api/meta?type=tag")
		.then(res => res.json())
		.then(tags => {
			const select = document.getElementById("tagSelect");

			tags.forEach(t => {
				const opt = document.createElement("option");
				opt.value = t.id;               // IMPORTANT
				opt.textContent = t.name;
				select.appendChild(opt);
			});
		});
}

document.getElementById("nameSearchBtn").addEventListener("click", () => {
	const query = document.getElementById("nameInput").value.trim();

	if (!query) return;

	console.log("name-keyword :" + query);
	fetch(ctx + `/api/search/name?keyword=${encodeURIComponent(query)}`)
		.then(res => res.json())
		.then(data => {
			console.log("Name search result:", data);
			renderNameResults(data);
		});
});

document.getElementById("filterSearchBtn").addEventListener("click", () => {
	const genreSelect = document.getElementById("genreSelect");
	const tagSelect = document.getElementById("tagSelect");
	const genres = Array.from(genreSelect.selectedOptions).map(o => Number(o.value));
	const tags = Array.from(tagSelect.selectedOptions).map(o => Number(o.value));

	fetch(ctx +  "/api/search/filter", {
		method: "POST",
		headers: {
			"Content-Type": "application/json"
		},
		body: JSON.stringify({
			genres: genres,
			tags: tags
		})
	})
		.then(res => res.json())
		.then(data => {
			console.log("Filter search result:", data);
			renderFilterResults(data);
		});
});

let likingList = [];

document.getElementById("addSearchBtn").addEventListener("click", () => {
	const input = document.getElementById("likingSearch");
	const name = input.value.trim();
	if (!name) return;

	// avoid duplicates
	if (likingList.includes(name)) return;

	likingList.push(name);
	input.value = "";
	renderLikingList();
});
function renderLikingList() {
	const box = document.getElementById("novelNames");
	box.innerHTML = "";

	likingList.forEach((name, index) => {
		const chip = document.createElement("div");
		chip.className = "liking-chip";

		chip.innerHTML = `
            <span class="chip-text">${name}</span>
            <button class="chip-remove">×</button>
        `;

		chip.querySelector(".chip-remove").addEventListener("click", () => {
			likingList.splice(index, 1);
			renderLikingList();
		});

		box.appendChild(chip);
	});
}

document.getElementById("likeSearchBtn").addEventListener("click", () => {
	if (likingList.length === 0) return;

	const params = new URLSearchParams();

	likingList.forEach(name => {
		params.append("books", name);
	});

	fetch(ctx + "/similarSearch", {
		method: "POST",
		headers: {
			"Content-Type": "application/x-www-form-urlencoded"
		},
		body: params.toString()
	})
		.then(res => res.json())  
		.then(data => {
			console.log("Like search result:", data);
			renderLikeResults(data);  
		});
});
