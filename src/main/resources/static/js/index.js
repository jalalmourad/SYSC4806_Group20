document.addEventListener("DOMContentLoaded", function() {
    const manageProjectsButton = document.querySelector(".btn-primary");

    manageProjectsButton.addEventListener("click", function(event) {
        event.preventDefault();
        alert("Redirecting to the Projects page...");
        window.location.href = "projects.html";
    });

    const settingsDropdown = document.getElementById("settingsDropdown");
    settingsDropdown.addEventListener("click", function() {
        console.log("Settings dropdown clicked.");
    });
});
