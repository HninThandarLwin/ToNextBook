const ctx = document.body.dataset.ctx || "";
console.log("CTX =", ctx);

document.addEventListener("DOMContentLoaded", () => {
    
    const openBtn = document.getElementById("open-login");
    const modal = document.getElementById("login-modal");
    const closeBtn = document.getElementById("login-close");
	const form = document.getElementById("login-form");

    if (!modal) {
        console.error("login-modal not found");
        return;
    }

    // Open popup
    if (openBtn) {
        openBtn.addEventListener("click", (e) => {
            e.preventDefault();
            console.log("Opening login popup");
            modal.classList.remove("hidden");
        });
    }

    // Close popup
    if (closeBtn) {
        closeBtn.addEventListener("click", () => {
            modal.classList.add("hidden");
        });
    }
	form.addEventListener("submit", (e) => {
	    e.preventDefault(); // stop normal submit

	    const formData = new FormData(form);

	    fetch(ctx + "/login", {
	        method: "POST",
	        body: formData
	    })
	    .then(res => {
	        if (res.ok) {
				const toast = document.getElementById("login-toast");

				   toast.classList.remove("hidden");
				   toast.classList.add("show");

				   // hide after 1.2 sec
				   setTimeout(() => {
				       toast.classList.remove("show");
				       toast.classList.add("hide");
				   }, 1200);

				   // reload after animation
				   setTimeout(() => {
				       location.reload();
				   }, 1600);
				   
	            location.reload(); // session set → header updates
	        } else {
	            document.getElementById("login-error").textContent = "Login failed";
	        }
	    });
		console.log("e.target : ");
		console.log(e.target);
	});

});
