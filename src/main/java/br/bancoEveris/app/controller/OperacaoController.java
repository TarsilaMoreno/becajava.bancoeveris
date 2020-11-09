package br.bancoEveris.app.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.bancoEveris.app.model.BaseResponse;
import br.bancoEveris.app.model.Conta;
import br.bancoEveris.app.model.Operacao;
import br.bancoEveris.app.request.DepositoRequest;
import br.bancoEveris.app.service.OperacaoService;

@RestController
@RequestMapping("/operacoes")
public class OperacaoController extends BaseController {
	private Operacao operacao = new Operacao();
	private OperacaoService _service;

	public OperacaoController(OperacaoService service) {
		_service = service;
	}

	@PostMapping(path = "/deposito")
	public ResponseEntity deposito(@RequestBody DepositoRequest request) {
		try {

			operacao.setTipo("D");
			operacao.setValor(request.getValor());
			if (request.getHash() != "") {
				Conta conta = new Conta();
				conta.setHash(request.getHash());
				operacao.setContaDestino(conta);
			}

			BaseResponse base = _service.inserir(operacao);
			return ResponseEntity.status(base.statusCode).body(base);

		} catch (Exception e) {
			return ResponseEntity.status(errorBase.statusCode).body(errorBase);
		}

	}

}
