// Login do paciente
document.getElementById("pacienteLogin").addEventListener("submit", async function(e) {
    e.preventDefault();

    const nome = document.getElementById("pacienteNome").value;
    const senha = document.getElementById("pacienteSenha").value;

    try {
        const response = await fetch("/api/login/paciente", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ nome, senha })
        });

        if (response.ok) {
            localStorage.setItem("usuario", nome);
            window.location.href = "paciente.html";
        } else {
            alert("Login do paciente inválido");
        }
    } catch (error) {
        console.error("Erro ao fazer login do paciente:", error);
        alert("Erro ao conectar com o servidor.");
    }
});

// Login do médico
document.getElementById("medicoLogin").addEventListener("submit", async function(e) {
    e.preventDefault();

    const nome = document.getElementById("medicoNome").value;
    const senha = document.getElementById("medicoSenha").value;

    try {
        const response = await fetch("/api/login/medico", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ nome, senha })
        });

        if (response.ok) {
            localStorage.setItem("usuario", nome);
            window.location.href = "medico.html";
        } else {
            alert("Login do médico inválido");
        }
    } catch (error) {
        console.error("Erro ao fazer login do médico:", error);
        alert("Erro ao conectar com o servidor.");
    }
});
