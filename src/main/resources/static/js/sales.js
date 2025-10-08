console.log("Script loaded");

fetch("api/shows/sweets")
    .then(response => response.json())
    .then(data => {
        const sweetsListDiv = document.getElementById("sweets-list");

        const form = document.createElement("form");
        form.id = "sweets-form";

        const ul = document.createElement("ul");

        data.forEach(sweet => {
            const li = document.createElement("li");

            const checkbox = document.createElement("input");
            checkbox.type = "checkbox";
            checkbox.value = sweet.id;
            checkbox.name = "sweet";

            // Store price as a data attribute for easy access
            checkbox.dataset.price = sweet.price;

            const label = document.createElement("label");
            label.textContent = ` ${sweet.name} - ${sweet.price.toFixed(2)} kr`;

            li.appendChild(checkbox);
            li.appendChild(label);
            ul.appendChild(li);
        });

        form.appendChild(ul);

        const buyButton = document.createElement("button");
        buyButton.type = "submit";
        buyButton.textContent = "Buy Selected Sweets";
        form.appendChild(buyButton);

        // Area to show the total price
        const totalPriceDiv = document.createElement("div");
        totalPriceDiv.id = "total-price";
        totalPriceDiv.style.marginTop = "1em";
        form.appendChild(totalPriceDiv);

        sweetsListDiv.appendChild(form);

        form.addEventListener("submit", function (e) {
            e.preventDefault();

            const selectedCheckboxes = Array.from(form.elements["sweet"]).filter(cb => cb.checked);

            if (selectedCheckboxes.length === 0) {
                alert("Please select at least one sweet to buy.");
                return;
            }

            // Calculate total price
            const totalPrice = selectedCheckboxes.reduce((sum, cb) => {
                return sum + parseFloat(cb.dataset.price);
            }, 0);

            totalPriceDiv.textContent = `Total price: ${totalPrice.toFixed(2)} kr`;
        });
    });




// Function called on the first page when submitting a date
    function submitDate() {
    const dateInput = document.getElementById("ticket_date").value;
    if (!dateInput) {
        alert("Please choose a date.");
        return;
    }

    // Redirect to the movie_date_time.html page with date in query string
    window.location.href = `ticket_movie_date_time.html?date=${dateInput}`;
}

// Code that runs on the second page to fetch and show movies for the selected date
    document.addEventListener("DOMContentLoaded", function () {
    // Check if we are on the movie_date_time.html page by seeing if 'movie-list' exists
    const movieList = document.getElementById("movie-list");
    if (!movieList) return; // Exit if this is not the second page

    // Read the date from the URL query string
    const urlParams = new URLSearchParams(window.location.search);
    const selectedDate = urlParams.get("date");

    if (!selectedDate) {
        movieList.innerHTML = "<p>No date selected.</p>";
        return;
    }

    // Fetch shows from backend API
    fetch(`/api/shows/by-date?date=${selectedDate}`)
        .then(response => {
            if (!response.ok) {
                throw new Error("Network response was not ok.");
            }
            return response.json();
        })
        .then(data => {
            if (data.length === 0) {
                movieList.innerHTML = `<p>No shows found on ${selectedDate}.</p>`;
                return;
            }

            // Build HTML for shows
            const smallTheaters = data.filter(show => show.theaterName.toLowerCase() === "small");
            const bigTheaters = data.filter(show => show.theaterName.toLowerCase() !== "small");

            // Helper function to build HTML for a group
            function renderShows(shows) {
                return shows.map(show => `
            <div class="movie-card">
                <strong>${show.movieTitle}</strong><br>
                Time: ${new Date(show.showingTime).toLocaleString()}<br>
                Age limit: ${show.ageLimit}<br>
                <img src="/images/${show.movieImg}" alt="${show.movieTitle}">
                <br>
                <button onclick="goToSeatSelection(${show.showId})">Book This Show</button>
                <hr>
            </div>
            `).join("");
            }

            // Render the full HTML
                        const html = `
                <h2>Big Theater</h2>
                ${renderShows(bigTheaters)}
            
                <h2>Small Theater</h2>
                ${renderShows(smallTheaters)}
            `;

            movieList.innerHTML = html;

            })
            .catch(error => {
                console.error("Error fetching shows:", error);
                movieList.innerHTML = "<p>Failed to load shows.</p>";
            });
});

