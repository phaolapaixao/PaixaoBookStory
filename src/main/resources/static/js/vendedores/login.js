document.addEventListener("DOMContentLoaded", () => {
    console.log("Tela de login carregada!");

    // Mostrar/ocultar senha
    const senhaInput = document.querySelector('input[type="password"]');
    const toggleBtn = document.createElement("button");
    toggleBtn.textContent = "Mostrar";
    toggleBtn.type = "button";
    toggleBtn.classList.add("btn", "btn-sm", "btn-outline-secondary", "mt-2");

    senhaInput.parentNode.appendChild(toggleBtn);

    toggleBtn.addEventListener("click", () => {
        if (senhaInput.type === "password") {
            senhaInput.type = "text";
            toggleBtn.textContent = "Ocultar";
        } else {
            senhaInput.type = "password";
            toggleBtn.textContent = "Mostrar";
        }
    });

    const form = document.querySelector("form");
    form.addEventListener("submit", (event) => {
        const email = document.querySelector('input[type="email"]').value;
        const senha = senhaInput.value;

        if (!email.includes("@")) {
            event.preventDefault();
            alert("Digite um e-mail válido!");
        }

        if (senha.length < 6) {
            event.preventDefault();
            alert("A senha deve ter pelo menos 6 caracteres!");
        }
    });
});
