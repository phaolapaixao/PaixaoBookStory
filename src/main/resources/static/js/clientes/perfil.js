document.addEventListener("DOMContentLoaded", () => {
    const formApagar = document.getElementById("formApagar");

    formApagar.addEventListener("submit", (event) => {
        if (!confirm("Tem certeza que deseja apagar sua conta?")) {
            event.preventDefault();
        }
    });
});
