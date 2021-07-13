package com.dcat.ReCo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dcat.ReCo.dtos.PageableDTO;
import com.dcat.ReCo.dtos.VoucherEditDTO;
import com.dcat.ReCo.dtos.voucher.VoucherBulkDeleteDTO;
import com.dcat.ReCo.dtos.voucher.VoucherCreateDTO;
import com.dcat.ReCo.services.voucher.VoucherService;
import com.dcat.ReCo.utils.https.HttpResponse;
import com.google.firebase.messaging.FirebaseMessagingException;

@RestController
@RequestMapping("/vouchers")
public class VoucherController {

	@Autowired
	private VoucherService voucherService;

	@GetMapping
	public ResponseEntity<?> getAll(@ModelAttribute PageableDTO pageable) {

		return HttpResponse.sendOK(voucherService.getAll(pageable.get()));
	}

	@GetMapping("/restaurant/{restaurantId}")
	public ResponseEntity<?> getAllVoucherByRestaurant(
			@PathVariable Long restaurantId,
			@ModelAttribute PageableDTO pageableDTO) {

		return HttpResponse.sendOK(voucherService
				.getAllByRestaurant(restaurantId, pageableDTO.get()));
	}

	@GetMapping("/owner/{oid}")
	public ResponseEntity<?> getAllVoucherByOwnerId(@PathVariable Long oid,
			@ModelAttribute PageableDTO pageable) {

		return HttpResponse
				.sendOK(voucherService.getAllByOwner(oid, pageable.get()));
	}

	@PostMapping
	public ResponseEntity<?> createVoucher(@RequestBody VoucherCreateDTO dto)
			throws FirebaseMessagingException {

		return HttpResponse.sendOK(voucherService.createOne(dto));
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getOneVoucher(
			@PathVariable Long id) {

		return HttpResponse.sendOK(voucherService.getOne(id));
	}

	@PutMapping
	public ResponseEntity<?> updateOneVoucher(@RequestBody VoucherEditDTO dto) {
		voucherService.updateOne(dto);

		return HttpResponse.sendNoContent();
	}

	@PostMapping("/bulk-delete")
	public ResponseEntity<?> bulkDelete(@RequestBody VoucherBulkDeleteDTO dto) {
		voucherService.bulkDelete(dto);

		return HttpResponse.sendNoContent();
	}

	@GetMapping("/top10")
	public ResponseEntity<?> getTop10Voucher() {

		return HttpResponse.sendOK(voucherService.getTop10());
	}
	
	@GetMapping("/search")
	public ResponseEntity<?> search(
			@RequestParam(name="query", required = false) String query,
			@ModelAttribute PageableDTO dto) {

		return HttpResponse.sendOK(voucherService.search(query, dto.get()));
	}
}
