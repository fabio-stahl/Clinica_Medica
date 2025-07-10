// AvaliacaoMedico Pop-up logic

async function fetchConsultasNaoAvaliadas(pacienteId) {
    const response = await fetch(`/consultas/consultas/nao-avaliadas/${pacienteId}`);
    if (response.ok) {
        return await response.json();
    }
    return [];
}

function showAvaliacaoPopup(consultas) {
    const modal = document.createElement('div');
    modal.id = 'avaliacaoModal';
    modal.className = 'avaliacao-modal';

    modal.innerHTML = `
        <div class="avaliacao-modal-content">
            <span class="avaliacao-close">&times;</span>
            <h2>Avaliação da Consulta</h2>
            <form id="avaliacaoForm">
                <label for="nota">Nota (1 a 5):</label>
                <select id="nota" name="nota" required>
                    <option value="">Selecione</option>
                    <option value="1">1 - Muito ruim</option>
                    <option value="2">2 - Ruim</option>
                    <option value="3">3 - Regular</option>
                    <option value="4">4 - Bom</option>
                    <option value="5">5 - Excelente</option>
                </select>
                <label for="mensagem">Comentário:</label>
                <textarea id="mensagem" name="mensagem" rows="4" placeholder="Deixe seu comentário"></textarea>
                <button type="submit">Enviar Avaliação</button>
            </form>
        </div>
    `;

    document.body.appendChild(modal);

    const closeBtn = modal.querySelector('.avaliacao-close');
    closeBtn.onclick = () => {
        modal.remove();
    };

    const form = modal.querySelector('#avaliacaoForm');
    form.onsubmit = async (e) => {
        e.preventDefault();
        const nota = form.nota.value;
        const mensagem = form.mensagem.value;
        if (!nota) {
            alert('Por favor, selecione uma nota.');
            return;
        }
        // For simplicity, evaluate the first consulta in the list
        const consulta = consultas[0];
        const medicoId = consulta.medico.id;
        const pacienteId = consulta.paciente.id;

        const avaliacaoData = {
            mensagem,
            nota: parseInt(nota),
            medicoId,
            pacienteId
        };

        try {
            const res = await fetch('/api/avaliacao', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(avaliacaoData)
            });
            if (res.ok) {
                alert('Avaliação enviada com sucesso!');
                modal.remove();
            } else {
                alert('Erro ao enviar avaliação.');
            }
        } catch (error) {
            console.error('Erro ao enviar avaliação:', error);
            alert('Erro ao enviar avaliação.');
        }
    };
}

async function checkAndShowAvaliacao() {
    const pacienteStr = localStorage.getItem('paciente');
    if (!pacienteStr) return;
    const paciente = JSON.parse(pacienteStr);
    const consultas = await fetchConsultasNaoAvaliadas(paciente.id);
    if (consultas.length > 0) {
        showAvaliacaoPopup(consultas);
    }
}

// Expose the function to be called after login redirect
window.checkAndShowAvaliacao = checkAndShowAvaliacao;
