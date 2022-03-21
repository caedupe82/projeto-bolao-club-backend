package br.com.classificados.mt.services;

import br.com.classificados.mt.domain.Painel;
import br.com.classificados.mt.domain.Palpite;
import br.com.classificados.mt.domain.Usuario;
import br.com.classificados.mt.dto.PalpiteDTO;
import br.com.classificados.mt.dto.RankingDTO;
import br.com.classificados.mt.repositories.PainelRepository;
import br.com.classificados.mt.repositories.PalpiteRepository;
import br.com.classificados.mt.repositories.UsuarioRepository;
import br.com.classificados.mt.services.exceptions.DataIntegrityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class PalpiteService {

	@Autowired
	private PalpiteRepository palpiteRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private PainelRepository painelRepository;

	public Palpite insert(Palpite palpite) {
		
		LocalDate now = LocalDate.now();
		
		List<Painel> list = this.painelRepository.findAllByMesAndAno(now.getMonthValue(), now.getYear());
		
		Painel painelValidador = null;
		
		if(list != null && !list.isEmpty()) {
			painelValidador = list.get(0);
		}
		
		if (painelValidador != null && palpite.getData().isBefore(painelValidador.getAberturaSistema())) {
			throw new DataIntegrityException("Só é possível modificar o Palpite após a abertura do sistema.");
		}
		
		if (painelValidador != null && (palpite.getData().isAfter(painelValidador.getFechamentoSistema()) || 
				palpite.getData().isEqual(painelValidador.getFechamentoSistema()))) {
			throw new DataIntegrityException("Não é possível modificar o Palpite após o fechamento do sistema.");
		}
		
		palpite.setData(LocalDate.now());
		palpite.setQuantidadeAcerto(0L);
		
		return this.palpiteRepository.save(palpite);
	}

	public PalpiteDTO findOneByUsuario(String email) {
		PalpiteDTO palpiteDTO = new PalpiteDTO();

		Usuario usuario = this.usuarioRepository.findByEmail(email);

		if (usuario.getTermosAceito() == null || usuario.getTermosAceito().equals(Boolean.FALSE)) {
			throw new DataIntegrityException("Termos de uso ainda não foi aceito.");
		}

		if (!usuario.getPagouUltimaMensalidade()) {
			throw new DataIntegrityException("Não é possível modificar o Meu Palpite sem enviar o comprovante mensal.");
		}

		LocalDate now = LocalDate.now();

		List<Palpite> list = this.palpiteRepository.findAllByUsuario(usuario.getCodigo());

		List<Painel> listPainel = this.painelRepository.findAllByMesAndAno(now.getMonthValue(), now.getYear());

		if (list == null || list.isEmpty()) {
			validarFechamentoSistema(palpiteDTO, now, listPainel);
			return palpiteDTO;
		}

		for (Palpite palpite : list) {
			if (palpite.getData().getYear() == now.getYear() && palpite.getData().getMonth() == now.getMonth()) {
				palpiteDTO = this.fromDTO(palpite);
				validarFechamentoSistema(palpiteDTO, now, listPainel);
				palpiteDTO.setAcertos(palpite.getAcertos());
				palpiteDTO.setNumerosSorteados(palpite.getNumerosSorteados());
				palpiteDTO.setQuantidadeAcerto(palpite.getQuantidadeAcerto());
				break;
			}
		}

		return palpiteDTO;
	}

	private void validarFechamentoSistema(PalpiteDTO palpiteDTO, LocalDate now, List<Painel> listPainel) {
		Painel painelValidador;
		if (listPainel != null && !listPainel.isEmpty()) {
			painelValidador = listPainel.get(0);

			if (now.isBefore(painelValidador.getAberturaSistema())
					|| (now.isAfter(painelValidador.getFechamentoSistema()) || 
							now.isEqual(painelValidador.getFechamentoSistema()))) {
				palpiteDTO.setFechamentoSistema(Boolean.TRUE);
			}
		}
	}

	public List<RankingDTO> findAllRankingByMesAndAno() {
		LocalDate now = LocalDate.now();

		return this.palpiteRepository.findAllRankingByMesAndAno(now.getMonthValue(), now.getYear());
	}

	public Palpite fromEntity(PalpiteDTO palpiteDTO) {
		return new Palpite(palpiteDTO.getCodigo(), palpiteDTO.getNumeros().replace(" ", ""),
				this.usuarioRepository.findByEmail(palpiteDTO.getEmail()).getCodigo(),
				LocalDate.parse(palpiteDTO.getData().substring(0, 10)));
	}

	public PalpiteDTO fromDTO(Palpite palpite) {
		return new PalpiteDTO(palpite.getCodigo(), palpite.getNumeros(), palpite.getData().toString(), "");
	}
}
