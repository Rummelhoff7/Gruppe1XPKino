document.addEventListener("DOMContentLoaded", () => {
    // Hent 'dato' fra URL
    const urlParams = new URLSearchParams(window.location.search);
    const dato = urlParams.get('dato');

    // Vælg elementer i DOM
    const valgtDatoEl = document.getElementById("valgt-dato");
    const listElement = document.getElementById("ticket-list");
    const resultatEl = document.getElementById("resultat");

    if (!dato) {
        valgtDatoEl.textContent = "ingen dato valgt";
        resultatEl.textContent = "Ingen dato fundet i URL.";
        return;
    }

    // Vis valgt dato i UI (formatér til dansk)
    const dateObj = new Date(dato);
    const formatted = dateObj.toLocaleDateString("da-DK", {
        weekday: "long",
        year: "numeric",
        month: "long",
        day: "numeric"
    });
    valgtDatoEl.textContent = formatted;

    // Send kun dato (ikke tidspunkt) til API
    const dateOnly = dateObj.toISOString().split("T")[0];

    // Hent billetter
    fetch(`/api/tickets/by-date-time?date=${encodeURIComponent(dateOnly)}`)
        .then(response => {
            if (!response.ok) throw new Error("Fejl ved hentning af data");
            return response.json();
        })
        .then(tickets => {
            listElement.innerHTML = ""; // Rens liste
            if (tickets.length === 0) {
                resultatEl.textContent = "Ingen billetter fundet.";
                return;
            }

            resultatEl.textContent = `${tickets.length} billetter fundet:`;

            tickets.forEach(ticket => {
                const li = document.createElement('li');
                li.textContent = `Ticket ID: ${ticket.id}, Price: ${ticket.price}, Seat: ${ticket.seat}`;
                listElement.appendChild(li);
            });
        })
        .catch(err => {
            resultatEl.textContent = "Der opstod en fejl: " + err.message;
            console.error("Error fetching tickets:", err);
        });
});
