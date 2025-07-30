document.addEventListener("DOMContentLoaded", () => {
    const estadoSelect = document.getElementById("estado");
    const cidadeInput = document.getElementById("cidade");
    const ruaInput = document.getElementById("rua");
    const cepInput = document.getElementById("cep");

    async function buscarCEP() {
        const estado = estadoSelect.value.trim();
        const cidade = cidadeInput.value.trim();
        const rua = ruaInput.value.trim();

        if (!estado || !cidade || !rua) return;

        const url = `https://viacep.com.br/ws/${estado}/${encodeURIComponent(cidade)}/${encodeURIComponent(rua)}/json/`;

        try {
            const response = await fetch(url);
            if (!response.ok) throw new Error("Erro na API ViaCEP");
            const dados = await response.json();

            if (dados.length > 0 && dados[0].cep) {
                cepInput.value = dados[0].cep;
            } else {
                cepInput.value = "";
                console.warn("CEP não encontrado para este endereço");
            }
        } catch (error) {
            console.error("Erro ao buscar CEP:", error);
        }
    }

    estadoSelect.addEventListener("change", buscarCEP);
    cidadeInput.addEventListener("blur", buscarCEP);
    ruaInput.addEventListener("blur", buscarCEP);
});
