//---------------- sign-up -----------------
document.getElementById("signup-form")?.addEventListener("submit", function(event) {
    event.preventDefault();

    const firstName = this.firstname.value.trim();
    const lastName = this.lastname.value.trim();
    const username = this.username.value.trim(); // NEW
    const password = this.password.value.trim();
    const role = this.role.value;

    fetch("/api/signup", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ firstName, lastName, username, password, role }) // include username
    })
        .then(res => res.json())
        .then(data => {
            alert("User created successfully!");
            this.reset();

            // Redirect to login page after successful signup
            window.location.href = "index.html";
        })
        .catch(err => {
            console.error(err);
            alert("Error creating user.");
        });
});

//---------------- login -----------------
document.querySelector(".login-box form")?.addEventListener("submit", function(event) {
    event.preventDefault();

    const username = this.querySelector("input[type='text']").value.trim();
    const password = this.querySelector("input[type='password']").value.trim();

    fetch("/api/login", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ username, password })
    })
        .then(res => res.json())
        .then(success => {
            if (success) {
                alert("Login successful!");
                window.location.href = "sales_page.html"; // redirect on success
            } else {
                alert("Incorrect username or password.");
            }
        })
        .catch(err => {
            console.error(err);
            alert("Error logging in. Please try again.");
        });
});
