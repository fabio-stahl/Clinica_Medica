document.getElementById("formCancelar").addEventListener("submit", async function (e) {
  e.preventDefault();

  const nomePaciente = document.getElementById("nomePaciente").value.trim();
  const dataHora = document.getElementById("dataHora").value;

  try {
    // CORREÇÃO: Use o endpoint correto sem "/api"
    const response = await fetch("/consultas/cancelar", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ nomePaciente, dataHora })
    });

    if (!nomePaciente || !dataHora) {
        alert("Preencha todos os campos!");
        return;
    }

    if (response.ok) {
      alert("Consulta cancelada com sucesso!");
      window.location.href = "paciente.html";
    } else {
      const errorMsg = await response.text();
      alert(`Erro ao cancelar: ${errorMsg}`); // Mostra detalhes do erro
    }
  } catch (error) {
    console.error("Erro:", error);
    alert("Erro de conexão com o servidor.");
  }
});