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
            // Recebe o paciente completo do backend
            const paciente = await response.json();
            // Salva no localStorage para uso em paciente.html
            localStorage.setItem("paciente", JSON.stringify(paciente));
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
            const medico = await response.json();
            localStorage.setItem("medico", JSON.stringify(medico));
            window.location.href = "medico.html";
        } else {
            alert("Login do médico inválido");
        }
    } catch (error) {
        console.error("Erro ao fazer login do médico:", error);
        alert("Erro ao conectar com o servidor.");
    }
});
