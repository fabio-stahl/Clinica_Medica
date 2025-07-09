document.getElementById("formRealizar").addEventListener("submit", async function (e) {
  e.preventDefault();

  const nomeMedico = localStorage.getItem("usuario") || "";
  const nomePaciente = document.getElementById("nomePaciente").value.trim();
  const dataHora = document.getElementById("dataHora").value;
  const descricao = document.getElementById("descricao").value.trim();

  try {
    const response = await fetch("/api/consultas/realizar", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ nomeMedico, nomePaciente, dataHora, descricao })
    });

    if (response.ok) {
      alert("Consulta registrada com sucesso!");
      window.location.href = "medico.html";
    } else {
      alert("Erro ao registrar consulta.");
    }
  } catch (error) {
    console.error("Erro ao registrar consulta:", error);
    alert("Erro de conexão com o servidor.");
  }
});
