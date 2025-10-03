console.log("Script loaded");

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
            const html = data.map(show => `
            <div class="show-option">
                <strong>${show.movieTitle}</strong><br>
                Time: ${new Date(show.showingTime).toLocaleString()}<br>
                Theater: ${show.theaterName}<br>
                <br>
                <button onclick="goToSeatSelection(${show.showId})">Book This Show</button>
                <hr>
            </div>
            `).join("");

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
            age: 0,
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
        .then(res => {
            if (!res.ok) throw new Error("Failed to book ticket");
            return res.json();
        })
        .then(data => {
            const ticketId = data.ticketId;

            fetch(`/api/shows/tickets/${ticketId}`)
                .then(res => res.json())
                .then(ticket => {
                    showTicketModal(ticket);
                });
        })
        .catch(err => {
            alert("Error: " + err.message);
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

