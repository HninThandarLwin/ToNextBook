
function loadRandomBook() {
    fetch(ctx +'/RandomBook')
        .then(response => response.json())
        .then(data => {

            document.querySelector('.title').innerText = data.title;
            document.querySelector('.status').innerText =
                'Novel Status - ' + data.status;

            document.querySelector('.description').innerText =
                data.description;

            document.getElementById('image').src =
               ctx + '/img/' + data.image;
			   
		   document.getElementById('image').alt =
		       data.title_en;
        })
        .catch(err => console.error(err));
}

// first load
loadRandomBook();

// every 60 seconds
setInterval(loadRandomBook, 60000);
