function getInputValues() {
    const name = document.getElementById("inputName").value;
    const row = document.getElementById("inputRow").value;
    const seat = document.getElementById("inputSeat").value;

    // Display the values on the page (for now)
    document.getElementById("output").innerText =
        `Name: ${name}, Row: ${row}, Seat: ${seat}`;

    // You could also send this data to a backend using fetch or AJAX here
}

function submitDate() {
    const dateInput = document.getElementById("ticket_date");
    const selectedDate = dateInput.value; // This returns the value as a string (YYYY-MM-DD)

    console.log("Selected date:", selectedDate);
    alert("You chose: " + selectedDate);
}