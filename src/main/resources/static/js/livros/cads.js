document.addEventListener("DOMContentLoaded", () => {
    // Adiciona confirmação personalizada antes de excluir
    document.querySelectorAll(".btn-delete").forEach(button => {
        button.addEventListener("click", (event) => {
            if (!confirm("Deseja realmente excluir este livro?")) {
                event.preventDefault();
            }
        });
    });
});
