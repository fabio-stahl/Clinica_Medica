document.getElementById('formCadastroMedico').addEventListener('submit', async function(event) {
    event.preventDefault();

    // Pegando os valores dos campos do formulário
    const nome = event.target[0].value.trim();
    const especialidade = event.target[1].value;
    const planoDeSaude = event.target[2].value.trim();
    const senha = event.target[3].value;

    // Montando o objeto médico
    const medico = {
        nome,
        especialidade,
        planoDeSaude,
        senha
    };

    try {
        const response = await fetch('/medicos', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(medico)
        });

        if (response.ok) {
            alert('Médico cadastrado com sucesso!');
            window.location.href = 'login.html';
        } else {
            const msg = await response.text();
            alert('Erro ao cadastrar médico: ' + msg);
        }
    } catch (error) {
        alert('Erro ao conectar com o servidor.');
        console.error(error);
    }
});