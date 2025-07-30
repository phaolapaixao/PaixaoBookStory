let carrinho = [];

function salvarCarrinho() {
    localStorage.setItem("carrinho", JSON.stringify(carrinho));
}

async function carregarCarrinhoBackend() {
    try {
        const resposta = await fetch('/clientes/carrinho/buscar');
        if (!resposta.ok) throw new Error('Erro ao carregar carrinho do servidor');

        const carrinhoServidor = await resposta.json();

        if (carrinhoServidor.itens && carrinhoServidor.itens.length > 0) {
            carrinho = carrinhoServidor.itens.map(item => ({
                livroId: item.livro.id,
                titulo: item.livro.titulo,
                preco: item.precoUnitario,
                quantidade: item.quantidade
            }));
            salvarCarrinho();
        }
        renderizarCarrinho();
    } catch (error) {
        console.error('Erro:', error);
        carrinho = JSON.parse(localStorage.getItem("carrinho")) || [];
        renderizarCarrinho();
    }
}

function renderizarCarrinho() {
    const tabela = document.getElementById("cart-body");
    tabela.innerHTML = "";
    let total = 0;

    if (carrinho.length === 0) {
        tabela.innerHTML = "<tr><td colspan='4' class='text-center'>Seu carrinho está vazio.</td></tr>";
        document.getElementById("totalCarrinho").textContent = "0.00";
        return;
    }

    carrinho.forEach((item, index) => {
        total += item.preco * item.quantidade;

        const linha = document.createElement("tr");
        linha.innerHTML = `
            <td>${item.titulo}</td>
            <td>R$ ${item.preco.toFixed(2)}</td>
            <td>${item.quantidade}</td>
            <td><button class="btn btn-sm btn-danger" onclick="removerItem(${index})">Remover</button></td>
        `;
        tabela.appendChild(linha);
    });

    document.getElementById("totalCarrinho").textContent = total.toFixed(2);
}

function removerItem(index) {
    carrinho.splice(index, 1);
    salvarCarrinho();
    renderizarCarrinho();
}

function limparCarrinho() {
    if (confirm("Deseja realmente esvaziar o carrinho?")) {
        carrinho = [];
        salvarCarrinho();
        renderizarCarrinho();
    }
}

async function confirmarPedido() {
    if (carrinho.length === 0) {
        alert("Seu carrinho está vazio!");
        return;
    }

    const formaPagamento = prompt("Digite a forma de pagamento (PIX, Cartão, Boleto):");
    if (!formaPagamento) {
        alert("Escolha uma forma de pagamento!");
        return;
    }

    const pedidoData = {
        formaPagamento: formaPagamento,
        itens: carrinho.map(item => ({
            livroId: item.livroId,
            quantidade: item.quantidade,
            preco: item.preco
        }))
    };

    try {
        const resposta = await fetch("/clientes/carrinho/finalizar", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(pedidoData)
        });

        if (resposta.ok) {
            alert("Pedido confirmado! Obrigado pela compra.");
            limparCarrinho();
        } else {
            alert("Erro ao confirmar pedido!");
        }
    } catch (e) {
        alert("Erro de conexão com o servidor!");
    }
}

document.getElementById("clear-cart").onclick = limparCarrinho;
document.getElementById("confirmar-pedido").onclick = confirmarPedido;

window.onload = carregarCarrinhoBackend;
