document.getElementById("formAlterar").addEventListener("submit", async function (e) {
  e.preventDefault();

  const nomeAtual = localStorage.getItem("usuario") || "";
  const novoNome = document.getElementById("novoNome").value.trim();
  const novaEspecialidade = document.getElementById("novaEspecialidade").value.trim();
  const novoPlano = document.getElementById("novoPlano").value.trim();

  try {
    const response = await fetch("/api/medicos/alterar", {
      method: "PUT",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({
        nomeAtual,
        novoNome,
        novaEspecialidade,
        novoPlano
      })
    });

    if (response.ok) {
      alert("Dados atualizados com sucesso!");
      localStorage.setItem("usuario", novoNome);
      localStorage.setItem("especialidade", novaEspecialidade);
      localStorage.setItem("plano", novoPlano);
      window.location.href = "medico.html";
    } else {
      alert("Erro ao atualizar dados.");
    }
  } catch (error) {
    console.error("Erro ao atualizar dados:", error);
    alert("Erro de conexão com o servidor.");
  }
});
