document.getElementById('formCadastroPaciente').addEventListener('submit', async function(event) {
    event.preventDefault();

    // Pegando os valores dos campos do formulário
    const nome = event.target[0].value.trim();
    const idade = parseInt(event.target[1].value, 10);
    const planoDeSaude = event.target[2].value.trim();
    const senha = event.target[3].value;

    const paciente = {
        nome,
        idade,
        planoDeSaude,
        senha
    };

    try {
        const response = await fetch('/pacientes', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(paciente)
        });

        if (response.ok) {
            alert('Paciente cadastrado com sucesso!');
            window.location.href = 'login.html';
        } else {
            const msg = await response.text();
            alert('Erro ao cadastrar paciente: ' + msg);
        }
    } catch (error) {
        alert('Erro ao conectar com o servidor.');
        console.error(error);
    }
});