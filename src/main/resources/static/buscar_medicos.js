async function buscarMedicos() {
  const nome = document.getElementById('filtroNome').value.trim();
  const especialidade = document.getElementById('filtroEspecialidade').value.trim();

  let url = '/buscarmedicos';
  const params = [];
  if (nome) params.push(`nome=${encodeURIComponent(nome)}`);
  if (especialidade) params.push(`especialidade=${encodeURIComponent(especialidade)}`);
  if (params.length > 0) url += `?${params.join('&')}`;

  try {
    const response = await fetch(url);
    if (!response.ok) throw new Error('Erro na resposta do servidor');
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

  if (!medicos || medicos.length === 0) {
    container.innerHTML = '<p>Nenhum médico encontrado.</p>';
    return;
  }

  medicos.forEach(medico => {
    const card = document.createElement('div');
    card.className = 'card-medico';
    card.innerHTML = `
      <h3>${medico.nome}</h3>
      <p><strong>Especialidade:</strong> ${medico.especialidade}</p>
      <p><strong>Plano Aceito:</strong> ${medico.planoDeSaude}</p>
      <p><strong>Estrelas:</strong> ${medico.mediaEstrelas || 'N/A'}</p>
      <p><strong>Últimas Avaliações:</strong> ${
        medico.avaliacoes && medico.avaliacoes.length > 0
          ? medico.avaliacoes.map(a => a.comentario).join(' | ')
          : 'Nenhuma'
      }</p>
    `;
    container.appendChild(card);
  });
}

// Opcional: buscar todos ao carregar
window.addEventListener('DOMContentLoaded', buscarMedicos);
