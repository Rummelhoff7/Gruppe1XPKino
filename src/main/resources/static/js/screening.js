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
        const startDate = document.getElementById("start-date").value; // "yyyy-MM-dd"
        const endDate = document.getElementById("end-date").value;
        const theaterId = Number(localStorage.getItem("selectedTheater"));
        const movieId = Number(localStorage.getItem("selectedMovie"));
        const time = localStorage.getItem("selectedTime"); // "HH:mm"

        if (!theaterId || !movieId || !time || !startDate || !endDate) {
            alert("Please select theater, movie, time, and both dates.");
            return;
        }

        // Combine startDate + time into ISO string for LocalDateTime
        const showingTime = `${startDate}T${time}:00`; // "yyyy-MM-ddTHH:mm:ss"

        fetch("/api/saveShow", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({
                theaterId,
                movieId,
                showingTime,
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

//---------------- show list & edit ---------------
const showsList = document.getElementById("shows-list");

if (showsList) {
    fetch("/api/current_shows")
        .then(res => res.json())
        .then(shows => {
            showsList.innerHTML = "";

            if (shows.length === 0) {
                showsList.textContent = "No shows available today.";
                return;
            }

            shows.forEach(show => {
                const card = document.createElement("div");
                card.className = "show-card";

                const title = document.createElement("p");
                title.textContent = "Movie: " + show.movieTitle;

                const theater = document.createElement("p");
                theater.textContent = "Theater: " + show.theaterName;

                const time = document.createElement("p");
                const showingTime = new Date(show.showingTime);
                time.textContent = "Time: " + showingTime.toLocaleTimeString([], {hour: '2-digit', minute:'2-digit'});

                const age = document.createElement("p");
                age.textContent = "Age limit: " + show.ageLimit;

                card.appendChild(title);
                card.appendChild(theater);
                card.appendChild(time);
                card.appendChild(age);

                // ---------- CLICK REDIRECT ----------
                card.addEventListener("click", () => {
                    localStorage.setItem("selectedShowId", show.showId); // store show ID
                    window.location.href = "/screening_form.html"; // redirect to your form page
                });

                showsList.appendChild(card);
            });
        })
        .catch(err => {
            console.error(err);
            showsList.textContent = "Failed to load shows.";
        });
}

// ---------- Edit Show Form ----------
const editForm = document.getElementById("edit-show-form");

if (editForm) {
    const selectedShowId = localStorage.getItem("selectedShowId");

    // Fetch the show data by ID from the API
    fetch(`/api/show/${selectedShowId}`)
        .then(res => res.json())
        .then(show => {
            // Pre-fill the form
            document.getElementById("theaterName").value = show.theaterName;

            const showingTime = new Date(show.showingTime);
            document.getElementById("showingTime").value = showingTime.toTimeString().slice(0, 5);

            document.getElementById("startDate").value = show.showingStartDate;
            document.getElementById("endDate").value = show.showingEndDate;
        })
        .catch(err => console.error(err));

    // Handle form submission
    editForm.addEventListener("submit", (e) => {
        e.preventDefault();

        const updatedShow = {
            showId: selectedShowId,
            theaterName: document.getElementById("theaterName").value,
            showingTime: document.getElementById("showingTime").value,
            startDate: document.getElementById("startDate").value,
            endDate: document.getElementById("endDate").value
        };

        fetch(`/api/update_show`, {
            method: "PUT",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify(updatedShow)
        })
            .then(res => res.json())
            .then(data => {
                alert("Show updated successfully!");

                // Remove the stored show ID
                localStorage.removeItem("selectedShowId");

                // Redirect back to the show edit list page
                window.location.href = "/screening_showedit.html";
            })
            .catch(err => console.error(err));
    });
}
