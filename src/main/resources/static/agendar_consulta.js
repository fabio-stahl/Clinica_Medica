async function buscarMedicos() {
  const nome = document.getElementById('filtroNome').value.trim();
  const especialidade = document.getElementById('filtroEspecialidade').value.trim();

  let url = '/api/medicos';
  const params = [];
  if (nome) params.push(`nome=${encodeURIComponent(nome)}`);
  if (especialidade) params.push(`especialidade=${encodeURIComponent(especialidade)}`);
  if (params.length > 0) url += `?${params.join('&')}`;

  try {
    const response = await fetch(url);
    const medicos = await response.json();
    renderizarMedicos(medicos);
  } catch (error) {
    console.error('Erro ao buscar médicos:', error);
    alert('Erro ao buscar médicos.');
  }
}

function renderizarMedicos(medicos) {
  const container = document.getElementById('lista-medicos');
  container.innerHTML = '';

  if (medicos.length === 0) {
    container.innerHTML = '<p>Nenhum médico encontrado.</p>';
    return;
  }

  medicos.forEach(medico => {
    const card = document.createElement('div');
    card.className = 'card-medico';
    card.innerHTML = `
      <h3>${medico.nome}</h3>
      <p><strong>Especialidade:</strong> ${medico.especialidade}</p>
      <p><strong>Plano Aceito:</strong> ${medico.plano}</p>
      <p><strong>Estrelas:</strong> ${medico.estrelas || 'N/A'}</p>
      <p><strong>Últimas Avaliações:</strong> ${medico.avaliacoes?.join(' | ') || 'Nenhuma'}</p>
    `;
    container.appendChild(card);
  });
}

// Opcional: buscar todos ao carregar
window.addEventListener('DOMContentLoaded', buscarMedicos);

document.getElementById('formConsulta').addEventListener('submit', async function(event) {
  event.preventDefault();

  const nomePaciente = document.getElementById('nomePaciente').value.trim();
  const nomeMedico = document.getElementById('nomeMedico').value.trim();
  const dataHora = document.getElementById('dataHora').value;
  const motivo = document.getElementById('motivo').value.trim();

  try {
    const response = await fetch('/consultas/agendar', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({
        nomePaciente,
        nomeMedico,
        dataHora,
        motivo
      })
    });

    if (response.ok) {
      alert('Consulta agendada com sucesso!');
      window.location.href = 'paciente.html';
    } else {
      const msg = await response.text();
      alert('Erro ao agendar consulta: ' + msg);
    }
  } catch (error) {
    alert('Erro ao conectar com o servidor.');
    console.error(error);
  }
});
