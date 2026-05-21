const API = window.location.origin;

let currentPage = 0;
const pageSize = 10;
let hasMore = true;

async function encurtarURL() {
  const input = document.getElementById('url-input');
  const btn = document.getElementById('shorten-btn');
  const resultBox = document.getElementById('result-box');
  const errorBox = document.getElementById('error-box');
  const url = input.value.trim();

  resultBox.classList.remove('visible');
  errorBox.classList.remove('visible');
  input.classList.remove('invalid');

  if (!url) {
    mostrarErro('Informe uma URL antes de encurtar.');
    input.classList.add('invalid');
    return;
  }

  try { new URL(url); }
  catch {
    mostrarErro('URL invalida. Inclua http:// ou https://.');
    input.classList.add('invalid');
    return;
  }

  btn.disabled = true;
  btn.innerHTML = '<span class="spinner"></span>Encurtando...';

  try {
    const res = await fetch(`${API}/api/shortener`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ urlOriginal: url })
    });

    if (!res.ok) {
      const err = await res.json().catch(() => ({ detail: `Erro ${res.status}` }));
      throw new Error(err.detail || err.message || `Erro ${res.status}`);
    }

    const data = await res.json();
    const shortUrl = data.urlEncurtada || `${API}/api/${data.codigoEncurtado}`;

    const link = document.getElementById('result-link');
    link.href = shortUrl;
    link.textContent = shortUrl;
    resultBox.classList.add('visible');
    input.value = '';
    input.classList.remove('invalid');

    currentPage = 0;
    hasMore = true;
    carregarLista(true);
  } catch (e) {
    mostrarErro(e.message);
  } finally {
    btn.disabled = false;
    btn.textContent = 'Encurtar';
  }
}

function mostrarErro(msg) {
  const box = document.getElementById('error-box');
  box.textContent = msg;
  box.classList.add('visible');
}

async function copiarLink() {
  const link = document.getElementById('result-link').href;
  const btn = document.querySelector('.copy-btn');
  try {
    await navigator.clipboard.writeText(link);
    btn.textContent = 'Copiado!';
    btn.disabled = true;
    setTimeout(() => {
      btn.textContent = 'Copiar';
      btn.disabled = false;
    }, 2000);
  } catch {
    const input = document.createElement('input');
    input.value = link;
    document.body.appendChild(input);
    input.select();
    document.execCommand('copy');
    document.body.removeChild(input);
    btn.textContent = 'Copiado!';
    setTimeout(() => btn.textContent = 'Copiar', 2000);
  }
}

async function carregarLista(reset = false) {
  const container = document.getElementById('list-container');
  const pagination = document.getElementById('pagination');

  if (reset) {
    container.innerHTML = '<div class="loading"><span class="spinner spinner-dark"></span> Carregando...</div>';
    currentPage = 0;
    hasMore = true;
  }

  if (!hasMore && !reset) return;

  try {
    const res = await fetch(`${API}/api/list?page=${currentPage}&size=${pageSize}`);
    if (!res.ok) throw new Error(`Erro ${res.status}`);
    const items = await res.json();

    if (reset) container.innerHTML = '';

    if (items.length === 0 && reset) {
      container.innerHTML = '<div class="empty-state">Nenhuma URL encurtada ainda.</div>';
      pagination.innerHTML = '';
      hasMore = false;
      return;
    }

    let ul = document.getElementById('url-list');
    if (!ul || reset) {
      if (ul) ul.remove();
      ul = document.createElement('ul');
      ul.id = 'url-list';
      container.appendChild(ul);
    }

    items.forEach(item => {
      const shortUrl = item.urlEncurtada || `${API}/api/${item.codigoEncurtado}`;
      const originalUrl = item.urlOriginal || '';
      const clicks = item.click || 0;
      const criadoEm = item.criadoEm ? formatarData(item.criadoEm) : '';

      const li = document.createElement('li');
      li.innerHTML = `
        <div class="url-info">
          <a class="url-short" href="${escapeHtml(shortUrl)}" target="_blank" rel="noopener">${escapeHtml(shortUrl)}</a>
          <span class="url-original" title="${escapeHtml(originalUrl)}">${escapeHtml(originalUrl)}</span>
        </div>
        <span class="url-date">${criadoEm}</span>
        <span class="badge">${clicks} clique${clicks !== 1 ? 's' : ''}</span>
      `;
      ul.appendChild(li);
    });

    if (items.length < pageSize) {
      hasMore = false;
      pagination.innerHTML = '';
    } else {
      hasMore = true;
      pagination.innerHTML = `
        <button class="btn-ghost" onclick="carregarMais()">Carregar mais</button>
        <span class="page-info">Pagina ${currentPage + 1}</span>
      `;
    }
  } catch (e) {
    if (reset) {
      container.innerHTML = `<div class="empty-state" style="color:var(--danger)">Erro ao carregar: ${escapeHtml(e.message)}</div>`;
      pagination.innerHTML = '';
    }
  }
}

async function carregarMais() {
  currentPage++;
  const pagination = document.getElementById('pagination');
  pagination.innerHTML = '<div class="loading"><span class="spinner spinner-dark"></span></div>';
  await carregarLista(false);
}

function formatarData(isoString) {
  try {
    const date = new Date(isoString);
    const now = new Date();
    const diffMs = now - date;
    const diffMin = Math.floor(diffMs / 60000);
    const diffHrs = Math.floor(diffMin / 60);
    const diffDays = Math.floor(diffHrs / 24);

    if (diffMin < 1) return 'agora';
    if (diffMin < 60) return `${diffMin}min atras`;
    if (diffHrs < 24) return `${diffHrs}h atras`;
    if (diffDays < 7) return `${diffDays}d atras`;
    return date.toLocaleDateString('pt-BR');
  } catch {
    return '';
  }
}

function escapeHtml(text) {
  const div = document.createElement('div');
  div.textContent = text;
  return div.innerHTML;
}

function confirmarRemocao() {
  document.getElementById('confirm-overlay').classList.add('visible');
}

function fecharConfirm() {
  document.getElementById('confirm-overlay').classList.remove('visible');
}

async function removerTudo() {
  fecharConfirm();
  try {
    const res = await fetch(`${API}/api/remove`, { method: 'DELETE' });
    if (!res.ok) throw new Error(`Erro ${res.status}`);
    carregarLista(true);
  } catch (e) {
    alert('Erro ao remover: ' + e.message);
  }
}

function toggleFAQ(element) {
  const item = element.closest('.faq-item');
  const answer = item.querySelector('.faq-answer');
  item.classList.toggle('open');
  answer.classList.toggle('visible');
}

document.getElementById('url-input').addEventListener('keydown', e => {
  if (e.key === 'Enter') encurtarURL();
});

document.getElementById('url-input').addEventListener('input', function() {
  this.classList.remove('invalid');
  document.getElementById('error-box').classList.remove('visible');
});

document.getElementById('confirm-overlay').addEventListener('click', function(e) {
  if (e.target === this) fecharConfirm();
});

document.addEventListener('keydown', e => {
  if (e.key === 'Escape') fecharConfirm();
});

carregarLista(true);
