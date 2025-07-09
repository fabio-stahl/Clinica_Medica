document.getElementById("pacienteLogin").addEventListener("submit", function (event) {
    event.preventDefault(); // Impede o envio do formulário
    const nome = document.getElementById("pacienteNome").value;

    // Salva o nome (opcional, pode ser usado na próxima página)
    localStorage.setItem("usuario", nome);

    // Redireciona para a página do paciente
    window.location.href = "paciente.html";
});

document.getElementById("medicoLogin").addEventListener("submit", function (event) {
    event.preventDefault(); // Impede o envio do formulário
    const nome = document.getElementById("medicoNome").value;

    // Salva o nome (opcional, pode ser usado na próxima página)
    localStorage.setItem("usuario", nome);

    // Redireciona para a página do médico
    window.location.href = "medico.html";
});
