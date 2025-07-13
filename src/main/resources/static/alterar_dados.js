document.getElementById("formAlterar").addEventListener("submit", async function (e) {
  e.preventDefault();

  const medicoStorage = localStorage.getItem("medico");
  if (!medicoStorage) {
    alert("Dados do médico não encontrados. Faça login novamente.");
    return;
  }

  const medicoObj = JSON.parse(medicoStorage);
  const id = medicoObj.id;

  const novoNome = document.getElementById("novoNome").value.trim();
  const novaEspecialidade = document.getElementById("novaEspecialidade").value.trim();
  const novoPlano = document.getElementById("novoPlano").value.trim();

  try {
    const response = await fetch(`/buscarmedicos/${id}`, {
      method: "PUT",
      headers: { "Content-Type": "application/json" },
      // Usar os nomes corretos das propriedades
      body: JSON.stringify({
        nome: novoNome,
        especialidade: novaEspecialidade,
        planoDeSaude: novoPlano
      })
    });

    if (response.ok) {
      alert("Dados atualizados com sucesso!");
      // Atualizar localStorage
      medicoObj.nome = novoNome;
      medicoObj.especialidade = novaEspecialidade;
      medicoObj.planoDeSaude = novoPlano;
      localStorage.setItem("medico", JSON.stringify(medicoObj));
      window.location.href = "medico.html";
    } else {
      const errorMsg = await response.text();
      alert(`Erro ao atualizar: ${errorMsg}`);
    }
  } catch (error) {
    console.error("Erro:", error);
    alert("Erro de conexão com o servidor.");
  }
});