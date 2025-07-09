document.getElementById("formRealizar").addEventListener("submit", async function (e) {
  e.preventDefault();

  const nomeMedico = localStorage.getItem("medico") 
    ? JSON.parse(localStorage.getItem("medico")).nome 
    : "";
  const nomePaciente = document.getElementById("nomePaciente").value.trim();
  const dataHora = document.getElementById("dataHora").value;
  const descricao = document.getElementById("descricao").value.trim();

  try {
    const response = await fetch("/consultas/realizar", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ nomeMedico, nomePaciente, dataHora, descricao })
    });

    if (response.ok) {
      const result = await response.json();
      let msg = "Consulta registrada com sucesso!";
      if (result.valorConsulta && result.valorConsulta > 0) {
        msg += ` Valor a ser pago pelo paciente: R$ ${result.valorConsulta.toFixed(2)}`;
      }
      alert(msg);
      window.location.href = "medico.html";
    } else {
      const erro = await response.text();
      alert("Erro ao registrar consulta: " + erro);
    }
  } catch (error) {
    console.error("Erro ao registrar consulta:", error);
    alert("Erro de conexão com o servidor.");
  }
});
