function atualizarContador() {
    const carrinho = JSON.parse(localStorage.getItem("carrinho")) || [];
    document.getElementById("cart-count").textContent = carrinho.length;
}

function addToCart(botao) {
    const titulo = botao.getAttribute("data-title");
    const preco = parseFloat(botao.getAttribute("data-price"));
    const livroId = parseInt(botao.getAttribute("data-id"));

    if (!livroId) { alert("ID do livro ausente!"); return; }
    if (!preco) { alert("Preço não informado!"); return; }

    let carrinho = JSON.parse(localStorage.getItem("carrinho")) || [];
    const itemExistente = carrinho.find(item => item.livroId === livroId);

    if (itemExistente) itemExistente.quantidade++;
    else carrinho.push({ livroId, titulo, preco, quantidade: 1 });

    localStorage.setItem("carrinho", JSON.stringify(carrinho));
    atualizarContador();
    alert(`📚 ${titulo} adicionado ao carrinho!`);
}

document.addEventListener("DOMContentLoaded", atualizarContador);
