// ---------------- SIGN-UP ----------------
document.getElementById("signup-form")?.addEventListener("submit", function (event) {
    event.preventDefault();

    const firstName = this.firstname.value.trim();
    const lastName = this.lastname.value.trim();
    const username = this.username.value.trim();
    const password = this.password.value.trim();
    const role = this.role.value; // must be one of EmployeeRole: KINO_ADMINISTRATOR, SALES_MANAGER, INSPECTOR

    fetch("/api/signup", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ firstName, lastName, username, password, role })
    })
        .then(res => {
            if (!res.ok) {
                return res.text().then(msg => { throw new Error(msg); });
            }
            return res.json();
        })
        .then(() => {
            alert("User created successfully!");
            this.reset();
            window.location.href = "index.html"; // back to login
        })
        .catch(err => {
            console.error(err);
            alert("Error creating user: " + err.message);
        });
});


// ---------------- LOGIN ----------------
document.querySelector(".login-box form")?.addEventListener("submit", function (event) {
    event.preventDefault();

    const username = this.querySelector("input[type='text']").value.trim();
    const password = this.querySelector("input[type='password']").value.trim();

    fetch("/api/login", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ username, password })
    })
        .then(res => res.json())
        .then(data => {
            if (data && data.success) {
                alert("Login successful!");

                // Redirect based on role from backend
                switch (data.role) {
                    case "KINO_ADMINISTRATOR":
                        window.location.href = "kino_administrator_page.html";
                        break;
                    case "SALES_MANAGER":
                        window.location.href = "sales_page.html";
                        break;
                    case "INSPECTOR":
                        window.location.href = "inspector_page.html";
                        break;
                    default:
                        alert("Unknown role. Please contact admin.");
                }
            } else {
                alert("Incorrect username or password.");
            }
        })
        .catch(err => {
            console.error(err);
            alert("Error logging in. Please try again.");
        });
});