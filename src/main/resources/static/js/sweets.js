console.log("sweets page log start");

async function fetchSweets() {
    try {
        const response = await fetch('/api/sweets');
        const sweets = await response.json();

        const container = document.getElementById('sweets');
        container.innerHTML = ''; // clear container before adding new cards

        sweets.forEach(sweet => {
            const card = document.createElement('div');
            card.className = 'sweet-card';

            card.innerHTML = `
                <img src="images/${sweet.img}" alt="${sweet.name}">
                <div class="sweet-info">
                    <h3>${sweet.name}</h3>
                    <p>Price: ${sweet.price} DKK</p>
                </div>
                <div class="button-group">
                    <button class="editBtn">Edit</button>
                    <button class="deleteBtn">Delete</button>
                </div>
            `;

            // Delete button handler
            card.querySelector('.deleteBtn').addEventListener('click', async () => {
                if (confirm(`Are you sure you want to delete "${sweet.name}"?`)) {
                    try {
                        const response = await fetch(`/api/sweets/${sweet.id}`, {
                            method: 'DELETE',
                        });
                        if (response.ok) {
                            fetchSweets();
                        } else {
                            alert('Failed to delete sweet');
                        }
                    } catch (error) {
                        console.error("Cannot delete sweet", error);
                    }
                }
            });

            // Edit button handler
            card.querySelector('.editBtn').addEventListener('click', async () => {
                const newName = prompt("Enter new name", sweet.name) || sweet.name;
                const newPrice = parseFloat(prompt("Enter new price:", sweet.price)) || sweet.price;
                const newImg = prompt("Enter new img:", sweet.img) || sweet.img;

                const updatedSweet = {
                    id: sweet.id,
                    name: newName,
                    price: newPrice,
                    img: newImg,
                };

                try {
                    const response = await fetch(`/api/sweets/${sweet.id}`, {
                        method: 'PUT',
                        headers: { 'Content-Type': 'application/json' },
                        body: JSON.stringify(updatedSweet),
                    });
                    if (response.ok) {
                        fetchSweets();
                    } else {
                        alert('Failed to update sweet');
                    }
                } catch (error) {
                    console.error("Cannot update sweet", error);
                }
            });

            container.appendChild(card);
        });
    } catch (error) {
        console.error('Error fetching sweets:', error);
    }
}

document.addEventListener("DOMContentLoaded", fetchSweets);