function goToSeatSelection(showId) {
    window.location.href = `ticket_seat.html?showId=${showId}`;
}

async function getInputValues() {
    const urlParams = new URLSearchParams(window.location.search);
    const showId = urlParams.get("showId");

    if (!showId) {
        alert("No show selected.");
        return;
    }

    const name = document.getElementById("inputName").value;
    const age = document.getElementById("inputAge").value;
    const row = parseInt(document.getElementById("inputRow").value, 10);
    const seat = parseInt(document.getElementById("inputSeat").value, 10);

    if (!name || isNaN(row) || isNaN(seat)) {
        alert("Please fill in all fields correctly.");
        return;
    }

    const showRes = await fetch(`/api/shows/${showId}`);
    const showData = await showRes.json();
    const theaterName = showData.theaterName?.toLowerCase();

    // Validate row and seat ranges based on theater type
    if (theaterName === "big") {
        if (row < 1 || row > 25 || seat < 1 || seat > 16) {
            alert("For 'big' theater, row must be 1–25 and seat must be 1–16.");
            return;
        }
    } else if (theaterName === "small") {
        if (row < 1 || row > 20 || seat < 1 || seat > 12) {
            alert("For 'small' theater, row must be 1–20 and seat must be 1–12.");
            return;
        }
    }

    const reservationData = {
        customer: {
            name: name,
            age: age,
            phoneNumber: ""
        },
        seat: {
            seatRow: row,
            seatNumber: seat
        },
        showId: parseInt(showId)
    };

    fetch('/api/shows/reservations/book', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(reservationData)
    })
        .then(async res => {
            if(!res.ok) {
                const errorMessage = await res.text(); // Read plain text message
                throw new Error(errorMessage);
            }
            return res.json();
        })
        .then(data => {
            const ticketId = data.ticketId;

            return fetch(`/api/shows/tickets/${ticketId}`);
        })
        .then(res => res.json())
        .then(ticket => {
            showTicketModal(ticket); // Show the ticket info
        })
        .catch(err => {
            alert(err.message); // Show user-friendly error
        });
}


function closeModal() {
    document.getElementById("ticketModal").style.display = "none";
}

function showTicketModal(ticket) {
    const info = `
        <strong>Name:</strong> ${ticket.customerName}<br>
        <strong>Row:</strong> ${ticket.seatRow}, Seat: ${ticket.seatNumber}<br>
        <strong>Movie:</strong> ${ticket.movieTitle}<br>
        <strong>Theater:</strong> ${ticket.theaterName}<br>
        <strong>Time:</strong> ${new Date(ticket.showingTime).toLocaleString()}<br>
        <strong>Price:</strong> $${ticket.price}
    `;
    document.getElementById("ticketInfo").innerHTML = info;
    document.getElementById("ticketModal").style.display = "block";
}

function movieSearch() {
    const title = document.getElementById("movie_search").value.trim();
    if (!title) {
        alert("Please enter a movie title.");
        return;
    }

    fetch(`/api/shows/title/${encodeURIComponent(title)}/earnings`)
        .then(response => {
            if (!response.ok) throw new Error("Movie not found");
            return response.json();
        })
        .then(data => {
            const info = `
                <strong>Title:</strong> ${data.movieTitle}<br>
                <strong>Tickets Sold:</strong> ${data.ticketsSold} <br>
                <strong>Total Earned:</strong> $${data.totalEarnings.toFixed(2)}
            `;
            document.getElementById("movie_search_info").innerHTML = info;
            document.getElementById("movie_search_Modal").style.display = "block";

        })
        .catch(error => {
            console.error("Error:", error);
            alert("Could not find movie or fetch earnings.");
        });
}





function closesearchModal() {
    document.getElementById("movie_search_Modal").style.display = "none";
}