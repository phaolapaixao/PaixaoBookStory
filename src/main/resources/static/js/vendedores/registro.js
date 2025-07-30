document.addEventListener('DOMContentLoaded', function () {
    const senha = document.querySelector('input[th\\:field="*{senha}"]');
    const confirmarSenha = document.querySelector('input[th\\:field="*{confirmarSenha}"]');

    if (senha && confirmarSenha) {
        confirmarSenha.addEventListener('input', function () {
            confirmarSenha.setCustomValidity(
                senha.value !== confirmarSenha.value ? 'As senhas não coincidem' : ''
            );
        });
    }

    const telefone = document.querySelector('input[th\\:field="*{telefone}"]');
    if (telefone) {
        telefone.addEventListener('input', function (e) {
            let value = e.target.value.replace(/\D/g, '');
            if (value.length > 2) {
                value = '(' + value.substring(0, 2) + ') ' + value.substring(2);
            }
            if (value.length > 10) {
                value = value.substring(0, 10) + '-' + value.substring(10, 14);
            }
            e.target.value = value;
        });
    }

    const cnpj = document.querySelector('input[th\\:field="*{cnpj}"]');
    if (cnpj) {
        cnpj.addEventListener('input', function (e) {
            e.target.value = e.target.value.replace(/\D/g, '').substring(0, 14);
        });
    }
});
