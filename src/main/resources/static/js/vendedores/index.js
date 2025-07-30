document.addEventListener("DOMContentLoaded", () => {
    const vendedorId = /*[[${vendedorId}]]*/ 1;  // substituir pelo valor real

    // Relatório de Vendas (modal)
    const relatorioModal = document.getElementById("relatorioModal");
    relatorioModal.addEventListener("show.bs.modal", () => {
        const tbody = document.getElementById("tabela-relatorio");
        tbody.innerHTML = "<tr><td colspan='5' class='text-center'>Carregando...</td></tr>";

        fetch("/vendedores/relatorio")
            .then(res => res.json())
            .then(data => {
                if (data.length === 0) {
                    tbody.innerHTML = "<tr><td colspan='5' class='text-center text-muted'>Nenhum dado encontrado</td></tr>";
                    return;
                }
                tbody.innerHTML = "";
                data.forEach(relatorio => {
                    tbody.innerHTML += `
                        <tr>
                          <td>${relatorio.ano}</td>
                          <td>${relatorio.mes}</td>
                          <td>${relatorio.totalPedidos}</td>
                          <td>${relatorio.totalLivrosVendidos}</td>
                          <td>R$ ${relatorio.totalVendas.toFixed(2)}</td>
                        </tr>`;
                });
            })
            .catch(err => {
                tbody.innerHTML = "<tr><td colspan='5' class='text-center text-danger'>Erro ao carregar dados</td></tr>";
                console.error(err);
            });
    });

    // Pedidos Pendentes
    const tbodyPedidosPendentes = document.getElementById("pedidos-pendentes-body");

    function carregarPedidosPendentes() {
        tbodyPedidosPendentes.innerHTML = "<tr><td colspan='5' class='text-center'>Carregando...</td></tr>";
        fetch(`/pedidos/pendentes/${vendedorId}`)
            .then(res => res.json())
            .then(data => {
                if (data.length === 0) {
                    tbodyPedidosPendentes.innerHTML = "<tr><td colspan='5' class='text-center text-muted'>Nenhum pedido pendente</td></tr>";
                    return;
                }
                tbodyPedidosPendentes.innerHTML = "";
                data.forEach(pedido => {
                    tbodyPedidosPendentes.innerHTML += `
                        <tr>
                            <td>${pedido.idPedido}</td>
                            <td>${pedido.nomeCliente}</td>
                            <td>${pedido.dataPedido}</td>
                            <td>
                              <select class="form-select status-select" data-pedido-id="${pedido.idPedido}">
                                <option value="Pendente" ${pedido.status === 'Pendente' ? 'selected' : ''}>Pendente</option>
                                <option value="Aprovado" ${pedido.status === 'Aprovado' ? 'selected' : ''}>Aprovado</option>
                                <option value="Cancelado" ${pedido.status === 'Cancelado' ? 'selected' : ''}>Cancelado</option>
                              </select>
                            </td>
                            <td>
                              <button class="btn btn-sm btn-primary btn-atualizar-status" data-pedido-id="${pedido.idPedido}">Atualizar</button>
                            </td>
                        </tr>`;
                });

                document.querySelectorAll('.btn-atualizar-status').forEach(btn => {
                    btn.addEventListener('click', () => {
                        const pedidoId = btn.getAttribute('data-pedido-id');
                        const select = document.querySelector(`select[data-pedido-id="${pedidoId}"]`);
                        const novoStatus = select.value;

                        fetch(`/pedidos/${pedidoId}/status?status=${novoStatus}`, {
                            method: 'PUT'
                        })
                            .then(res => {
                                if (res.ok) {
                                    alert('Status atualizado com sucesso!');
                                    carregarPedidosPendentes();
                                } else {
                                    alert('Erro ao atualizar status.');
                                }
                            })
                            .catch(err => {
                                alert('Erro ao atualizar status.');
                                console.error(err);
                            });
                    });
                });
            })
            .catch(err => {
                tbodyPedidosPendentes.innerHTML = "<tr><td colspan='5' class='text-center text-danger'>Erro ao carregar pedidos</td></tr>";
                console.error(err);
            });
    }

    // Clientes que Mais Compraram
    const tbodyClientesTop = document.getElementById("clientes-top-body");

    function carregarClientesTop() {
        tbodyClientesTop.innerHTML = "<tr><td colspan='3' class='text-center'>Carregando...</td></tr>";
        fetch(`/pedidos/clientes-top/${vendedorId}`)
            .then(res => res.json())
            .then(data => {
                if (data.length === 0) {
                    tbodyClientesTop.innerHTML = "<tr><td colspan='3' class='text-center text-muted'>Nenhum cliente encontrado</td></tr>";
                    return;
                }
                tbodyClientesTop.innerHTML = "";
                data.forEach(cliente => {
                    tbodyClientesTop.innerHTML += `
                        <tr>
                            <td>${cliente.nomeCliente}</td>
                            <td>${cliente.totalPedidos}</td>
                            <td>${cliente.totalLivros}</td>
                        </tr>`;
                });
            })
            .catch(err => {
                tbodyClientesTop.innerHTML = "<tr><td colspan='3' class='text-center text-danger'>Erro ao carregar clientes</td></tr>";
                console.error(err);
            });
    }

    carregarPedidosPendentes();
    carregarClientesTop();
});
