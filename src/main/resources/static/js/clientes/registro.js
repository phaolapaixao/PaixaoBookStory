document.addEventListener("DOMContentLoaded", () => {
    const form = document.getElementById("formCadastro");
    const senha = document.getElementById("senha");
    const confirmarSenha = document.getElementById("confirmarSenha");

    form.addEventListener("submit", (event) => {
        if (senha.value !== confirmarSenha.value) {
            event.preventDefault();
            alert("As senhas não coincidem!");
        }
    });
});
