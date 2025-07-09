document.getElementById("formCancelar").addEventListener("submit", async function (e) {
  e.preventDefault();

  const nomePaciente = document.getElementById("nomePaciente").value.trim();
  const dataHora = document.getElementById("dataHora").value;

  try {
    const response = await fetch("/api/consultas/cancelar", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ nomePaciente, dataHora })
    });

    if (response.ok) {
      alert("Consulta cancelada com sucesso!");
      window.location.href = "paciente.html";
    } else {
      alert("Erro ao cancelar consulta.");
    }
  } catch (error) {
    console.error("Erro ao cancelar consulta:", error);
    alert("Erro de conexão com o servidor.");
  }
});
