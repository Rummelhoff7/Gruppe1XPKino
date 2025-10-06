// -------------------- Button Hover --------------------
const buttons = document.querySelectorAll('.theaterbtn button, .movietimebtn button');
buttons.forEach(button => {
    button.addEventListener('mouseenter', () => button.style.transform = 'scale(1.1)');
    button.addEventListener('mouseleave', () => button.style.transform = 'scale(1)');
});

// -------------------- Theater Selection --------------------
function chooseTheater(theaterId) {
    localStorage.setItem("selectedTheater", theaterId);
    window.location.href = "/screening_movie_selector.html";
}

// -------------------- Movie Selection --------------------
const movieRow = document.querySelector(".movie-row");
if (movieRow) {
    fetch("/api/screening_movies")
        .then(res => res.json())
        .then(movies => {
            movies.forEach(movie => {
                const card = document.createElement("div");
                card.className = "movie-card";

                const img = document.createElement("img");
                img.src = "/images/" + movie.img;
                img.alt = movie.movieTitle;

                const title = document.createElement("p");
                title.textContent = movie.movieTitle;

                card.appendChild(img);
                card.appendChild(title);
                movieRow.appendChild(card);

                card.addEventListener("click", () => {
                    localStorage.setItem("selectedMovie", movie.id);
                    window.location.href = "/screening_times.html";
                });
            });
        });
}

// -------------------- Time Selection --------------------
function movieTime(timeStr) {
    localStorage.setItem("selectedTime", timeStr);
    window.location.href = "/screening_date.html";
}

// -------------------- Date Selection and Save Show --------------------
const saveDatesButton = document.getElementById("save-dates");
if (saveDatesButton) {
    saveDatesButton.addEventListener("click", () => {
        const startDate = document.getElementById("start-date").value;
        const endDate = document.getElementById("end-date").value;

        const theaterId = Number(localStorage.getItem("selectedTheater"));
        const movieId = Number(localStorage.getItem("selectedMovie"));
        const time = localStorage.getItem("selectedTime");

        if (!theaterId || !movieId || !time || !startDate || !endDate) {
            alert("Please select theater, movie, time, and both dates.");
            return;
        }

        fetch("/api/saveShow", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({
                theaterId,
                movieId,
                time,
                startDate,
                endDate
            })
        })
            .then(res => res.json())
            .then(data => {
                alert("Show saved successfully!");
                localStorage.removeItem("selectedTheater");
                localStorage.removeItem("selectedMovie");
                localStorage.removeItem("selectedTime");
                window.location.href = "/screeningTheater.html"; // restart flow
            })
            .catch(err => console.error(err));
    });
}
