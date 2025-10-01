

const buttons = document.querySelectorAll('.theaterbtn button');

buttons.forEach(button => {
    button.addEventListener('mouseenter', () => {
        button.style.transform = 'scale(1.1)';
    });

    button.addEventListener('mouseleave', () => {
        button.style.transform = 'scale(1)';
    });
});

function chooseTheater(theater) {
    fetch("/api/selection", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({ theater: theater })
    })
        .then(response => response.json())
        .then(data => {
            alert("You selected: " + data.theater);

        })
        .catch(err => console.error(err));
}