document.addEventListener("DOMContentLoaded", async function () {
  const medico = JSON.parse(localStorage.getItem("medico"));
  if (!medico) {
    alert("Médico não logado!");
    window.location.href = "login.html";
    return;
  }

  // Buscar consultas agendadas para o médico
  try {
    const response = await fetch(`/consultas/por-medico?nomeMedico=${encodeURIComponent(medico.nome)}`);
    if (!response.ok) throw new Error("Erro ao buscar consultas");
    const consultas = await response.json();
    renderizarConsultas(consultas);
  } catch (error) {
    alert("Erro ao buscar consultas agendadas.");
    console.error(error);
  }
});

function renderizarConsultas(consultas) {
  const container = document.getElementById("lista-consultas");
  container.innerHTML = "";

  if (!consultas || consultas.length === 0) {
    container.innerHTML = "<p>Nenhuma consulta agendada para realizar.</p>";
    return;
  }

  consultas.forEach(consulta => {
    const div = document.createElement("div");
    div.className = "card-consulta";
    div.innerHTML = `
      <p><strong>Paciente:</strong> ${consulta.paciente.nome}</p>
      <p><strong>Data/Hora:</strong> ${consulta.data.replace("T", " ").substring(0, 16)}</p>
      <p><strong>Descrição:</strong> ${consulta.descricao || "--"}</p>
      <textarea placeholder="Descrição do atendimento" rows="2" id="desc-${consulta.id}"></textarea>
      <button onclick="realizarConsulta(${consulta.id})">Realizar Consulta</button>
      <hr>
    `;
    container.appendChild(div);
  });
}

async function realizarConsulta(idConsulta) {
  const desc = document.getElementById(`desc-${idConsulta}`).value.trim();
  if (!desc) {
    alert("Preencha a descrição do atendimento.");
    return;
  }

  try {
    const response = await fetch("/consultas/realizar", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ idConsulta, descricao: desc })
    });

    if (response.ok) {
      const result = await response.json();
      alert(result.mensagem + (result.valorConsulta && result.valorConsulta > 0
        ? ` Valor a ser pago pelo paciente: R$ ${result.valorConsulta.toFixed(2)}` : ""));
      window.location.reload();
    } else {
      const erro = await response.text();
      alert("Erro ao registrar consulta: " + erro);
    }
  } catch (error) {
    alert("Erro de conexão com o servidor.");
    console.error(error);
  }
}

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
