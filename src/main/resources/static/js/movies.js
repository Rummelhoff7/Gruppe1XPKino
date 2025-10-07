console.log("Movie page log start")

const addMovieBtn = document.getElementById("addMovieBtn");
const form = document.getElementById("addMovieForm");
const submitBtn = document.getElementById('submitMovie');
const container = document.getElementById('movies');
const sortSelect = document.getElementById('sortMovies');


addMovieBtn.addEventListener("click", () => {
    form.classList.toggle("show");
})

async function fetchMovies() {
    const sortBy = sortSelect.value

    try {
        const response = await fetch('/api/movies');
        let movies = await response.json();

        movies.sort((a, b) => {
            switch (sortBy) {
                case 'title':
                    return a.movieTitle.localeCompare(b.movieTitle);
                case 'genre':
                    return a.genre.localeCompare(b.genre);
                case 'ageLimit':
                    const ageOrder = { 'ALL': 0, 'AGE_7': 7, 'AGE_11': 11, 'AGE_15': 15 };
                    return ageOrder[a.ageLimit] - ageOrder[b.ageLimit];
                case 'featureFilm':
                    return (a.featureFilm === b.featureFilm) ? 0 : a.featureFilm ? -1 : 1;
                default:
                    return 0;
            }
        });

        // tømmer containeren
        container.innerHTML = '';

        movies.forEach(movie => {
            const movieCard = document.createElement('div');
            movieCard.className = 'movie-card';

            movieCard.innerHTML = `
                <img src="/images/${movie.img}" alt="${movie.movieTitle}">
                <h3>${movie.movieTitle}</h3>
                <p><strong>Actors:</strong> ${movie.actors}</p>
                <p><strong>Genre:</strong> ${movie.genre}</p>
                <p><strong>Age Limit:</strong> ${movie.ageLimit}</p>
                <p><strong>Feature Film:</strong> ${movie.featureFilm ? 'Yes' : 'No'}</p>
                <button class="deleteBtn">Delete</button>
            `;

            movieCard.querySelector('.deleteBtn').addEventListener('click', async () => {
                if (confirm(`Are you sure you want to delete "${movie.movieTitle}"?`)) {
                    try {
                        const response = await fetch(`/api/movies/${movie.id}`, {
                            method: 'DELETE',
                        });
                        if (response.ok) {
                            fetchMovies();
                        } else {
                            alert('failed to delete movie');
                        }
                    } catch (error) {
                        console.log("Cannot delete movie", error);
                    }
                }
            });
            container.appendChild(movieCard);
        })
    } catch (error) {
        console.error('Cannot fetch movies', error);
    }
}

sortSelect.addEventListener('change', fetchMovies);

submitBtn.addEventListener('click', async (event) => {
    event.preventDefault();

    // Get input values
    const title = document.getElementById('title').value.trim();
    const actors = document.getElementById('actors').value.trim();
    const img = document.getElementById('img').value.trim();

    // Validate required fields
    if (!title || !actors || !img) {
        alert('Please fill all required fields.');
        return; // stop submission
    }

    const newMovie = {
        movieTitle: title,
        actors: actors,
        img: img,
        genre: document.getElementById('genre').value,
        ageLimit: document.getElementById('ageLimit').value,
        featureFilm: document.getElementById('featureFilm').checked, // det er boolean -> checked
        shows: []
    };

    try {
        const response = await fetch('/api/movies', {
            method: 'POST',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify(newMovie)
        });
        if (response.ok) {
            alert('Movie added');
            fetchMovies();
            form.reset();          // resets all form fields at once
            form.classList.remove('show');  // hides the form
        } else {
            alert('Error adding movie data.');
        }
    }catch (error) {
        console.error('Error adding movie data.');
    }
});

window.addEventListener('load', fetchMovies);