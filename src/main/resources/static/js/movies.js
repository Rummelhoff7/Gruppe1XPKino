console.log("Movie page log start")

async function fetchMovies() {
    try {
        const response = await fetch('/api/movies');
        const movies = await response.json();

        const container = document.getElementById('movies');
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
            `;

            container.appendChild(movieCard);
        })
    } catch (error) {
        console.error('Cannot fetch movies', error);
    }
}

document.getElementById('addMovieBtn').addEventListener('click', () => {
    const form = document.getElementById('addMovieForm');
    form.style.display = form.style.display === 'none' ? 'block' : 'none';
});



document.getElementById('submitMovie').addEventListener('click', async () => {
    const newMovie = {
        movieTitle: document.getElementById('title').value,
        actors: document.getElementById('actors').value,
        img: document.getElementById('img').value,
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
            document.getElementById('addMovieForm').reset();
        } else {
            alert('Error adding movie data.');
        }
    }catch (error) {
        console.error('Error adding movie data.');
    }
});

window.addEventListener('load', fetchMovies);